package com.pdogmuncher.zombieapocalypse.items;


import java.util.List;
import java.util.Random;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.tools.EntityFlare;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
//This item draws mobs towards it, and can be thrown
public class ItemFlare extends Item{
	public ItemFlare(){
		super();
		this.setMaxStackSize(1);
		this.setMaxDamage(30);
	}
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (!world.isRemote){
			if (!stack.getTagCompound().getBoolean("firing")){
				if (!world.isRemote){
					stack.getTagCompound().setBoolean("firing", true);
				}
				
			}
			else{
				System.out.println("Throwing");
				
				EntityFlare flare = new EntityFlare(world, 600 - (stack.getItemDamage() * 20));
				flare.setPosition(player.posX, player.posY, player.posZ);
				flare.addVelocity(player.getLookVec().xCoord * 10, player.getLookVec().yCoord * 10, player.getLookVec().zCoord * 10);
				world.spawnEntityInWorld(flare);
				player.inventory.consumeInventoryItem(this);
			}
		}
		return stack;
	}
	public void onUpdate(ItemStack stack, World world, Entity entity, int number, boolean bool){
		if (stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
		}
		int x = (int) entity.posX;
		int y = (int) entity.posY;
		int z = (int) entity.posZ;
		for (int i = x - 5; i < x+5; i++){
			for (int j = y - 5; j < y+5; j++){
				for (int k = z - 5; k < z+5; k++){
					if (world.getBlockState(new BlockPos(i, j, k)).getBlock() == ZombieApocalypse.light){
						world.setBlockState(new BlockPos(i, j, k), Blocks.air.getDefaultState());
					}
				}
			}
		}
		if (stack.getTagCompound().getBoolean("firing") && !world.isRemote){
			if (world.getBlockState(entity.getPosition()).getBlock() == Blocks.air){
				//world.setBlockState(entity.getPosition(), ZombieApocalypse.light.getDefaultState());
			}
		}
		if (stack.getTagCompound().getBoolean("firing") && world.getTotalWorldTime() % 20 == 0 && !world.isRemote){
			world.playSoundAtEntity(entity, "fire.fire", 1.0f, 1.0f);
			stack.setItemDamage(stack.getItemDamage()+1);
			if (stack.getItemDamage() >= stack.getMaxDamage()){
				stack = null;
				((EntityPlayer)entity).inventory.setInventorySlotContents(number, null);
				return;
			}
			try {
				world.makeFireworks(entity.posX, entity.posY, entity.posZ, 0, 0, 0, JsonToNBT.func_180713_a("{LifeTime:1,FireworksItem:{id:401,Count:1,tag:{Fireworks:{Explosions:[{Type:0,Flicker:0,Trail:0,Colors:[16711680],FadeColors:[16711680]}]}}}}"));
			} catch (NBTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			List pounded = world.getEntitiesWithinAABBExcludingEntity(entity, AxisAlignedBB.fromBounds((double)entity.posX, (double)entity.posY, (double)entity.posZ, (double)(entity.posX + 1), (double)(entity.posY + 1), (double)(entity.posZ + 1)).expand((double)(20), 20.0D, (double)(20)));
			for (int i = 0; i < pounded.size(); i++){
				if (pounded.get(i) instanceof EntityLiving){
					EntityLiving living = (EntityLiving) pounded.get(i);
					living.setAttackTarget((EntityLivingBase) entity);
					//living.getNavigator().tryMoveToEntityLiving(entity, living.getAIMoveSpeed());
				}
			}
		}
	}
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer p, int uses){
		if (stack.getTagCompound().getBoolean("firing")){
			return new ModelResourceLocation("zombieapocalypse:flare_lit", "inventory");
		}
		return new ModelResourceLocation("zombieapocalypse:flare", "inventory");
	}
}
