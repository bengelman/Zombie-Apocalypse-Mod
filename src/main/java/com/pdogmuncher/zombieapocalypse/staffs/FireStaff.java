package com.pdogmuncher.zombieapocalypse.staffs;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
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
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventHandler;

public class FireStaff extends ItemSpade {

	public FireStaff(int i, ToolMaterial p_i45353_1_) {
		super(p_i45353_1_);
		// TODO Auto-generated constructor stub
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setUnlocalizedName("Fire Staff");
	}
	public static final String name = "fire_staff";
	public String getName(){
		return name;
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer player) {
		if (par1ItemStack.getItem().getUnlocalizedName().equalsIgnoreCase("item.Fire Staff") && !player.isPotionActive(Potion.hunger) && player.inventory.hasItem(ZombieApocalypse.amulet) && !par2World.isRemote) {
			 //target.attackEntityFrom(DamageSource.magic, 10);
				ChatComponentText message = new ChatComponentText(player.getName() +  " used fireball");
				ChatStyle colour = new ChatStyle();
				colour.setColor(EnumChatFormatting.AQUA);
				
				message.setChatStyle(colour);
			player.addChatComponentMessage(message);
			//player.addVelocity(0.0f, 1.0D, 0.0f);
			//player.addPotionEffect(new PotionEffect(Potion.hunger.id, 200, 1));
			
			Vec3 look = player.getLookVec();
            EntitySmallFireball fireball = new EntitySmallFireball(par2World, player, 0, 0, 0);
            //fireball.explosionPower = 3;
            fireball.setPosition(
                    player.posX + look.xCoord * 2,
                    player.posY + 1 + look.yCoord * 2,
                    player.posZ + look.zCoord * 2);
            fireball.accelerationX = look.xCoord * 0.1;
            fireball.accelerationY = look.yCoord * 0.1;
            fireball.accelerationZ = look.zCoord * 0.1;
            fireball.addVelocity(fireball.motionX * 3, fireball.motionY * 3, fireball.motionZ * 3);
            par2World.spawnEntityInWorld(fireball);
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
		target.setFire(5);
		return true;
	}
	public EnumRarity getRarity (ItemStack par1){ 
		return EnumRarity.EPIC; //uncommon= Yellow rare= Light Blue epic= Purple 
	} 
}
