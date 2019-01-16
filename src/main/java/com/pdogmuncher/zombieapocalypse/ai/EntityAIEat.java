package com.pdogmuncher.zombieapocalypse.ai;

import java.util.Random;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.mobs.CitizenFemale;
import com.pdogmuncher.zombieapocalypse.mobs.Survivor;
import com.pdogmuncher.zombieapocalypse.tools.EntityBullet;

public class EntityAIEat extends EntityAIBase{
private final Survivor theEntity;
	int eatTime = 0;
	public EntityAIEat(Survivor survivor)
	{
	   theEntity = survivor;
	   setMutexBits(5);

	    // DEBUG
	    //System.out.println("EntityAIPanicHerdAnimal constructor()");
	}

	@Override
	public boolean shouldExecute()
	{
		if (theEntity.getHealth() == theEntity.getMaxHealth() || theEntity.foodLeft < 1)
	    {
	        return false;
	    }
	    else
	    {
	    	System.out.println("Eating");
	    	theEntity.setCurrentItemOrArmor(0, new ItemStack(Items.bread));
	    	eatTime = 0;
	    	theEntity.foodLeft--;
	    	theEntity.setHealth(theEntity.getMaxHealth());
	    	return true;
	    }    
	}
	@Override
	public boolean continueExecuting()
	{
		if (theEntity.worldObj.isRemote){
			return true;
		}
		eatTime++;
		//System.out.println("Food: " + eatTime);
		if (eatTime > 20){
			theEntity.setCurrentItemOrArmor(0, new ItemStack(ZombieApocalypse.rifle));
			return false;
		}
		return true;
	}
}
