package com.pdogmuncher.zombieapocalypse.ai;

import java.util.Random;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.generation.BronzeGenerator;
import com.pdogmuncher.zombieapocalypse.mobs.CitizenFemale;
import com.pdogmuncher.zombieapocalypse.mobs.EntityUltraZombie;
import com.pdogmuncher.zombieapocalypse.mobs.Survivor;
import com.pdogmuncher.zombieapocalypse.tools.EntityBullet;

public class EntityAISwarm extends EntityAIBase{
private final EntityUltraZombie theEntity;
	int eatTime = 0;
	double movePosX;
	double movePosY;
	double movePosZ;
	public EntityAISwarm(EntityUltraZombie entityUltraZombie)
	{
	   theEntity = entityUltraZombie;
	   setMutexBits(1);
	   
	    // DEBUG
	    //System.out.println("EntityAIPanicHerdAnimal constructor()");
	}

	@Override
	public boolean shouldExecute()
	{
		if (theEntity.getPosition().distanceSq(0, this.theEntity.posY, 0) < 5 || theEntity.worldObj.getWorldType() != ZombieApocalypse.APOCALYPSE)
	    {
	        return false;
	    }
		else if (theEntity.worldObj.getTotalWorldTime() < 12000){
			return false;
		}
	    else
	    {
	    	
	    	BlockPos blockpos = this.theEntity.func_180486_cf();
            Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, new Vec3((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ()));

            if (vec3 == null)
            {
                return false;
            }
            else
            {
                this.movePosX = vec3.xCoord;
                this.movePosY = vec3.yCoord;
                this.movePosZ = vec3.zCoord;
                return true;
            }
	    }    
	}
	public void startExecuting(){
		theEntity.getNavigator().tryMoveToXYZ(movePosX, movePosY, movePosZ, 0.5f);
	}
	@Override
	public boolean continueExecuting()
	{
		
		return !this.theEntity.getNavigator().noPath();
		//return true;
	}
}
