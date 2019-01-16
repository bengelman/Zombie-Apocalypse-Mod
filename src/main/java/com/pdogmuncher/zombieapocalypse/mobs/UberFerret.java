package com.pdogmuncher.zombieapocalypse.mobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class UberFerret extends EntityGiantZombie implements IBossDisplayData
{
    public UberFerret(World par1World)
    {
        super(par1World);
        this.setSize(10.9F, 11.3F);
        this.setupAI();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.60D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0f);
        
    }

    @Override
    protected String getLivingSound()
    {
    	
        return "mob.silverfish.say";//this refers to:yourmod/sound/YourSound
    }

    @Override
    protected String getHurtSound()
    {
        return "mob.wolf.hurt";//this refers to:yourmod/sound/optionalFile/YourSound
    }

    @Override
    protected String getDeathSound()
    {
        return "mob.wolf.hurt";
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }
/*
	@Override
	public EntityWolf createChild(EntityAgeable arg0) {
		// TODO Auto-generated method stub
		UberFerret child = new UberFerret(this.worldObj);
		child.setGrowingAge(0);
		return child;
	}
	*/
	protected void setupAI()
	{
	   clearAITasks(); // clear any tasks assigned in super classes
	   //tasks.addTask(0, new EntityAISwimming(this));
	   //tasks.addTask(1, new EntityAIPanicHerdAnimal(this));
	   // the leap and the collide together form an actual attack
	   //tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
	   //this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false));
	   //this.getNavigator().setBreakDoors(true);
	   //this.applyEntityAttributes();
	   this.tasks.addTask(0, new EntityAISwimming(this));
	   this.tasks.addTask(1, new EntityAIBreakDoor(this));
	   this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
	   this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
	   this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
	   this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, 1.0D, false));
	   this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
	   this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	   this.tasks.addTask(7, new EntityAILookIdle(this));
	   this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
	   this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	   //targetTasks.addTask(0, new EntityAIHurtByTargetHerdAnimal(this, true));      
	}

	protected void clearAITasks()
	{
	   tasks.taskEntries.clear();
	   targetTasks.taskEntries.clear();
	}
	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
    {
	    //int i = this.isTamed() ? 4 : 2;
		worldObj.spawnEntityInWorld(new EntityLightningBolt(this.worldObj, par1Entity.posX, par1Entity.posY, par1Entity.posZ));
	    return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) 5);
    }
	@Override
	public IChatComponent getDisplayName(){
		ChatComponentText message = new ChatComponentText("Lightning Boss");
		ChatStyle colour = new ChatStyle();
		colour.setColor(EnumChatFormatting.LIGHT_PURPLE);
		
		message.setChatStyle(colour);
		return message;
		
	}
	@Override
	public void onStruckByLightning(EntityLightningBolt bolt){
		if (!worldObj.isRemote){
			this.setHealth(this.getHealth() + 10);
		}
	}
	@Override
	public void onLivingUpdate(){
		super.onLivingUpdate();
		BossStatus.setBossStatus(this, true);
	}
}


