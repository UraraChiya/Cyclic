package com.lothrazar.cyclicmagic.component.itemtransfer;
import com.lothrazar.cyclicmagic.block.base.TileEntityBaseMachineInvo;
import com.lothrazar.cyclicmagic.component.uncrafter.TileEntityUncrafter.Fields;
import com.lothrazar.cyclicmagic.gui.ITileRedstoneToggle;
import com.lothrazar.cyclicmagic.util.UtilItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityItemPump extends TileEntityBaseMachineInvo implements ITickable, ITileRedstoneToggle {
  private static final int SLOT_TRANSFER = 0;
  public static enum Fields {
    REDSTONE, FILTERTYPE;
  }
  static final int FILTER_SIZE = 9;
  private int needsRedstone = 1;
  private int filterType;
  public TileEntityItemPump() {
    super(1 + FILTER_SIZE);
    this.setSlotsForBoth();
  }
  @Override
  public int[] getFieldOrdinals() {
    return super.getFieldArray(Fields.values().length);
  }
  @Override
  public int getField(int id) {
    switch (Fields.values()[id]) {
      case FILTERTYPE:
        return this.filterType;
      case REDSTONE:
        return this.needsRedstone;
    }
    return 0;
  }
  @Override
  public void setField(int id, int value) {
    switch (Fields.values()[id]) {
      case FILTERTYPE:
        this.filterType = value % 2;
      break;
      case REDSTONE:
        this.needsRedstone = value % 2;
      break;
    }
  }
  /**
   * for every side connected to me pull fluid in from it UNLESS its my current facing direction. for THAT side, i push fluid out from me pull first then push
   *
   * TODO: UtilFluid that does a position, a facing, and tries to move fluid across
   *
   *
   */
  @Override
  public void update() {
    if (world.isRemote) {
      return;
    }
    if (this.isPowered() == false) {
      return;
    }
    this.tryExport();
    this.tryImport();
  }
  public void tryExport() {
    if (this.getStackInSlot(SLOT_TRANSFER).isEmpty()) {
      return;//im empty nothing to give
    }
    boolean outputSuccess = false;
    ItemStack stackToExport = this.getStackInSlot(SLOT_TRANSFER).copy();
    EnumFacing facingTo = this.getCurrentFacing().getOpposite();
    BlockPos posSide = pos.offset(facingTo);
    EnumFacing sideOpp = facingTo.getOpposite();
    TileEntity tileTarget = world.getTileEntity(posSide);
    if (tileTarget == null ||
        tileTarget.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, sideOpp) == false) {
      return;
    }
    ItemStack pulled = UtilItemStack.tryDepositToHandler(world, posSide, sideOpp, stackToExport);
    if (pulled.getCount() != stackToExport.getCount()) {
      this.setInventorySlotContents(SLOT_TRANSFER, pulled);
      //one or more was put in
      outputSuccess = true;
    }
    if (outputSuccess && world.getTileEntity(pos.offset(facingTo)) instanceof TileEntityItemCable) {
      TileEntityItemCable cable = (TileEntityItemCable) world.getTileEntity(pos.offset(facingTo));
      cable.updateIncomingFace(facingTo.getOpposite());
    }
  }
  public void tryImport() {
    if (this.getStackInSlot(SLOT_TRANSFER).isEmpty() == false) {
      return;//im full leave me alone
    }
    EnumFacing sideOpp = this.getCurrentFacing();
    //get the block Behind me
    BlockPos posSide = pos.offset(sideOpp);
    TileEntity tileTarget = world.getTileEntity(posSide);
    if (tileTarget == null) {
      return;
    }
    if (tileTarget.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this.getCurrentFacing())) {
      IItemHandler itemHandlerFrom = tileTarget.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this.getCurrentFacing());
      for (int i = 0; i < itemHandlerFrom.getSlots(); i++) {
        ItemStack pulled = itemHandlerFrom.extractItem(i, 1, false);
        if (pulled != null && pulled.isEmpty() == false) {
          this.setInventorySlotContents(SLOT_TRANSFER, pulled.copy());
          return;
        }
      }
    }
  }
  @Override
  public void toggleNeedsRedstone() {
    int val = (this.needsRedstone + 1) % 2;
    this.setField(Fields.REDSTONE.ordinal(), val);
  }
  @Override
  public boolean onlyRunIfPowered() {
    return this.needsRedstone == 1;
  }
}
