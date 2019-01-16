package com.pdogmuncher.zombieapocalypse.tools;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.tools.EntityGrenade.GrenadeType;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Grenade extends Item
{
	public GrenadeType type;
	public Grenade(GrenadeType type)
	{
		super();
		this.type = type;
		setCreativeTab(ZombieApocalypse.tabGuns);
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par2World.isRemote){
			if (!par3EntityPlayer.capabilities.isCreativeMode)
			{
				--par1ItemStack.stackSize;
			}
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			
			par2World.spawnEntityInWorld(new EntityGrenade(par2World, par3EntityPlayer, type));
		}
		
		
		return par1ItemStack;
	}
}

