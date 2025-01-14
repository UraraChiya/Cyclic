package com.lothrazar.cyclic.event;

import com.lothrazar.cyclic.potion.CyclicMobEffect;
import com.lothrazar.cyclic.registry.BlockRegistry;
import com.lothrazar.cyclic.registry.PotionEffectRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PotionEvents {

  @SubscribeEvent
  public void onPotionAdded(MobEffectEvent.Added event) {
    if (event.getEffectInstance().getEffect() instanceof CyclicMobEffect self) {
      self.onPotionAdded(event);
    }
  }

  @SubscribeEvent
  public void isPotionApplicable(MobEffectEvent.Applicable event) {
    if (event.getEffectInstance().getEffect() instanceof CyclicMobEffect self) {
      self.isPotionApplicable(event);
    }
    BlockRegistry.ANTI_BEACON.get().isPotionApplicable(event);
  }

  @SubscribeEvent
  public void onPotionRemove(MobEffectEvent.Remove event) {
    if (event.getEffect() instanceof CyclicMobEffect self) {
      self.onPotionRemove(event);
    }
  }

  @SubscribeEvent
  public void onPotionExpiry(MobEffectEvent.Expired event) {
    if (event.getEffectInstance().getEffect() instanceof CyclicMobEffect self) {
      self.onPotionExpiry(event);
    }
  }

  @SubscribeEvent
  public void onEntityUpdate(LivingTickEvent event) {
    LivingEntity entity = event.getEntity();
    if (entity == null) {
      return;
    }
    for (CyclicMobEffect effect : PotionEffectRegistry.EFFECTS) {
      if (effect != null && entity.hasEffect(effect)) {
        effect.tick(event);
      }
    }
    //    if (!entity.level.isClientSide && entity instanceof Player player) {
    //      //onPotionDisable hack trigger
    //      if (player.getAbilities().mayfly
    //          && !player.isCreative()
    //          && !player.hasEffect(PotionEffectRegistry.angelic.get())) {
    //        //youre not angelic potion'd, and you ARE allowed to fly, 
    //        //and youre not creative
    //        CyclicFile file = PlayerDataEvents.getOrCreate(player);
    //        //        file.todoVisible = true; // set fly is from potion. les yes
    //        if (file.todoVisible) {
    //          file.todoVisible = false;
    //          .println("booted from flight");
    //          player.fallDistance = 0.0F;
    //          player.getAbilities().flying = false;
    //          player.getAbilities().mayfly = false;
    //        }
    //      }
    //    }
  }
}
