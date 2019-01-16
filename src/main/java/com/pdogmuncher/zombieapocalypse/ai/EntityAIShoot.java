package com.pdogmuncher.zombieapocalypse.ai;

import java.util.List;

import com.pdogmuncher.zombieapocalypse.tools.EntityBullet;
import com.pdogmuncher.zombieapocalypse.tools.EntityRocket;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class EntityAIShoot extends EntityAIBase{
	private final EntityLiving theEntity;
	int recharge = 0;
	public EntityAIShoot(EntityLiving par1Entity)
	{
	   theEntity = par1Entity;
	   setMutexBits(3);

	    // DEBUG
	    //System.out.println("EntityAIPanicHerdAnimal constructor()");
	}

	@Override
	public boolean shouldExecute()
	{
		if (theEntity.getAttackTarget() == null)
	    {
	        return false;
	    }
	    else
	    {
	    	//System.out.println("Shooting");
	        return true;
	    }    
	}
	@Override
	public boolean continueExecuting()
	{
		if (theEntity.getAttackTarget() == null){
			return false;
		}
		if (theEntity.getAttackTarget().isDead){
			return false;
		}
		if (theEntity.worldObj.isRemote){
			return true;
		}
		if (!theEntity.canEntityBeSeen(theEntity.getAttackTarget())){
			return false;
		}
		theEntity.faceEntity(theEntity.getAttackTarget(), 90, 90);
		if (recharge > 0){
			recharge--;
	   	}
	   	else{
		   recharge = 20;
		   theEntity.worldObj.playSoundEffect(theEntity.posX, theEntity.posY, theEntity.posZ, "random.explode", 0.5f, 0.2f);
		   EntityBullet p = new EntityBullet(theEntity.worldObj, theEntity, 15, 10, false);
		   p.damage = 15;
		   p.addVelocity(p.motionX * 3, p.motionY * 3, p.motionZ * 3);
           theEntity.worldObj.spawnEntityInWorld(p);
		}
	   return true;
	}
}
