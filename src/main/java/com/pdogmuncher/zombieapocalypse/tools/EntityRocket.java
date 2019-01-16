package com.pdogmuncher.zombieapocalypse.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityRocket extends EntityThrowable
{
    public EntityRocket(World par1World)
    {
        super(par1World);
    }
    public EntityRocket(World par1World, EntityLivingBase par2EntityLivingBase)
    {
        super(par1World, par2EntityLivingBase);
    }
    public EntityRocket(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }
    final float explosionRadius = 2.5f;
    
    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition){
        // TODO Auto-generated method stub
    	this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, false);
    	this.setDead();
    	//this.setDead();
    }
    
	@Override
	protected float getGravityVelocity() 
	{
		return 0.05f;
	}
	@Override
	public void onEntityUpdate(){
		double motionX = rand.nextGaussian() * 0.02D;
	    double motionY = rand.nextGaussian() * 0.02D;
	    double motionZ = rand.nextGaussian() * 0.02D;
	    worldObj.spawnParticle(
	          EnumParticleTypes.EXPLOSION_NORMAL, 
	          posX + rand.nextFloat() * width * 2.0F - width, 
	          posY + 0.5D + rand.nextFloat() * height, 
	          posZ + rand.nextFloat() * width * 2.0F - width, 
	          motionX, 
	          motionY, 
	          motionZ);
	}
    
}
