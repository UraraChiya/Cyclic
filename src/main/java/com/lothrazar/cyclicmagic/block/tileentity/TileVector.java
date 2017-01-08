package com.lothrazar.cyclicmagic.block.tileentity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

/**
 * PLAN: gui to change power and vector.
 * 
 * make sure it saves data when you harvest and place
 * 
 * @author Sam
 *
 */
public class TileVector extends TileEntityBaseMachineInvo {
  public static final int MAX_ANGLE = 90;
  public static final int MAX_YAW = 360;
  public static final int MAX_POWER = 64;
  public static final int DEFAULT_ANGLE = 45;
  public static final int DEFAULT_YAW = 90;
  public static final int DEFAULT_POWER = 10;
  public static final String NBT_ANGLE = "vectorAngle";
  public static final String NBT_POWER = "vectorPower";
  public static final String NBT_YAW = "vectorYaw";
  private int angle = DEFAULT_ANGLE;
  private int power = DEFAULT_POWER;
  private int yaw = DEFAULT_YAW;
  public static enum Fields {
    ANGLE, POWER, YAW;
  }
  public TileVector() {}
  @Override
  public void readFromNBT(NBTTagCompound tagCompound) {
    super.readFromNBT(tagCompound);
    power = tagCompound.getInteger(NBT_POWER);
    angle = tagCompound.getInteger(NBT_ANGLE);
    yaw = tagCompound.getInteger(NBT_YAW);
  }
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
    tagCompound.setInteger(NBT_POWER, power);
    tagCompound.setInteger(NBT_ANGLE, angle);
    tagCompound.setInteger(NBT_YAW, yaw);
    return super.writeToNBT(tagCompound);
  }
  public float getActualPower() {
    return (power + 10) / 10;
  }
  public int getPower() {
    return power;
  }
  public int getAngle() {
    return angle;
  }
  public int getYaw() {
    return yaw;
  }
  @Override
  public int getField(int id) {
    if (id >= 0 && id < this.getFieldCount())
      switch (Fields.values()[id]) {
      case ANGLE:
      return angle;
      case POWER:
      return power;
      case YAW:
      return yaw;
      default:
      break;
      }
    return -1;
  }
  @Override
  public void setField(int id, int value) {
    if (id >= 0 && id < this.getFieldCount())
      switch (Fields.values()[id]) {
      case ANGLE:
      this.angle = Math.min(value, MAX_ANGLE);
      break;
      case POWER:
      this.power = Math.min(value, MAX_POWER);
      if (this.power <= 0) this.power = 1;
      break;
      case YAW:
      this.yaw = Math.min(value, MAX_YAW);
      break;
      }
  }
  @Override
  public int getFieldCount() {
    return Fields.values().length;
  }
  @Override
  public int getSizeInventory() {
    return 0;
  }
  @Override
  public ItemStack getStackInSlot(int index) {
    return null;
  }
  @Override
  public ItemStack decrStackSize(int index, int count) {
    return null;
  }
  @Override
  public ItemStack removeStackFromSlot(int index) {
    return null;
  }
  @Override
  public void setInventorySlotContents(int index, ItemStack stack) {}
  @Override
  public int[] getSlotsForFace(EnumFacing side) {
    return new int[] {};
  }
  //  @Override
  //  public void toggleNeedsRedstone() {
  //    int val = this.needsRedstone + 1;
  //    if (val > 1) {
  //      val = 0;//hacky lazy way
  //    }
  //    this.setField(Fields.REDSTONE.ordinal(), val);
  //  }
  //  private boolean onlyRunIfPowered() {
  //    return this.needsRedstone == 1;
  //  }
}
