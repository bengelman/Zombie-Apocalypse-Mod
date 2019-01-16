package com.pdogmuncher.zombieapocalypse.tools;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityFlare extends EntityCreature{
	int timeglowing = 600;
	public EntityFlare(World worldIn) {
		super(worldIn);
		this.setHealth(100);
		this.setSize(1.0f, 1.0f);
		// TODO Auto-generated constructor stub
	}
	public void applyEntityAttributes(){
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.0f);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100);
	}
	public EntityFlare(World worldIn, int lifespan) {
		super(worldIn);
		timeglowing = lifespan;
		this.setHealth(100);
		this.setSize(1.0f, 1.0f);
		System.out.println("Time left: " + timeglowing);
		// TODO Auto-generated constructor stub
	}
	public void onLivingUpdate(){
		Entity entity = this;
		List pounded = worldObj.getEntitiesWithinAABBExcludingEntity(entity, AxisAlignedBB.fromBounds((double)entity.posX, (double)entity.posY, (double)entity.posZ, (double)(entity.posX + 1), (double)(entity.posY + 1), (double)(entity.posZ + 1)).expand((double)(20), 20.0D, (double)(20)));
		for (int i = 0; i < pounded.size(); i++){
			if (pounded.get(i) instanceof EntityLiving){
				EntityLiving living = (EntityLiving) pounded.get(i);
				living.setAttackTarget((EntityLivingBase) entity);
				//living.getNavigator().tryMoveToEntityLiving(entity, living.getAIMoveSpeed());
			}
		}
		try {
			worldObj.makeFireworks(posX, posY, posZ, 0, 0, 0, JsonToNBT.func_180713_a("{LifeTime:1,FireworksItem:{id:401,Count:1,tag:{Fireworks:{Explosions:[{Type:0,Flicker:0,Trail:0,Colors:[16711680],FadeColors:[16711680]}]}}}}"));
		} catch (NBTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double motionX = rand.nextGaussian() * 0.02D;
	    double motionY = rand.nextGaussian() * 0.02D;
	    double motionZ = rand.nextGaussian() * 0.02D;
	    worldObj.spawnParticle(
      	          EnumParticleTypes.FIREWORKS_SPARK, 
      	          posX + rand.nextFloat() * width * 2.0F - width, 
      	          posY + 0.5D + rand.nextFloat() * height, 
      	          posZ + rand.nextFloat() * width * 2.0F - width, 
      	          motionX, 
      	          motionY, 
      	          motionZ);
		timeglowing--;
		
		if (timeglowing <= 0){
			this.setDead();
		}
	}
}
