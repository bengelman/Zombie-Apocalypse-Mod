package com.pdogmuncher.zombieapocalypse.bronzetools;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BronzeAxe extends ItemAxe {
	public BronzeAxe(int i, ToolMaterial p_i45327_1_) {
		super(p_i45327_1_);
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setUnlocalizedName("bronzeage_bronzeAxe");
	}
	public static final String name = "bronzeAxe";
	public String getName(){
		return name;
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer player) {
		if (par1ItemStack.getItem().getUnlocalizedName().equalsIgnoreCase("item.bronzeage_bronzeAxe") && player.onGround && !player.isPotionActive(Potion.hunger) && player.inventory.hasItem(ZombieApocalypse.amulet)) {
			 //target.attackEntityFrom(DamageSource.magic, 10);
			Vec3 look = player.getLookVec();
			player.addVelocity(look.xCoord * 5, look.yCoord, look.zCoord * 5);
			if (par2World.isRemote){
				return par1ItemStack;
			}
				ChatComponentText message = new ChatComponentText(player.getName() +  " used axe sprint");
				ChatStyle colour = new ChatStyle();
				colour.setColor(EnumChatFormatting.AQUA);
				
				message.setChatStyle(colour);
			player.addChatComponentMessage(message);
			//player.addVelocity(0.0f, 1.0D, 0.0f);
			//player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 5, 40));
			
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
