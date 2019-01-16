package com.pdogmuncher.zombieapocalypse.staffs;

import java.util.Random;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AirStaff extends ItemSpade {

	public AirStaff(int i, ToolMaterial p_i45353_1_) {
		super(p_i45353_1_);
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setUnlocalizedName("Air Staff");
	}
	public static final String name = "air_staff";
	public String getName(){
		return name;
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer player) {
		if (par1ItemStack.getItem().getUnlocalizedName().equalsIgnoreCase("item.Air Staff") && player.inventory.hasItem(ZombieApocalypse.amulet)) {
			 //target.attackEntityFrom(DamageSource.magic, 10);
				ChatComponentText message = new ChatComponentText(player.getName() +  " used fly");
				ChatStyle colour = new ChatStyle();
				colour.setColor(EnumChatFormatting.AQUA);
				
				message.setChatStyle(colour);
			player.addChatComponentMessage(message);
			//player.addVelocity(0.0f, 1.0D, 0.0f);
			//player.addPotionEffect(new PotionEffect(Potion.hunger.id, 200, 1));
			//player.posY+= 1;
		    player.setPositionAndUpdate(player.posX, player.posY + 0.5f, player.posZ);
			player.capabilities.allowFlying = true;
			player.capabilities.isFlying = true;
			if (Minecraft.getMinecraft().playerController.gameIsSurvivalOrAdventure()){
				player.capabilities.allowFlying = false;
			}
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
	public EnumRarity getRarity (ItemStack par1){ 
		return EnumRarity.EPIC; //uncommon= Yellow rare= Light Blue epic= Purple 
	} 
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int num, boolean truefalse){
		if (entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) entity;
			if (player.capabilities.isFlying){
				Random rand = new Random();
				double motionX = rand.nextGaussian() * 0.02D;
        	    double motionY = rand.nextGaussian() * 0.02D;
        	    double motionZ = rand.nextGaussian() * 0.02D;
        	    world.spawnParticle(
        	          EnumParticleTypes.ENCHANTMENT_TABLE,
        	          player.posX + rand.nextFloat() * player.width * 2.0F - player.width, 
        	          player.posY + rand.nextFloat() * (player.height / 2), 
        	          player.posZ + rand.nextFloat() * player.width * 2.0F - player.width, 
        	          motionX, 
        	          motionY, 
        	          motionZ);
			}
		}
	}
	@EventHandler
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker){
		this.onItemRightClick(stack, target.worldObj, (EntityPlayer)attacker);
		return true;
	}
}
