package com.pdogmuncher.zombieapocalypse.bronzetools;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class BronzeSpade extends ItemSpade {

	public BronzeSpade(int i, ToolMaterial p_i45353_1_) {
		super(p_i45353_1_);
		// TODO Auto-generated constructor stub
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setUnlocalizedName("Bronze Spade");
	}
	public static final String name = "bronzeSpade";
	public String getName(){
		return name;
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer player) {
		if (par1ItemStack.getItem().getUnlocalizedName().equalsIgnoreCase("item.Bronze Spade") && !player.isPotionActive(Potion.hunger) && player.inventory.hasItem(ZombieApocalypse.amulet) && !par2World.isRemote) {
			 //target.attackEntityFrom(DamageSource.magic, 10);
				ChatComponentText message = new ChatComponentText(player.getName() +  " used spade tunnel");
				ChatStyle colour = new ChatStyle();
				colour.setColor(EnumChatFormatting.AQUA);
				
				message.setChatStyle(colour);
			player.addChatComponentMessage(message);
			//player.addVelocity(0.0f, 1.0D, 0.0f);
			player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 100, 5));
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
