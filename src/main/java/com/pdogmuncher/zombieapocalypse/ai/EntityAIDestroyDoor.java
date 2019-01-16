package com.pdogmuncher.zombieapocalypse.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIDoorInteract;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class EntityAIDestroyDoor extends EntityAIDoorInteract
{
    private int breakingTime;
    private int field_75358_j = -1;

    public EntityAIDestroyDoor(EntityLiving par1EntityLiving)
    {
        super(par1EntityLiving);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return !super.shouldExecute() ? false : (this.theEntity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")/* ? false : !this.doorBlock.isDoorOpen(this.theEntity.worldObj, this.theEntity.posX, this.theEntity.posY, this.theEntity.posZ)*/);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
        this.breakingTime = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        double d0 = this.theEntity.getDistanceSq((double)this.theEntity.posX, (double)this.theEntity.posY, (double)this.theEntity.posZ);
        return this.breakingTime <= 240 && d0 < 4.0D;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        super.resetTask();
        //this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.getEntityId(), this.theEntity.posX, this.theEntity.posY, this.theEntity.posZ, -1);
        //this.theEntity.worldObj.
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        super.updateTask();

        if (this.theEntity.getRNG().nextInt(20) == 0)
        {
            this.theEntity.worldObj.playAuxSFX(1010, new BlockPos(this.theEntity.posX, this.theEntity.posY, this.theEntity.posZ), 0);
        }

        ++this.breakingTime;
        int i = (int)((float)this.breakingTime / 240.0F * 10.0F);

        if (i != this.field_75358_j)
        {
            //this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.getEntityId(), this.entityPosX, this.entityPosY, this.entityPosZ, i);
            this.field_75358_j = i;
        }

        if (this.breakingTime == 240)
        {
        	//this.theEntity.worldObj.playSo
            //this.theEntity.worldObj.setBlockToAir(this.entityPosX, this.entityPosY, this.entityPosZ);
            this.theEntity.worldObj.setBlockState(new BlockPos(this.theEntity.posX, this.theEntity.posY, this.theEntity.posZ), Blocks.air.getDefaultState());
            //this.theEntity.worldObj.playAuxSFX(1012, this.entityPosX, this.entityPosY, this.entityPosZ, 0);
            //this.theEntity.worldObj.playAuxSFX(2001, this.entityPosX, this.entityPosY, this.entityPosZ, this.targetDoor.blockID);
        }
    }
}
