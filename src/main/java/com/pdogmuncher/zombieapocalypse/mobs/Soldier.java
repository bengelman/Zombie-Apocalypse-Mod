package com.pdogmuncher.zombieapocalypse.mobs;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.ai.EntityAIShoot;

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
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
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

public class Soldier extends CitizenFemale{
	public int recharge;
	public Soldier(World par1World)
    {
        super(par1World);
        this.setSize(0.8F, 2.0F);
        this.setupAI();
        this.setCurrentItemOrArmor(3, new ItemStack(ZombieApocalypse.kevlarChestplate));
        this.setCurrentItemOrArmor(0, new ItemStack(ZombieApocalypse.m4));
    }
    
    private boolean leap;
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
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
	   this.tasks.addTask(1, new EntityAIShoot(this));
	   this.tasks.addTask(2, new EntityAIMate(this, 0.67));
	   this.tasks.addTask(3, new EntityAITempt(this, 0.67, Items.emerald, false));
	   this.tasks.addTask(4, new EntityAIFollowOwner(this, 0.67f, 10.0F, 2.0F));
	   this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	   this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
	   this.tasks.addTask(7, new EntityAILookIdle(this));
	   this.targetTasks.addTask(0, new EntityAIOwnerHurtTarget(this));
	   this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
	   this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
	   this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityUltraZombie.class, true));
	   this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityMob.class, true));
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
	public void onEntityUpdate(){
		super.onEntityUpdate();
		if (recharge>0){
			recharge--;
		}
	}

	@Override
	public EntityAgeable createChild(EntityAgeable arg0) {
		// TODO Auto-generated method stub
		return new Soldier(this.worldObj);
	}
	public boolean interact(EntityPlayer par1EntityPlayer) {
		//super.interact(par1EntityPlayer);
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
			if (!this.isTamed()){
				this.setTamed(true);
				this.setOwnerId(par1EntityPlayer.getPersistentID().toString());
				if (!worldObj.isRemote){
					par1EntityPlayer.addChatMessage(new ChatComponentText("You have recruited a soldier"));
				}
				
			}
			else{
				System.out.println(this.getOwnerId());
			}
		}
		
		return false;
	}
	@Override
	public boolean canMateWith(EntityAnimal animal){
		return animal instanceof CitizenFemale && !(animal instanceof Citizen) && !(animal instanceof Soldier);
	}
	@Override
    protected float getSoundPitch(){
    	return 1.0f;
    }
}
