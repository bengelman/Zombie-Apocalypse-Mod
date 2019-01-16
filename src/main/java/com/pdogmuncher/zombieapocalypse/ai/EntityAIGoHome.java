package com.pdogmuncher.zombieapocalypse.ai;

import java.util.Random;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ChatComponentText;

import com.pdogmuncher.zombieapocalypse.mobs.CitizenFemale;
import com.pdogmuncher.zombieapocalypse.tools.EntityBullet;

public class EntityAIGoHome extends EntityAIBase{
private final CitizenFemale theEntity;
	int conversationTime = 0;
	int currentTalk = 0;
	String[] talks = new String[]{"It's nice weather we're having", "Yikes, I think I heard a zombie", "I wonder what's for dinner?"};
	public EntityAIGoHome(CitizenFemale citizenFemale)
	{
	   theEntity = citizenFemale;
	   setMutexBits(1);

	    // DEBUG
	    //System.out.println("EntityAIPanicHerdAnimal constructor()");
	}

	@Override
	public boolean shouldExecute()
	{
		if (theEntity.worldObj.isDaytime() || theEntity.homeBase == null || theEntity.getDistanceSqToCenter(theEntity.homeBase) < 5)
	    {
	        return false;
	    }
	    else
	    {
	    	//System.out.println("Walking Over");
	    	//theEntity.getMoveHelper().setMoveTo(theEntity.homeBase.getX(), theEntity.homeBase.getY(), theEntity.homeBase.getZ(), 0.67f);
	    	theEntity.getNavigator().clearPathEntity();
	    	theEntity.getNavigator().tryMoveToXYZ(theEntity.homeBase.getX(), theEntity.homeBase.getY(), theEntity.homeBase.getZ(),0.67f);
	    	
	    	//theEntity.getNavigator().setPath(theEntity.getNavigator().getPathToXYZ(theEntity.homeBase.getX(), theEntity.homeBase.getY(), theEntity.homeBase.getZ()), 0.67f);
	    	//theEntity.getNavigator().getPathToEntityLiving(theEntity.conversing);
	    	//theEntity.getNavigator().tryMoveToEntityLiving(theEntity.worldObj.getClosestPlayerToEntity(theEntity, 10), 0.67);
	        return true;
	    }    
	}
	@Override
	public boolean continueExecuting()
	{
		if (theEntity.getNavigator().getPath() == null){
			return false;
		}
		if (!theEntity.getNavigator().getPath().isFinished()){
			if (theEntity.motionX < 0.2 && theEntity.motionZ < 0.2){
				theEntity.getJumpHelper().doJump();
				//theEntity.addVelocity(0, 1, 0);
			}
			
			//theEntity.getNavigator().
			//theEntity.getMoveHelper().setMoveTo(theEntity.homeBase.getX(), theEntity.homeBase.getY(), theEntity.homeBase.getZ(), 0.67f);
			return true;
		}
		//theEntity.faceEntity(theEntity.conversing, 0.0f, 0.0f);
		
		/*
		if (theEntity.getAttackTarget() == null){
			return false;
		}
		if (theEntity.getAttackTarget().isDead){
			
		}
		if (theEntity.worldObj.isRemote){
			return true;
		}
		theEntity.faceEntity(theEntity.getAttackTarget(), 90, 90);
		if (theEntity.recharge > 0){
			
	   	}
	   	else{
		   theEntity.recharge = 20;
		   theEntity.worldObj.playSoundEffect(theEntity.posX, theEntity.posY, theEntity.posZ, "random.explode", 0.5f, 0.2f);
		   EntityBullet p = new EntityBullet(theEntity.worldObj, theEntity);
		   p.damage = 15;
		   p.addVelocity(p.motionX * 3, p.motionY * 3, p.motionZ * 3);
           theEntity.worldObj.spawnEntityInWorld(p);
		}
		*/
	   return false;
	}
}
