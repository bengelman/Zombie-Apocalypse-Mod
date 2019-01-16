package com.pdogmuncher.zombieapocalypse.bronzetools;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.Mod.EventHandler;

public class BronzeHoe extends ItemHoe {

	public BronzeHoe(int i, ToolMaterial arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setUnlocalizedName("Bronze Hoe");
	}
	public static final String name = "bronzeHoe";
	public String getName(){
		return name;
	}
	@Override
	public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer player, EntityLivingBase target)
	{
		
		System.out.println(itemStack.getItem().getUnlocalizedName());
		 if (itemStack.getItem().getUnlocalizedName().equalsIgnoreCase("item.Bronze Hoe") && 
				 !player.isPotionActive(Potion.hunger) && player.inventory.hasItem(ZombieApocalypse.amulet) && 
				 !player.worldObj.isRemote) {
			 //target.attackEntityFrom(DamageSource.magic, 10);
			 ChatComponentText message = new ChatComponentText(player.getName() +  " used dirt throw");
			 ChatStyle colour = new ChatStyle();
			 colour.setColor(EnumChatFormatting.AQUA);
				
			 message.setChatStyle(colour);
			 player.addChatComponentMessage(message);/*
			 double var2 = target.posX - player.posX * 1.0012;
			 double var4 = target.posZ - player.posZ * 1.0012;
			 target.addVelocity(var2 * 2, 2.0D, var4 * 2);*/
			 target.addPotionEffect(new PotionEffect(Potion.blindness.id, 200, 10));
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
			 return true;
		 }else {
			 return false;
		 }
	}
}
