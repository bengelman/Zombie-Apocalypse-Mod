package com.pdogmuncher.zombieapocalypse.bronzetools;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BronzeArmor extends ItemArmor{
	public BronzeArmor(String unlocalizedName, ArmorMaterial material, String textureName, int type) {
	    super(material, 0, type);
	    this.setUnlocalizedName(unlocalizedName);
	    
	    //this.setTextureName("bronzeage" + ":" + unlocalizedName);
	}
	/*
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack){
		if (player.getHeldItem().getItem().equals(ExampleMod.amulet)){
			player.capabilities.allowFlying = true;
		}
		else{
			player.capabilities.allowFlying = false;
		}
	}
	*/
}
