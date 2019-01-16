package com.pdogmuncher.zombieapocalypse.mobs;

import com.pdogmuncher.zombieapocalypse.ai.EntityAIDestroyDoor;
import com.pdogmuncher.zombieapocalypse.ai.EntityAISwarm;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityUltraZombie extends EntityAnimal implements IMob
{
    public EntityUltraZombie(World par1World)
    {
        super(par1World);
        this.setSize(0.8F, 1.9F);
        this.setupAI();
        
        //this.setCurrentItemOrArmor(3, Items.leather_boots);
        //this.setCurrentItemOrArmor(3, new ItemStack(Items.leather_chestplate));
    }
    @Override
    public boolean canPickUpLoot(){
    	return true;
    }
    @Override
    public boolean canDespawn(){
    	return false;
    }
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
        //this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0f);
        
    }
    
    @Override
    protected String getLivingSound()
    {
    	
        return "mob.zombie.say";//this refers to:yourmod/sound/YourSound
    }

    @Override
    protected String getHurtSound()
    {
        return "mob.zombie.hurt";//this refers to:yourmod/sound/optionalFile/YourSound
    }

    @Override
    protected String getDeathSound()
    {
        return "mob.zombie.death";
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
	   this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.67D, false));
	   this.tasks.addTask(2, new EntityAIAttackOnCollide(this, CitizenFemale.class, 0.67D, true));
	   this.tasks.addTask(2, new EntityAIAttackOnCollide(this, Survivor.class, 0.67D, true));
	   this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.67f));
	   this.tasks.addTask(3, new EntityAIDestroyDoor(this));
	   this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.67D));
	   this.tasks.addTask(5, new EntityAISwarm(this));
	   this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 0.67D, false));
	   this.tasks.addTask(7, new EntityAIWander(this, 0.67D));
	   this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 0.67F));
	   this.tasks.addTask(9, new EntityAILookIdle(this));
	   this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
	   this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	   this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, Survivor.class, true));
	   this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, CitizenFemale.class, true));
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
		//worldObj.spawnEntityInWorld(new EntityLightningBolt(this.worldObj, par1Entity.posX, par1Entity.posY, par1Entity.posZ));
	    DamageSource d = new DamageSource("Ultra Zombie");
	    d.damageType = "uzombie";
		return par1Entity.attackEntityFrom(d, (float) 5);
    }
	@Override
	public void onLivingUpdate(){
		super.onLivingUpdate();
		if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL){
			this.setDead();
		}
	}
	@Override
	public boolean isSprinting(){
		return true;
	}
	//@Override
	public EntityAgeable createChild(EntityAgeable arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void collideWithEntity(Entity e){
		super.collideWithEntity(e);
		if (e instanceof EntityPlayer || e instanceof CitizenFemale){
			this.attackEntityAsMob(e);
		}
	}
	
}

