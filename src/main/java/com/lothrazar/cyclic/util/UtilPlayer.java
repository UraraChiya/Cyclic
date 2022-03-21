package com.lothrazar.cyclic.util;

import java.util.UUID;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;

public class UtilPlayer {

  public static final UUID REACH_ID = UUID.fromString("1abcdef2-eff2-4a81-b92b-a1cb95f115c6");

  public static void removePlayerReach(Player playerIn) {
    AttributeInstance attr = playerIn.getAttribute(ForgeMod.REACH_DISTANCE.get());
    attr.removeModifier(REACH_ID);
  }

  public static void addPlayerReach(Player playerIn, int reachBoost) {
    removePlayerReach(playerIn);
    AttributeInstance attr = playerIn.getAttribute(ForgeMod.REACH_DISTANCE.get());
    //vanilla is 5, so +11 it becomes 16
    AttributeModifier enchantment = new AttributeModifier(REACH_ID, "ReachEnchantmentCyclic", reachBoost, AttributeModifier.Operation.ADDITION);
    attr.addPermanentModifier(enchantment);
  }

  public static double getExpTotal(Player player) {
    //  validateExpPositive(player);
    int level = player.experienceLevel;
    // numeric reference:
    // http://minecraft.gamepedia.com/Experience#Leveling_up
    double totalExp = getXpForLevel(level);
    double progress = Math.round(player.getXpNeededForNextLevel() * player.experienceProgress);
    totalExp += (int) progress;
    return totalExp;
  }

  public static int getXpForLevel(int level) {
    // numeric reference:
    // http://minecraft.gamepedia.com/Experience#Leveling_up
    int totalExp = 0;
    if (level <= 15) {
      totalExp = level * level + 6 * level;
    }
    else if (level <= 30) {
      totalExp = (int) (2.5 * level * level - 40.5 * level + 360);
    }
    else {
      // level >= 31
      totalExp = (int) (4.5 * level * level - 162.5 * level + 2220);
    }
    return totalExp;
  }

  public static ItemStack getPlayerItemIfHeld(Player player) {
    ItemStack wand = player.getMainHandItem();
    if (wand.isEmpty()) {
      wand = player.getOffhandItem();
    }
    return wand;
  }

  public static int getFirstSlotWithBlock(Player player, BlockState targetState) {
    int ret = -1;
    ItemStack stack;
    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
      stack = player.getInventory().getItem(i);
      if (!stack.isEmpty() &&
          stack.getItem() != null &&
          Block.byItem(stack.getItem()) == targetState.getBlock()) {
        return i;
      }
    }
    return ret;
  }

  public static BlockState getBlockstateFromSlot(Player player, int slot) {
    ItemStack stack = player.getInventory().getItem(slot);
    if (!stack.isEmpty() &&
        stack.getItem() != null &&
        Block.byItem(stack.getItem()) != null) {
      Block b = Block.byItem(stack.getItem());
      return b.defaultBlockState();
    }
    return null;
  }

  public static void decrStackSize(Player player, int slot) {
    if (player.isCreative() == false && slot >= 0) {
      player.getInventory().removeItem(slot, 1);
    }
  }

  public static Item getItemArmorSlot(Player player, EquipmentSlot slot) {
    ItemStack inslot = player.getInventory().armor.get(slot.getIndex());
    //    ItemStack inslot = player.inventory.armorInventory[slot.getIndex()];
    Item item = (inslot.isEmpty()) ? null : inslot.getItem();
    return item;
  }
}
