package com.pdogmuncher.zombieapocalypse.tools;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MechaArmor extends ItemArmor{
	public MechaArmor(String unlocalizedName, ArmorMaterial material, String textureName, int type) {
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
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
		player.addPotionEffect(new PotionEffect(Potion.resistance.id, 1, 0));
		if (this == ZombieApocalypse.mechaChestplate){
			player.addPotionEffect(new PotionEffect(ZombieApocalypse.shotDamage.id, 1, 2));
		}
		else if (this == ZombieApocalypse.mechaLeggings){
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1, 2));
		}
		else if (this == ZombieApocalypse.mechaBoots){
			player.addPotionEffect(new PotionEffect(Potion.jump.id, 1, 2));
		}
		else{
			player.addPotionEffect(new PotionEffect(ZombieApocalypse.accuracy.id, 1, 2));
		}
	}
}
