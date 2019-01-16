package com.pdogmuncher.zombieapocalypse.tools;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityKnife extends EntityThrowable
{
    public EntityKnife(World par1World)
    {
        super(par1World);
    }
    public EntityKnife(World par1World, EntityLivingBase par2EntityLivingBase)
    {
        super(par1World, par2EntityLivingBase);
    }
    public EntityKnife(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }
    final float explosionRadius = 1.5f;
    
    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition){
        // TODO Auto-generated method stub
    	//this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, false);
    	//this.setDead();
    	if (par1MovingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY){
    		par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) this.getThrower()), 5);
    	}
    	if (!worldObj.isRemote){
    		this.dropItem(ZombieApocalypse.knife, 1);
    	}
    	
    	this.setDead();
    	
    }
    
	@Override
	protected float getGravityVelocity() 
	{
		return super.getGravityVelocity() * 2;
		//return 1.0f;
	}
    
}
