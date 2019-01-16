package com.pdogmuncher.zombieapocalypse.staffs;

import java.util.List;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventHandler;

public class IceStaff extends ItemSpade {

	public IceStaff(int i, ToolMaterial p_i45353_1_) {
		super(p_i45353_1_);
		// TODO Auto-generated constructor stub
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setUnlocalizedName("Ice Staff");
	}
	public static final String name = "fire_staff";
	public String getName(){
		return name;
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer player) {
		if (par1ItemStack.getItem().getUnlocalizedName().equalsIgnoreCase("item.ice_staff") && !player.isPotionActive(Potion.hunger) && player.inventory.hasItem(ZombieApocalypse.amulet) && !par2World.isRemote) {
			 //target.attackEntityFrom(DamageSource.magic, 10);
				ChatComponentText message = new ChatComponentText(player.getName() +  " used snowball push");
				ChatStyle colour = new ChatStyle();
				colour.setColor(EnumChatFormatting.AQUA);
				
				message.setChatStyle(colour);
			player.addChatComponentMessage(message);
			//player.addVelocity(0.0f, 1.0D, 0.0f);
			//player.addPotionEffect(new PotionEffect(Potion.hunger.id, 200, 1));
			
			/*Vec3 look = player.getLookVec();
            EntityLargeFireball fireball = new EntityLargeFireball(par2World, player, 0, 0, 0);
            fireball.explosionPower = 0;
            fireball.setPosition(
                    player.posX + look.xCoord * 2,
                    player.posY + 1 + look.yCoord * 2,
                    player.posZ + look.zCoord * 2);
            fireball.accelerationX = look.xCoord * 0.1;
            fireball.accelerationY = look.yCoord * 0.1;
            fireball.accelerationZ = look.zCoord * 0.1;
            par2World.spawnEntityInWorld(fireball);*/
			List pounded = par2World.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.fromBounds((double)player.posX, (double)player.posY, (double)player.posZ, (double)(player.posX + 1), (double)(player.posY + 1), (double)(player.posZ + 1)).expand((double)(3), 4.0D, (double)(3)));
			for (int i = 0; i < pounded.size(); i++){
				if (pounded.get(i) instanceof EntityLiving){
					EntityLiving living = (EntityLiving) pounded.get(i);
					living.addVelocity(living.posX - player.posX, living.posY - player.posY, living.posZ - player.posZ);
				}
			}
			for (int i = 0; i < 4; i++){
				EntitySnowball snowball = new EntitySnowball(par2World, player);
				snowball.setVelocity(2, 0, 0);
				par2World.spawnEntityInWorld(snowball);
				snowball.posY--;
				
				EntitySnowball snowball3 = new EntitySnowball(par2World, player);
				snowball3.setVelocity(0.0, 0, 2);
				par2World.spawnEntityInWorld(snowball3);
				snowball3.posY--;
				
				EntitySnowball snowball4 = new EntitySnowball(par2World, player);
				snowball4.setVelocity(-2., 0, 0);
				par2World.spawnEntityInWorld(snowball4);
				snowball4.posY--;
				
				EntitySnowball snowball5 = new EntitySnowball(par2World, player);
				snowball5.setVelocity(0, 0, -2);
				par2World.spawnEntityInWorld(snowball5);
				snowball5.posY--;
				
				
				
				EntitySnowball vsnowball = new EntitySnowball(par2World, player);
				vsnowball.setVelocity(1.4, 0, 1.4);
				par2World.spawnEntityInWorld(vsnowball);
				vsnowball.posY--;
				
				EntitySnowball vsnowball3 = new EntitySnowball(par2World, player);
				vsnowball3.setVelocity(1.4, 0, -1.4);
				par2World.spawnEntityInWorld(vsnowball3);
				vsnowball3.posY--;
				
				EntitySnowball vsnowball4 = new EntitySnowball(par2World, player);
				vsnowball4.setVelocity(-1.4, 0, 1.4);
				par2World.spawnEntityInWorld(vsnowball4);
				vsnowball4.posY--;
				
				EntitySnowball vsnowball5 = new EntitySnowball(par2World, player);
				vsnowball5.setVelocity(-1.4, 0, -1.4);
				par2World.spawnEntityInWorld(vsnowball5);
				vsnowball5.posY--;
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
	@EventHandler
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker){
		this.onItemRightClick(stack, target.worldObj, (EntityPlayer)attacker);
		return true;
	}
	public EnumRarity getRarity (ItemStack par1){ 
		return EnumRarity.EPIC; //uncommon= Yellow rare= Light Blue epic= Purple 
	} 
}
