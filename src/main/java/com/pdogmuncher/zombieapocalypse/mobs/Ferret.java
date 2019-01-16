package com.pdogmuncher.zombieapocalypse.mobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Ferret extends EntityWolf
{
    public Ferret(World par1World)
    {
        super(par1World);
        this.setSize(0.9F, 1.3F);
        this.setupAI();
    }
    
    private boolean leap;
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.67D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0f);
        
    }

    @Override
    protected String getLivingSound()
    {
    	
        return "mob.silverfish.say";//this refers to:yourmod/sound/YourSound
    	//return "mob.wolf.bark";
    }

    @Override
    protected String getHurtSound()
    {
        return "mob.wolf.hurt";//this refers to:yourmod/sound/optionalFile/YourSound
    }

    @Override
    protected String getDeathSound()
    {
        return "mob.silverfish.hurt";
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }

	@Override
	public EntityWolf createChild(EntityAgeable arg0) {
		// TODO Auto-generated method stub
		Ferret child = new Ferret(this.worldObj);
		child.setGrowingAge(0);
		return child;
	}
	protected void setupAI()
	{
	   clearAITasks(); // clear any tasks assigned in super classes
	   //tasks.addTask(0, new EntityAISwimming(this));
	   //tasks.addTask(1, new EntityAIPanicHerdAnimal(this));
	   // the leap and the collide together form an actual attack
	   //tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
	   //this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false));
	   this.tasks.addTask(0, new EntityAISwimming(this));
	   this.tasks.addTask(1, new EntityAISit(this));
	   this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
	   this.tasks.addTask(3, new EntityAIAttackOnCollide(this, .3f, true));
	   this.tasks.addTask(4, new EntityAIMate(this, 0.67D));
	   this.tasks.addTask(5, new EntityAITempt(this, 0.67D, Items.rabbit, false));
	   this.tasks.addTask(6, new EntityAIFollowOwner(this, .67f, 10.0F, 2.0F));
	   this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	   this.tasks.addTask(8, new EntityAIWander(this, 0.67D));
	   this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
	   this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
	   this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
	   this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityRabbit.class, true));
	   this.setAIMoveSpeed(0.67f);
	   //targetTasks.addTask(0, new EntityAIHurtByTargetHerdAnimal(this, true));      
	}
	public boolean isAiEnabled(){
		return true;
	}
	protected void clearAITasks()
	{
	   tasks.taskEntries.clear();
	   targetTasks.taskEntries.clear();
	}
	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
    {
	    int i = this.isTamed() ? 6 : 4;
	    return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) i);
    }
	@Override
	public void onStruckByLightning(EntityLightningBolt bolt){
		super.onStruckByLightning(bolt);
		if (!this.worldObj.isRemote){
			UberFerret fer = new UberFerret(this.worldObj);
			fer.setPosition(this.posX, this.posY, this.posZ);
			worldObj.spawnEntityInWorld(fer);
			this.setDead();
		}
	}
	/*
	public boolean canBeSteered()
    {
        return true;
    }
    
    public void updateRidden()
    {
        if(ridingEntity.isDead)
        {
            ridingEntity = null;
            return;
        }
        motionX = 0.0D;
        motionZ = 0.0D;
        motionY = 0.0D;
        onUpdate();
        //onUpdate();
        if(ridingEntity == null)
        {
           return;
        }
        ridingEntity.updateRiderPosition();
    }
    
    public double getMountedYOffset()
    {
        return (double)height * 0.0D + 1.5D;
    }
    
    public boolean interact(EntityPlayer entityplayer)
   {
        if(riddenByEntity != null && (riddenByEntity instanceof EntityPlayer) && riddenByEntity != entityplayer)
        {
            return true;
        }
        if(!worldObj.isRemote)
        {
            entityplayer.mountEntity(this);
        }
        return true;
    }
    @Override
    public void moveEntity(double d, double d1, double d2)
    {
        if (riddenByEntity != null && (riddenByEntity instanceof EntityPlayer))
        {
              
              prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
              prevRotationPitch = rotationPitch = 0.0F;                                                   
                if(leap)
                {//have it fly up a bit
                   motionX += riddenByEntity.motionX * 0.0D;
                    motionZ += riddenByEntity.motionZ * 0.0D;
                    motionY += 0.0D;
                leap = false;
                }else
                {//regular run on the ground movement
                   motionX += riddenByEntity.motionX * 3.0D;
                    motionZ += riddenByEntity.motionZ * 3.0D;
                EntityPlayer entityplayer = (EntityPlayer)riddenByEntity;
              
                }
                if(isCollidedHorizontally)
                {
     
                            if(onGround)                    //else not fly
                            {
                               motionY += 0.6F;     //let it try to jump over obstacle
                            }
                }
 
            super.moveEntity(motionX, motionY, motionZ);
        }else
        {
           super.moveEntity(d, d1, d2);
        }
    }
    public EntityAIControlledByPlayer getAIControlledByPlayer()
    {
        return this.aiControlledByPlayer;
    }
    public void onUpdate(){
    	if (riddenByEntity != null && riddenByEntity instanceof EntityPlayer){
    		this.moveEntity(0, 0, 0);
    	}
    }*/
	public boolean interact(EntityPlayer par1EntityPlayer) {
		//super.interact(par1EntityPlayer);
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
		if (itemstack != null){
			if (itemstack.getItem() == Items.rabbit){
				this.setInLove(par1EntityPlayer);
				if (!par1EntityPlayer.capabilities.isCreativeMode){
					itemstack.stackSize--;
				}
				
				if (itemstack.stackSize <= 0){
					itemstack = null;
				}
				if (!this.isTamed()){
					this.setTamed(true);
					this.setOwnerId(par1EntityPlayer.getPersistentID().toString());
					if (!worldObj.isRemote){
						
					}
					
				}
				else{
					//System.out.println(this.getOwnerId());
				}
			}
		}
		if (this.isTamed()){
			this.setSitting(!this.isSitting());
			this.isJumping = false;
			
            //this.setPathToEntity((PathEntity)null);
            this.navigator.setPath(null, 0);
            this.setAttackTarget(null);
            this.navigator.clearPathEntity();
            this.setRevengeTarget(null);
            //this.setTarget((Entity)null);
            //this.setAttackTarget((EntityLivingBase)null);
		}
		return false;
	}
}


