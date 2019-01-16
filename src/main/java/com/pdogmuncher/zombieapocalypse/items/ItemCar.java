package com.pdogmuncher.zombieapocalypse.items;

import com.pdogmuncher.zombieapocalypse.mobs.EntityCar;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemCar extends Item
{
public ItemCar()
{
	 super();
	 this.maxStackSize = 1;
	 this.setCreativeTab(CreativeTabs.tabTransport);
}

/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
{
	if (!par2World.isRemote){
		if (player.capabilities.isCreativeMode | player.inventory.consumeInventoryItem(this)){
			EntityCar tank = new EntityCar(par2World);
			tank.setPositionAndRotation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationYaw);
			par2World.spawnEntityInWorld(tank);
		}
	}
	 return par1ItemStack;
}
}