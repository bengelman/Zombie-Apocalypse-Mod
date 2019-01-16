package com.pdogmuncher.zombieapocalypse.mobs;

import java.util.Random;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ChatComponentText;

import com.pdogmuncher.zombieapocalypse.tools.EntityBullet;

public class EntityAITalk extends EntityAIBase{
private final CitizenFemale theEntity;
	int conversationTime = 0;
	int currentTalk = 0;
	String[] talks = new String[]{"It's nice weather we're having", "Yikes, I think I heard a zombie", "I wonder what's for dinner?"};
	public EntityAITalk(CitizenFemale citizenFemale)
	{
	   theEntity = citizenFemale;
	   setMutexBits(2);

	    // DEBUG
	    //System.out.println("EntityAIPanicHerdAnimal constructor()");
	}

	@Override
	public boolean shouldExecute()
	{
		if (theEntity.worldObj.getClosestPlayerToEntity(theEntity, 10) == null || theEntity.conversing != null || (new Random().nextInt() % 50) != 0)
	    {
	        return false;
	    }
	    else
	    {
	    	//System.out.println("Walking Over");
	    	theEntity.conversing = theEntity.worldObj.getClosestPlayerToEntity(theEntity, 10);
	    	currentTalk = Math.abs(new Random().nextInt() % talks.length);
	    	//theEntity.getNavigator().getPathToEntityLiving(theEntity.conversing);
	    	//theEntity.getNavigator().tryMoveToEntityLiving(theEntity.worldObj.getClosestPlayerToEntity(theEntity, 10), 0.67);
	        return true;
	    }    
	}
	@Override
	public boolean continueExecuting()
	{
		//theEntity.faceEntity(theEntity.conversing, 0.0f, 0.0f);
		double xdiff = theEntity.conversing.posX - theEntity.posX;
		double ydiff = theEntity.conversing.posZ - theEntity.posZ;
		double angle = Math.atan(ydiff/xdiff);
		if (xdiff < 0){
			angle += Math.PI;
		}
		//theEntity.setRotationYawHead((float) angle);
		theEntity.faceEntity(theEntity.conversing, 360f, 360f);
		conversationTime++;
		if (conversationTime > 40){
			theEntity.setCustomNameTag("");
			conversationTime = 0;
			theEntity.conversing = null;
			return false;
		}
		else if (currentTalk < talks.length){
			//System.out.println(talks[(conversationTime/40) % talks.length]);
			theEntity.setCustomNameTag(talks[currentTalk]);
		}
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
	   return true;
	}
}
