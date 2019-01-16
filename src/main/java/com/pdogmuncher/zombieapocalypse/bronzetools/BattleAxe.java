package com.pdogmuncher.zombieapocalypse.bronzetools;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class BattleAxe extends ItemAxe {
	public BattleAxe(int i, ToolMaterial p_i45327_1_) {
		super(p_i45327_1_);
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setUnlocalizedName("bronzeage_battle_axe");
	}
	public static final String name = "battle_axe";
	public String getName(){
		return name;
	}
	/*@Override
	public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer player, EntityLivingBase target)
	{
		
		System.out.println(itemStack.getItem().getUnlocalizedName());
		 if (itemStack.getItem().getUnlocalizedName().equalsIgnoreCase("item.bronzeage_battle_axe")) {
			 //target.attackEntityFrom(DamageSource.magic, 10);
				ChatComponentText message = new ChatComponentText(player.getName() +  " used hammer smash");
				ChatStyle colour = new ChatStyle();
				colour.setColor(EnumChatFormatting.AQUA);
				
				message.setChatStyle(colour);
			player.addChatComponentMessage(message);
				double var2 = target.posX - player.posX * 1.0012;
			    double var4 = target.posZ - player.posZ * 1.0012;
			    target.addVelocity(var2 * 2, 2.0D, var4 * 2);
			 return true;
		 }else {
			 return false;
		 }
	}*/
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer player) {
		if (par1ItemStack.getItem().getUnlocalizedName().equalsIgnoreCase("item.bronzeage_battle_axe") && 
			player.onGround && !player.isPotionActive(Potion.hunger) && player.inventory.hasItem(ZombieApocalypse.amulet)) {
			if (par2World.isRemote){
				player.addVelocity(0.0f, 1.0D, 0.0f);
				return par1ItemStack;
			}
			 //target.attackEntityFrom(DamageSource.magic, 10);
			ChatComponentText message = new ChatComponentText(player.getName() +  " used axe leap");
			ChatStyle colour = new ChatStyle();
			colour.setColor(EnumChatFormatting.AQUA);
				
			message.setChatStyle(colour);
			player.addChatComponentMessage(message);
			player.addVelocity(0.0f, 1.0D, 0.0f);
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 50, 2000));
			player.addPotionEffect(new PotionEffect(Potion.hunger.id, 200, 1));
			player.addPotionEffect(new PotionEffect(Potion.saturation.id, 400, 1));
			ItemStack itemstack = null;
			for (int i = 0; i < player.inventory.mainInventory.length; i++) {
				ItemStack s = player.inventory.mainInventory[i];
				if (s != null && s.getItem().getUnlocalizedName().equalsIgnoreCase("item.Amulet")) {
					if (!player.capabilities.isCreativeMode){
						s.setItemDamage(s.getItemDamage() + 1);
					}
					if (s.getMaxDamage() <= s.getItemDamage()){
						s.stackSize = -1;
						player.inventory.setInventorySlotContents(i, null);
					}
					System.out.println("Damage item");
					break;
				}
			}
		 }
		    return par1ItemStack;
	  }
}
