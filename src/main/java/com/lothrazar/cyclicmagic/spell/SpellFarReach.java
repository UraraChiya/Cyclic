package com.lothrazar.cyclicmagic.spell;

import com.lothrazar.cyclicmagic.ModMain;
import com.lothrazar.cyclicmagic.util.UtilSound;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class SpellFarReach extends BaseSpell {

	public SpellFarReach(int id, String n) {
		super(id, n);
		this.cooldown = 1;
	}
	int maxRange = 50;
	@Override
	public boolean cast(World world, EntityPlayer player, BlockPos pos, EnumFacing side) {

	
        if(!player.capabilities.allowEdit) {
        	return false;
        }
	
		System.out.println("far reach cast " + world.isRemote);
		
		
		if(world.isRemote){
			
			BlockPos mouseover = ModMain.proxy.getBlockMouseover(maxRange);
			
			if(mouseover != null){
				System.out.println(mouseover.toString());
			}
			
			
			
		}
		
		return false;
	}
}
