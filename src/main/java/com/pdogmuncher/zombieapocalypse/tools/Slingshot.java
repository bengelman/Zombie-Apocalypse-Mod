package com.pdogmuncher.zombieapocalypse.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Slingshot extends Item
{
  public Slingshot()
  {
    super();
    setCreativeTab(CreativeTabs.tabCombat);
    setUnlocalizedName("slingshot");
  }
  int cooldown = 0;
  public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer par3EntityPlayer) {
	  if (cooldown > 0){
		  return par1ItemStack;
	  }
	    if(par3EntityPlayer.capabilities.isCreativeMode||par3EntityPlayer.inventory.consumeInventoryItem(Items.clay_ball))
	    {
	        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
	        if (!par2World.isRemote)
	        {
	        	Projectile p = new Projectile(par2World, par3EntityPlayer);
	            par2World.spawnEntityInWorld(p);
	            p.addVelocity(p.motionX, p.motionY, p.motionZ);
	            cooldown = 40;
	        }
	    }
	    return par1ItemStack;
  }
  public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
	  if (cooldown > 0){
		  cooldown--;
	  }
  }
}

