package com.pdogmuncher.zombieapocalypse.bronzetools;



import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.mobs.Ferret;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BattleHammer extends ItemSword {

	public BattleHammer(int i, ToolMaterial arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setUnlocalizedName("Battle Hammer");
		
	}
	public static final String name = "battle_hammer";
	public String getName(){
		
		return name;
	}
	@EventHandler
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker){
		
		double var2 = target.posX - attacker.posX * 1.0012;
	    double var4 = target.posZ - attacker.posZ * 1.0012;
	    target.addVelocity(var2, 0.0D, var4);
		return true;
	}
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity){

	       
		return bFull3D;
		
		
	}
	@Override
	public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer player, EntityLivingBase target)
	{
		
		System.out.println(itemStack.getItem().getUnlocalizedName());
		 if (itemStack.getItem().getUnlocalizedName().equalsIgnoreCase("item.Battle Hammer") && !player.isPotionActive(Potion.hunger) && player.inventory.hasItem(ZombieApocalypse.amulet)) {
			 if (player.worldObj.isRemote)
			 {
				 //player.playSound("zombieapocalypse:music.tinte", 1.0f, 0.4f);
				 return false;
			 }
			 //target.attackEntityFrom(DamageSource.magic, 10);
			ChatComponentText message = new ChatComponentText(player.getName() +  " used hammer smash");
			ChatStyle colour = new ChatStyle();
			colour.setColor(EnumChatFormatting.AQUA);
				
			message.setChatStyle(colour);
			player.addChatComponentMessage(message);
			double var2 = target.posX - player.posX * 1.0012;
			double var4 = target.posZ - player.posZ * 1.0012;
			target.addVelocity(var2 * 2, 2.0D, var4 * 2);
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
		 }
		 else {
			 return false;
		 }
	}
	
	@Override
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
	  super.onUpdate(stack, world, entity, par4, par5);
	  EntityPlayer player = (EntityPlayer) entity;
	  ItemStack equipped = player.getCurrentEquippedItem();
	  if (player.getCurrentEquippedItem() == null){
		  return;
	  }
	  if(equipped.getItem().getUnlocalizedName().equalsIgnoreCase("item.Battle Hammer")) {
	      player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5, 1));
	  }
	}
}
