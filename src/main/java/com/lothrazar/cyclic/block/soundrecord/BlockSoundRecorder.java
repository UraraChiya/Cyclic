package com.lothrazar.cyclic.block.soundrecord;

import com.lothrazar.cyclic.base.BlockBase;
import com.lothrazar.cyclic.net.PacketRecordSound;
import com.lothrazar.cyclic.registry.ContainerScreenRegistry;
import com.lothrazar.cyclic.registry.PacketRegistry;
import com.lothrazar.cyclic.util.UtilBlockstates;
import java.util.List;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockSoundRecorder extends BlockBase {

  public static IntValue RADIUS;

  public BlockSoundRecorder(Properties properties) {
    super(properties.strength(1F).sound(SoundType.SCAFFOLDING));
    MinecraftForge.EVENT_BUS.register(this);
    this.setHasGui();
  }

  @Override
  public void registerClient() {
    MenuScreens.register(ContainerScreenRegistry.SOUND_RECORDER, ScreenSoundRecorder::new);
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Override
  public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
    return new TileSoundRecorder();
  }

  @OnlyIn(Dist.CLIENT)
  @SubscribeEvent
  public void onPlaySound(PlaySoundEvent event) {
    ClientLevel clientWorld = Minecraft.getInstance().level;
    if (event.getSound() == null || event.getSound().getLocation() == null || event.getResultSound() instanceof TickableSoundInstance || clientWorld == null) {
      return;
    } //long term/repeating/music
    List<BlockPos> blocks = UtilBlockstates.findBlocks(clientWorld,
        new BlockPos(event.getSound().getX(), event.getSound().getY(), event.getSound().getZ()), this, RADIUS.get());
    for (BlockPos nearby : blocks) {
      String sid = event.getSound().getLocation().toString();
      //TODO: only send packet if block is in 'listening' / 'lit' blocksate?
      PacketRegistry.INSTANCE.sendToServer(new PacketRecordSound(sid, nearby));
      //hack save to client. otherwise have to hard sync or reload world
      BlockEntity tile = clientWorld.getBlockEntity(nearby);
      if (tile instanceof TileSoundRecorder) {
        ((TileSoundRecorder) tile).onSoundHeard(sid);
      }
    }
  }
}