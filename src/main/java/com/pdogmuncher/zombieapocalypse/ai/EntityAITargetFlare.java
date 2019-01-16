package com.pdogmuncher.zombieapocalypse.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.google.common.base.Predicate;
import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

public class EntityAITargetFlare extends EntityAINearestAttackableTarget
{
    private EntityCreature theTameable;
    private static final String __OBFID = "CL_00001623";

    public EntityAITargetFlare(EntityCreature p_i45876_1_, Class p_i45876_2_, boolean p_i45876_3_)
    {
        super(p_i45876_1_, p_i45876_2_, p_i45876_3_, false);
        this.theTameable = p_i45876_1_;
    }
    public boolean isSuitableTarget(EntityLivingBase base, boolean is){
    	if (base instanceof EntityPlayer){
    		if (((EntityPlayer)base).getCurrentEquippedItem() != null){
    			if (((EntityPlayer)base).getCurrentEquippedItem().getItem() != ZombieApocalypse.flare){
        			return false;
        		}
    			if (((EntityPlayer)base).getCurrentEquippedItem().getTagCompound() == null){
    				((EntityPlayer)base).getCurrentEquippedItem().setTagCompound(new NBTTagCompound());
    			}
    			if (!((EntityPlayer)base).getCurrentEquippedItem().getTagCompound().getBoolean("firing")){
    				return false;
    			}
    		}
    		else{
    			return false;
    		}
    	}
    	return super.isSuitableTarget(base, is);
    }
    public boolean continueExecuting(){
    	if (this.targetEntity instanceof EntityPlayer){
    		if (((EntityPlayer)this.targetEntity).getCurrentEquippedItem() != null){
        		if (((EntityPlayer)this.targetEntity).getCurrentEquippedItem().getItem() == ZombieApocalypse.flare){
        			if (((EntityPlayer)this.targetEntity).getCurrentEquippedItem().getTagCompound().getBoolean("firing"))
        				return super.continueExecuting();
        		}
        	}
    	}
    	
    	return false;
    }
}
