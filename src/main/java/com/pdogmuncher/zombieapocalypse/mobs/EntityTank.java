package com.pdogmuncher.zombieapocalypse.mobs;

import org.lwjgl.input.Keyboard;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.events.KeyBindings;
import com.pdogmuncher.zombieapocalypse.events.KeyInputHandler;
import com.pdogmuncher.zombieapocalypse.tools.EntityBullet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import static io.netty.buffer.Unpooled.buffer;
import io.netty.buffer.ByteBuf;

public class EntityTank extends EntityAnimal
{

   private final EntityAIControlledByPlayer aiControlledByPlayer;
   private boolean leap;
   public double xStuff;
   public double yStuff;
   public double zStuff;
   private boolean shouldSwitch;
   public EntityPlayer player;
   int bulletsSoFar;
   int countdown;
   double inaccuracy = 0.3;
    public EntityTank(World par1World)
    {
        super(par1World);
        //this.texture = "/warpcraft/mobs/RoboSuit.png";
        this.tasks.addTask(1, this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 1.01F));
        setSize(1.0F, 1.0F);
    }
    @Override
    public boolean hitByEntity(Entity entity){
    	if (!entity.worldObj.isRemote && entity != this.riddenByEntity){
    		this.setDead();
        	this.dropItem(ZombieApocalypse.aluminumSheet, 10);
    	}
    	
    	return true;
    }
    @Override
    public void damageEntity(DamageSource source, float cause){
    	super.damageEntity(source, cause);
    	if (!worldObj.isRemote){
    		this.setDead();
    	}
    }
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.00D);
        //this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0f);
        
    }
    
    public boolean isAIEnabled()
    {
        return true;
    }

    protected void updateAITasks()
    {
        super.updateAITasks();
    }



    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
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
        this.onUpdate();
        //onUpdate();
        if(ridingEntity == null)
        {
           return;
        }
        ridingEntity.updateRiderPosition();
        xStuff = riddenByEntity.posX;
		yStuff = riddenByEntity.posY;
		zStuff = riddenByEntity.posZ;
    }
    
    public double getMountedYOffset()
    {
        return (double)height * 0.0D + 0.75D;
    }
    
    public boolean interact(EntityPlayer entityplayer)
   {
    	System.out.println("Interaction");
        if(riddenByEntity != null && (riddenByEntity instanceof EntityPlayer) && riddenByEntity != entityplayer)
        {
            return true;
        }
        if(!worldObj.isRemote)
        {
        	player = entityplayer;
            entityplayer.mountEntity(this);
        }
        return true;
    }
    
    public void moveEntity(double d, double d1, double d2)
    {
        if (riddenByEntity != null && (riddenByEntity instanceof EntityPlayer))
        {
              motionX = 0;
              motionY = 0;
              motionZ = 0;
              //prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
              //prevRotationPitch = rotationPitch = 0.0F;                                                   
              if(leap)
              {//have it fly up a bit
                   	motionX += riddenByEntity.motionX * 0.0D;
                    motionZ += riddenByEntity.motionZ * 0.0D;
                    motionY += 0.0D;
                    leap = false;
              }
              else
              {//regular run on the ground movement
                   	motionX += riddenByEntity.motionX * 10.0D;
                   	motionY += riddenByEntity.motionY * 10.0D;
                    motionZ += riddenByEntity.motionZ * 10.0D;
                    EntityPlayer entityplayer = (EntityPlayer)riddenByEntity;
              /*if(((EntityPlayer)entityplayer). && !isJumping)
            {
                 motionY +=  0.1D;
            }*/
              
              }
              if(isCollidedHorizontally)
              {
     
            	  if(onGround){
            		  motionY += 0.6F;     //let it try to jump over obstacle
                  }
              }
   		   super.moveEntity(motionX, motionY, motionZ);
              //this.setRotation(riddenByEntity.rotationPitch, riddenByEntity.rotationYaw);
              //super.moveEntity(motionX, motionY, motionZ);
        }
        else
        {
        	super.moveEntity(d, d1, d2);
        }
    }
    
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
    }

    protected String getLivingSound()
    {
        return "none";
    }

    protected String getHurtSound()
    {
        return "none";
    }

    protected String getDeathSound()
    {
        return "none";
    }
    
    public void attackEntityWithRangedAttack(EntityLiving par1EntityLiving)
    {
        EntityBullet var2 = new EntityBullet(this.worldObj, this, 5, 10, false);
        double var3 = par1EntityLiving.posX - this.posX;
        double var5 = par1EntityLiving.posY + (double)par1EntityLiving.getEyeHeight() - 1.100000023841858D - var2.posY;
        double var7 = par1EntityLiving.posZ - this.posZ;
        float var9 = MathHelper.sqrt_double(var3 * var3 + var7 * var7) * 0.2F;
        var2.setThrowableHeading(var3, var5 + (double)var9, var7, 1.6F, 12.0F);
        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.worldObj.spawnEntityInWorld(var2);
    }
    
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
    }

    protected float getSoundVolume()
    {
        return 0.4F;
    }
    
    public EntityAIControlledByPlayer getAIControlledByPlayer()
    {
        return this.aiControlledByPlayer;
    }

   @Override
   public EntityAgeable createChild(EntityAgeable var1) {
      // TODO Auto-generated method stub
      return new EntityTank(this.worldObj);
   }
   public void onUpdate(){
	   if (riddenByEntity != null && riddenByEntity instanceof EntityPlayer && this.worldObj != null && !this.worldObj.isRemote){
		   ((EntityPlayer)riddenByEntity).addPotionEffect(new PotionEffect(Potion.resistance.id, 2, 4));
		   this.updateRiderPosition();
		   //this.moveEntity(0, 0, 0);
		   if (countdown > 0){
			   countdown --;
		   }
		   if (KeyBindings.vehicleAbility.isKeyDown() && !worldObj.isRemote && this.riddenByEntity!=null && countdown < 1){
			   EntityBullet p = new EntityBullet(worldObj, (EntityPlayer)this.riddenByEntity, 5, 15, false);
	        	
	            worldObj.spawnEntityInWorld(p);
	            p.addVelocity(p.motionX * 3, p.motionY * 3, p.motionZ * 3);
	            //p.posX+=p.motionX;
	            //p.posZ+= p.motionZ;
	            //p.posY+= p.motionY;
	            p.damage = 5;
	            bulletsSoFar++;
	            double miss = ((Math.random() * (this.inaccuracy * 2)) - this.inaccuracy);
	            p.motionX += miss;
	            miss = ((Math.random() * (this.inaccuracy * 2)) - this.inaccuracy);
	            p.motionY += miss;
	            miss = ((Math.random() * (this.inaccuracy * 2)) - this.inaccuracy);
	            p.motionZ += miss;
	            this.playSound("random.explode", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	            if (bulletsSoFar > 100){
	            	countdown = 200;
		            bulletsSoFar = 0;
	            }
		   }
		   if (this.worldObj.isRemote){
			   //player = (EntityPlayer)riddenByEntity;
		   }
		   /*if (!this.worldObj.isRemote && player != null){
			   this.rotationYaw = player.rotationYaw;
			   /*player = this.worldObj.getClosestPlayerToEntity(this, 50);
			   player.posX = this.posX;
			   player.posZ = this.posZ;
			   //player.setPositionAndUpdate(posX, riddenByEntity.posY, posZ);
			   
		   }*/
	   }
   }
   @Override
   public void onEntityUpdate(){
	   
   }
}
