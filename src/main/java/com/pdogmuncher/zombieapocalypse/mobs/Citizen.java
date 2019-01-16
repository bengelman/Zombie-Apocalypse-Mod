package com.pdogmuncher.zombieapocalypse.mobs;

import java.util.Random;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.ai.EntityAIGoHome;
import com.pdogmuncher.zombieapocalypse.tools.Firearm;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class Citizen extends CitizenFemale{
	public int recharge;
	public Citizen(World par1World)
    {
        super(par1World);
        this.setSize(0.7F, 1.9F);
        this.setupAI();
        this.setCurrentItemOrArmor(0, new ItemStack(ZombieApocalypse.bronzeChestplate));
    }
    
    private boolean leap;
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.67f);
        
        //this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0f);
    }

    @Override
    protected String getLivingSound()
    {
    	
        //return "mob.silverfish.say";//this refers to:yourmod/sound/YourSound
    	return "mob.villager.idle";
    }

    @Override
    protected String getHurtSound()
    {
        return "zombieapocalypse:sound.soldierhit";//this refers to:yourmod/sound/optionalFile/YourSound
    }

    @Override
    protected String getDeathSound()
    {
        return "zombieapocalypse:sound.soldierhit";
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
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
	   this.tasks.addTask(1, new EntityAIMate(this, 0.5));
	   this.tasks.addTask(2, new EntityAIOpenDoor(this, true));
	   this.tasks.addTask(4, new EntityAITempt(this, 0.5, Items.emerald, false));
	   this.tasks.addTask(3, new net.minecraft.entity.ai.EntityAIMoveIndoors(this));
	   //this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityUltraZombie.mobSelector, 18.0f, 0.67D, 0.67D));
	   
	   this.tasks.addTask(4, new EntityAITalk(this));
	   this.tasks.addTask(5, new EntityAIPanic(this, 0.5));
	   this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	   this.tasks.addTask(7, new EntityAIWander(this, 0.5D));
	   this.tasks.addTask(8, new EntityAILookIdle(this));
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
	public EntityAgeable createChild(EntityAgeable arg0) {
		// TODO Auto-generated method stub
		if (new Random().nextBoolean()){
			return new Citizen(this.worldObj);
		}
		return new CitizenFemale(this.worldObj);
	}
	public boolean interact(EntityPlayer par1EntityPlayer) {
		super.interact(par1EntityPlayer);
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
		if (itemstack == null){
			return false;
		}
		if (itemstack.getItem() == Items.emerald){
			this.setInLove(par1EntityPlayer);
			
			if (!par1EntityPlayer.capabilities.isCreativeMode){
				itemstack.stackSize--;
			}
			if (itemstack.stackSize <= 0){
				itemstack = null;
			}
		}
		else if (itemstack.getItem() instanceof Firearm && !worldObj.isRemote){
			par1EntityPlayer.inventory.consumeInventoryItem(itemstack.getItem());
			Survivor vivor = new Survivor (this.worldObj, false);
			vivor.setPosition(this.posX, this.posY, this.posZ);
			worldObj.spawnEntityInWorld(vivor);
			this.setDead();
		}
		
		return false;
	}
	@Override
	public boolean canMateWith(EntityAnimal animal){
		return animal instanceof CitizenFemale && !(animal instanceof Citizen)  && !(animal instanceof Soldier);
	}
	@Override
    protected float getSoundPitch(){
    	return 1.0f;
    }
}
