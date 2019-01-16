package com.pdogmuncher.zombieapocalypse.tools;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemCrossbow extends Item{
	public long ticks = 0;
	public final int USE_TIME = 20;
	public ItemCrossbow(){
		super();
		this.setMaxStackSize(1);
		this.setMaxDamage(100);
	}
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer p){
		if (stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
		}
		if (!world.isRemote && stack.getTagCompound().getBoolean("armed") && stack.getTagCompound().getInteger("next") - ticks <= 0){
			world.playSoundAtEntity(p, "random.bow", 1.0F, 1.0F);
			stack.getTagCompound().setBoolean("armed", false);
			EntityArrow arrow = new EntityArrow(world, p, 2.0f);
			arrow.setDamage(3.5f);
			world.spawnEntityInWorld(arrow);
			stack.getTagCompound().setInteger("next", (int)ticks + USE_TIME);
		}
		return stack;
	}
	public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean selected){
		if (stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
		}
		if (world.isRemote || !(player instanceof EntityPlayer)){
			return;
		}
		if (!stack.getTagCompound().getBoolean("armed")){
			if (((EntityPlayer)player).capabilities.isCreativeMode){
				stack.getTagCompound().setBoolean("armed", true);
			}
			else if (((EntityPlayer)player).inventory.consumeInventoryItem(Items.arrow)){
				stack.getTagCompound().setBoolean("armed", true);
			}
		}
		if (!stack.getTagCompound().getBoolean("armed")){
			
		}
		ticks = world.getTotalWorldTime();
	}
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer p, int someCrap){
		if (!stack.getTagCompound().getBoolean("armed")){
			return new ModelResourceLocation("zombieapocalypse:crossbow", "inventory");
		}
		if (stack.getTagCompound().getInteger("next") - ticks <= USE_TIME / 4){
			return new ModelResourceLocation("zombieapocalypse:crossbow_full", "inventory");
		}
		if (stack.getTagCompound().getInteger("next") - ticks <= USE_TIME / 2){
			return new ModelResourceLocation("zombieapocalypse:crossbow_1", "inventory");
		}
		if (stack.getTagCompound().getInteger("next") - ticks <= (3*USE_TIME) / 2){
			return new ModelResourceLocation("zombieapocalypse:crossbow_2", "inventory");
		}
		return new ModelResourceLocation("zombieapocalypse:crossbow", "inventory");
	}
}
