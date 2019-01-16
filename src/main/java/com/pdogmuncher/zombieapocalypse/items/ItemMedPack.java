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

public class ItemMedPack extends Item{
	public ItemMedPack(){
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
				living.setHealth(living.getMaxHealth());
				return stack;
			}
		}
		player.setHealth(player.getMaxHealth());
		if (player.getActivePotionEffect(ZombieApocalypse.bleeding) != null){
			player.removePotionEffect(ZombieApocalypse.bleeding.id);
		}
		if (player.getActivePotionEffect(ZombieApocalypse.infected_wound) != null){
			player.removePotionEffect(ZombieApocalypse.infected_wound.id);
		}
		return stack;
	}
	
}
