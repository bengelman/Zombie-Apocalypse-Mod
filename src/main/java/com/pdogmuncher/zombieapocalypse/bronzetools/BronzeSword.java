package com.pdogmuncher.zombieapocalypse.bronzetools;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BronzeSword extends ItemSword {

	public BronzeSword(int i, ToolMaterial arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setUnlocalizedName("Bronze Sword");
	}
	public static final String name = "bronzeSword";
	public String getName(){
		return name;
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer player) {
		super.onItemRightClick(par1ItemStack, par2World, player);
		if (par1ItemStack.getItem().getUnlocalizedName().equalsIgnoreCase("item.Bronze Sword") && !player.isPotionActive(Potion.hunger) && player.inventory.hasItem(ZombieApocalypse.amulet) && !par2World.isRemote) {
			 //target.attackEntityFrom(DamageSource.magic, 10);
				ChatComponentText message = new ChatComponentText(player.getName() +  " used sharpen");
				ChatStyle colour = new ChatStyle();
				colour.setColor(EnumChatFormatting.AQUA);
				
				message.setChatStyle(colour);
			player.addChatComponentMessage(message);
			//player.addVelocity(0.0f, 1.0D, 0.0f);
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 200, 1));
			player.addPotionEffect(new PotionEffect(Potion.hunger.id, 400, 1));
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
	  
	/*
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
    	if(Minecraft.getMinecraft().objectMouseOver!=null)
    	{
    		int xm=Minecraft.getMinecraft().objectMouseOver.getBlockPos().getX();
    		int ym=Minecraft.getMinecraft().objectMouseOver.getBlockPos().getY();
    		int zm=Minecraft.getMinecraft().objectMouseOver.getBlockPos().getZ();  		
    		par2World.spawnEntityInWorld(new EntityLightningBolt(par2World, xm, ym, zm));
    	}
	
    	return par1ItemStack;
}
*/
}
