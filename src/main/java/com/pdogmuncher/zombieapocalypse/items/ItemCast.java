package com.pdogmuncher.zombieapocalypse.items;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemCast extends Item{
	public ItemCast(){
		super();
	}
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (!player.capabilities.isCreativeMode){
			player.inventory.consumeInventoryItem(this);
		}
		
		if (player.getActivePotionEffect(ZombieApocalypse.broken_leg) != null){
			int duration = player.getActivePotionEffect(ZombieApocalypse.broken_leg).getDuration();
			player.removePotionEffect(ZombieApocalypse.broken_leg.id);
			player.addPotionEffect(new PotionEffect(ZombieApocalypse.broken_leg.id, duration / 4));
		}
		return stack;
	}
	
}
