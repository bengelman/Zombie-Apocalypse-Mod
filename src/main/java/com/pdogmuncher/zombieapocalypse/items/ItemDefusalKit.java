package com.pdogmuncher.zombieapocalypse.items;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemDefusalKit extends Item{
	public ItemDefusalKit(){
		super();
	}
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing facing, float arg0, float arg1, float arg2){
		
		if (world.getBlockState(pos).getBlock() == ZombieApocalypse.bomb && !world.isRemote){
			world.setBlockState(pos, Blocks.air.getDefaultState());
			player.addChatMessage(new ChatComponentText(player.getDisplayNameString() + " has defused the bomb!"));
			return true;
		}
		return false;
	}
	
}
