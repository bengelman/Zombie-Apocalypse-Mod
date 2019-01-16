package com.pdogmuncher.zombieapocalypse.mobs;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.ai.EntityAIEat;
import com.pdogmuncher.zombieapocalypse.ai.EntityAIShoot;
import com.pdogmuncher.zombieapocalypse.generation.BronzeGenerator;

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
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class Survivor extends EntityAnimal{
	public int recharge;
    public boolean gender;
    public int foodLeft;
    int tradeIndex;
    static ItemPair[] trades = new ItemPair[]{
    	new ItemPair(Items.carrot, Items.chainmail_leggings),
    	new ItemPair(Items.bread, Items.chainmail_chestplate),
    	new ItemPair(Items.cooked_chicken, Items.melon_seeds),
    	new ItemPair(Items.cooked_beef, Items.name_tag),
    	new ItemPair(Items.pumpkin_pie, Items.saddle),
    	new ItemPair(Items.cake, Items.iron_horse_armor)
    };
	public Survivor(World par1World)
    {
        super(par1World);
        this.setSize(0.8F, 2.0F);
        this.setupAI();
        this.setCurrentItemOrArmor(0, new ItemStack(ZombieApocalypse.rifle));
        this.setCurrentItemOrArmor(3, new ItemStack(ZombieApocalypse.backpack));
        this.setCurrentItemOrArmor(4, new ItemStack(ZombieApocalypse.light));
        this.gender = new Random().nextBoolean();
        tradeIndex = Math.abs(new Random().nextInt()) % trades.length;
        foodLeft = 10;
    }
	public Survivor(World par1World, boolean gender)
    {
        super(par1World);
        this.setSize(0.8F, 2.0F);
        this.setupAI();
        this.setCurrentItemOrArmor(0, new ItemStack(ZombieApocalypse.rifle));
        this.setCurrentItemOrArmor(3, new ItemStack(ZombieApocalypse.backpack));
        this.setCurrentItemOrArmor(4, new ItemStack(ZombieApocalypse.light));
        this.gender = gender;
        tradeIndex = Math.abs(new Random().nextInt()) % trades.length;
        foodLeft = 10;
    }
	@Override
    public void writeEntityToNBT(NBTTagCompound tag){
    	super.writeEntityToNBT(tag);
    	tag.setBoolean("gender", gender);
    	tag.setInteger("foodLeft", foodLeft);
    	tag.setInteger("tradeIndex", tradeIndex);
    }
	@Override
    public void readEntityFromNBT(NBTTagCompound tag){
    	super.readEntityFromNBT(tag);
    	gender = tag.getBoolean("gender");
    	foodLeft = tag.getInteger("foodLeft");
    	tradeIndex = tag.getInteger("tradeIndex");
    }
    private boolean leap;
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.67f);
        //this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0f);
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
	   this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 100, 0.67f));
	   
	   this.tasks.addTask(3, new EntityAIMate(this, 0.67));
	   
	   this.tasks.addTask(3, new EntityAIEat(this));
	   //this.tasks.addTask(4, new EntityAIFollowOwner(this, 0.67f, 10.0F, 2.0F));
	   this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	   this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
	   this.tasks.addTask(7, new EntityAILookIdle(this));
	   //this.targetTasks.addTask(0, new EntityAIOwnerHurtTarget(this));
	   //this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
	   this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
	   this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityUltraZombie.class, true));
	   this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityMob.class, true));
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

	public boolean interact(EntityPlayer par1EntityPlayer) {
		super.interact(par1EntityPlayer);
		if (worldObj.isRemote){
			return false;
		}
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
		if (itemstack!=null){
			if (itemstack.getItem() == Items.emerald){
				this.setInLove(par1EntityPlayer);
				
				if (!par1EntityPlayer.capabilities.isCreativeMode){
					itemstack.stackSize--;
				}
				if (itemstack.stackSize <= 0){
					itemstack = null;
				}
				return false;
			}
		}
		if (itemstack!= null){
			if (itemstack.getItem() == trades[tradeIndex].firstItem && par1EntityPlayer.inventory.hasItem(Items.emerald)){
				par1EntityPlayer.inventory.consumeInventoryItem(trades[tradeIndex].firstItem);
				par1EntityPlayer.inventory.consumeInventoryItem(Items.emerald);
				par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(trades[tradeIndex].secondItem));
				this.foodLeft += tradeIndex + 1;
				par1EntityPlayer.addChatComponentMessage(new ChatComponentText("Thanks! Hope you like the " + new ItemStack(this.trades[tradeIndex].secondItem).getDisplayName()));
				return false;
			}
		}
		
		
		String message = "Hello, traveller! I would be happy to give you a ";
		message += new ItemStack(this.trades[tradeIndex].secondItem).getDisplayName();
		message += " in exchange for an emerald and a ";
		message += new ItemStack(this.trades[tradeIndex].firstItem).getDisplayName();
		par1EntityPlayer.addChatComponentMessage(new ChatComponentText(message));
		System.out.println("Prompting");
		return false;
	}
	@Override
    protected float getSoundPitch(){
    	return 1.0f;
    }
	@Override
	public boolean canDespawn(){
		return false;
	}
	@Override
	public EntityAgeable createChild(EntityAgeable arg0) {
		// TODO Auto-generated method stub
		return new Survivor(this.worldObj);
	}
	@Override
	public boolean canMateWith(EntityAnimal animal){
		if (!(animal instanceof Survivor)){
			return false;
		}
		Survivor vivor = (Survivor) animal;
		if (vivor.gender == !gender){
			return true;
		}
		return false;
		
	}
	
}
class ItemPair{
	public Item firstItem;
	public Item secondItem;
	public ItemPair(Item one, Item two){
		firstItem = one;
		secondItem = two;
	}
}
