package com.pdogmuncher.zombieapocalypse.staffs;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.mobs.UberFerret;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventHandler;

public class LightningStaff extends ItemSpade {
	public static World savedWorld;
	public LightningStaff(int i, ToolMaterial p_i45353_1_) {
		super(p_i45353_1_);
		// TODO Auto-generated constructor stub
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setUnlocalizedName("Lightning Staff");
	}
	public static final String name = "lightning_staff";
	public String getName(){
		return name;
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
    	if(Minecraft.getMinecraft().objectMouseOver!=null && player.inventory.hasItem(ZombieApocalypse.amulet))
    	{
    		if (Minecraft.getMinecraft().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK){
    			if (par2World.isRemote){
        			
        			int xm=Minecraft.getMinecraft().objectMouseOver.getBlockPos().getX();
            		int ym=Minecraft.getMinecraft().objectMouseOver.getBlockPos().getY();
            		int zm=Minecraft.getMinecraft().objectMouseOver.getBlockPos().getZ();  
            		Entity lightning = new EntityLightningBolt(par2World, xm, ym, zm);
            		par2World.spawnEntityInWorld(lightning);
            		
        			return par1ItemStack;
        		}
        		savedWorld = par2World;
        		int xm=Minecraft.getMinecraft().objectMouseOver.getBlockPos().getX();
        		int ym=Minecraft.getMinecraft().objectMouseOver.getBlockPos().getY();
        		int zm=Minecraft.getMinecraft().objectMouseOver.getBlockPos().getZ();  
        		Entity lightning = new EntityLightningBolt(par2World, xm, ym, zm);
        		lightning.setInvisible(false);
        		/*UberFerret ubsFer = new UberFerret(par2World);
        		ubsFer.setPosition(player.posX, player.posY, player.posZ);
        		Minecraft.getMinecraft().theWorld.spawnEntityInWorld(ubsFer);*/
        		ChatComponentText message = new ChatComponentText(player.getName() +  " used lightning strike");
    			ChatStyle colour = new ChatStyle();
    			colour.setColor(EnumChatFormatting.AQUA);
    			
    			message.setChatStyle(colour);
    			player.addChatComponentMessage(message);
        		par2World.setThunderStrength(5.0f);
        		par2World.spawnEntityInWorld(lightning);
        		par2World.setRainStrength(5.0f);
        		player.addStat(ZombieApocalypse.lightningStrike, 1);
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
    					break;
    				}
    			}
    		}
    		else if (Minecraft.getMinecraft().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY){
    			if (par2World.isRemote){
        			
        			int xm=Minecraft.getMinecraft().objectMouseOver.entityHit.getPosition().getX();
            		int ym=Minecraft.getMinecraft().objectMouseOver.entityHit.getPosition().getY();
            		int zm=Minecraft.getMinecraft().objectMouseOver.entityHit.getPosition().getZ();  
            		Entity lightning = new EntityLightningBolt(par2World, xm, ym, zm);
            		par2World.spawnEntityInWorld(lightning);
            		
        			return par1ItemStack;
        		}
        		savedWorld = par2World;
        		int xm=Minecraft.getMinecraft().objectMouseOver.entityHit.getPosition().getX();
        		int ym=Minecraft.getMinecraft().objectMouseOver.entityHit.getPosition().getY();
        		int zm=Minecraft.getMinecraft().objectMouseOver.entityHit.getPosition().getZ();  
        		Entity lightning = new EntityLightningBolt(par2World, xm, ym, zm);
        		lightning.setInvisible(false);
        		/*UberFerret ubsFer = new UberFerret(par2World);
        		ubsFer.setPosition(player.posX, player.posY, player.posZ);
        		Minecraft.getMinecraft().theWorld.spawnEntityInWorld(ubsFer);*/
        		ChatComponentText message = new ChatComponentText(player.getName() +  " used lightning strike");
    			ChatStyle colour = new ChatStyle();
    			colour.setColor(EnumChatFormatting.AQUA);
    			
    			message.setChatStyle(colour);
    			player.addChatComponentMessage(message);
        		par2World.setThunderStrength(5.0f);
        		par2World.spawnEntityInWorld(lightning);
        		par2World.setRainStrength(5.0f);
        		player.addStat(ZombieApocalypse.lightningStrike, 1);
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
    					break;
    				}
    			}
    		}
    	}
	
    	return par1ItemStack;
}
	@EventHandler
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker){
		this.onItemRightClick(stack, target.worldObj, (EntityPlayer)attacker);
		return true;
	}
	public EnumRarity getRarity (ItemStack par1){ 
		return EnumRarity.EPIC; //uncommon= Yellow rare= Light Blue epic= Purple 
	} 
}
