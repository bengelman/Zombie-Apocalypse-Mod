package com.pdogmuncher.zombieapocalypse.bronzetools;

import java.util.List;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class BronzePickaxe extends ItemPickaxe {
	public boolean pounding = false;
	public BronzePickaxe(int i, ToolMaterial p_i45347_1_) {
		super(p_i45347_1_);
		// TODO Auto-generated constructor stub
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setUnlocalizedName("Bronze Pickaxe");
	}
	public static final String name = "bronzePickaxe";
	public String getName(){
		return name;
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer player) {
		if (par1ItemStack.getItem().getUnlocalizedName().equalsIgnoreCase("item.Bronze Pickaxe") &&
				!player.isPotionActive(Potion.hunger) && player.inventory.hasItem(ZombieApocalypse.amulet)/* && !par2World.isRemote*/) {
			 //target.attackEntityFrom(DamageSource.magic, 10);
			player.motionY = 1.0f;
			if (par2World.isRemote){
				
				return par1ItemStack;
			}
				ChatComponentText message = new ChatComponentText(player.getName() +  " used ground pound");
				ChatStyle colour = new ChatStyle();
				colour.setColor(EnumChatFormatting.AQUA);
				
				message.setChatStyle(colour);
			player.addChatComponentMessage(message);
			//player.addVelocity(0.0f, 1.0D, 0.0f);
			//player.addPotionEffect(new PotionEffect(Potion.resistance.id, 20, 20));
			//par2World.createExplosion(player, player.posX, player.posY, player.posZ, 5.0f, false);
			pounding = true;
			player.addPotionEffect(new PotionEffect(Potion.hunger.id, 200, 1));
			player.addPotionEffect(new PotionEffect(Potion.saturation.id, 200, 1));
			//player.addPotionEffect(new PotionEffect(Potion.confusion.id, 400, 6));
			
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
