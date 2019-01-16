package com.pdogmuncher.zombieapocalypse.items;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemBandaid extends Item{
	public ItemBandaid(){
		super();
	}
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (!player.capabilities.isCreativeMode){
			player.inventory.consumeInventoryItem(this);
		}
		MovingObjectPosition hit = Minecraft.getMinecraft().objectMouseOver;
		if (hit.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY){
			if (hit.entityHit instanceof EntityLiving){
				EntityLiving living = (EntityLiving) hit.entityHit;
				living.setHealth(living.getHealth() + 2);
				return stack;
			}
		}
		if (player.getHealth() <= player.getMaxHealth() - 2){
			player.setHealth(player.getHealth() + 2);
		}
		else{
			player.setHealth(player.getMaxHealth());
		}
		/*
		if (player.getActivePotionEffect(ZombieApocalypse.bleeding) != null){
			player.getActivePotionEffect(ZombieApocalypse.bleeding).
		}*/
		if (player.getActivePotionEffect(ZombieApocalypse.bleeding) != null){
			player.removePotionEffect(ZombieApocalypse.bleeding.id);
		}
		return stack;
	}
	
}
