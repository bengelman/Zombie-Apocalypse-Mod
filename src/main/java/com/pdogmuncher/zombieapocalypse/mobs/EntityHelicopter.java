package com.pdogmuncher.zombieapocalypse.mobs;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.events.KeyBindings;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityHelicopter extends EntityAnimal
{
private boolean field_70279_a;
private double field_70276_b;
private int boatPosRotationIncrements;
private double boatX;
private double boatY;
private double boatZ;
private double boatYaw;
private double boatPitch;

private double velocityX;
private double velocityY;
private double velocityZ;
private int fuel;
private EntityAIControlledByPlayer aiControlledByPlayer;
public EntityHelicopter(World par1World)
{
	 super(par1World);
	 this.field_70279_a = true;
	 this.field_70276_b = 0.07D;
	 this.preventEntitySpawning = true;
	 this.setSize(1.5F, 0.6F);
	 this.tasks.addTask(1, this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 1.01F));
	 
}
public boolean isAIEnabled()
{
    return true;
}

protected void updateAITasks()
{
    super.updateAITasks();
}
@Override
public double getYOffset(){
	return this.height / 2.0f;
}
@Override
public void damageEntity(DamageSource source, float amount){
	super.damageEntity(source, amount);
	if (this.getHealth() < 1){
		this.dropItem(ZombieApocalypse.car, 1);
	}
}
/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
protected boolean canTriggerWalking()
{
	 return false;
}
/**
	 * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
	 * pushable on contact, like boats or minecarts.
	 */

/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
public boolean canBePushed()
{
	 return true;
}
public EntityHelicopter(World par1World, double par2, double par4, double par6)
{
	 this(par1World);
	 this.setPosition(par2, par4 + (double)this.getYOffset(), par6);
	 this.motionX = 0.0D;
	 this.motionY = 0.0D;
	 this.motionZ = 0.0D;
	 this.prevPosX = par2;
	 this.prevPosY = par4;
	 this.prevPosZ = par6;
}
/**
	 * Returns the Y offset from the entity's position for any entity riding this one.
	 */
public double getMountedYOffset()
{
	 return (double)this.height * 1.0D - 0.30000001192092896D;
}
/**
	 * Called when the entity is attacked.
	 */
public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
{
	this.setDead();
	this.dropItem(ZombieApocalypse.helicopter, 1);
	return false;
	 /*if (this.func_85032_ar())
	 {
		 return false;
	 }
	 else if (!this.worldObj.isRemote && !this.isDead)
	 {
		 
		 this.setBeenAttacked();
		 if ((par1DamageSource.getEntity() instanceof EntityPlayer) && ((EntityPlayer)par1DamageSource.getEntity()).capabilities.isCreativeMode)
		 {
			 this.setDamageTaken(100);
		 }
		 
		 return true;
	 }
	 else
	 {
		 return true;
	 }*/
}
//@SideOnly(Side.CLIENT)
/**
	 * Setups the entity to do the hurt animation. Only used by packets in multiplayer.
	 */

/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
public boolean canBeCollidedWith()
{
	 return !this.isDead;
}
//@SideOnly(Side.CLIENT)
/**
	 * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
	 * posY, posZ, yaw, pitch
	 */
public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
{
	 if (this.field_70279_a)
	 {
		 this.boatPosRotationIncrements = par9 + 5;
	 }
	 else
	 {
		 double var10 = par1 - this.posX;
		 double var12 = par3 - this.posY;
		 double var14 = par5 - this.posZ;
		 double var16 = var10 * var10 + var12 * var12 + var14 * var14;
		 if (var16 <= 1.0D)
		 {
			 return;
		 }
		 this.boatPosRotationIncrements = 3;
	 }
	 this.boatX = par1;
	 this.boatY = par3;
	 this.boatZ = par5;
	 this.boatYaw = (double)par7;
	 this.boatPitch = (double)par8;
	 this.motionX = this.velocityX;
	 this.motionY = this.velocityY;
	 this.motionZ = this.velocityZ;
}
//@SideOnly(Side.CLIENT)
/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
public void setVelocity(double par1, double par3, double par5)
{
	 this.velocityX = this.motionX = par1;
	 this.velocityY = this.motionY = par3;
	 this.velocityZ = this.motionZ = par5;
}
/**
	 * Called to update the entity's position/logic.
	 */
public void onUpdate()
{
	 super.onUpdate();
	 if (this.riddenByEntity == null){
		 
		 return;
	 }
	 
	 if (!(this.riddenByEntity instanceof EntityPlayer)){
		 return;
	 }
	 if (((EntityPlayer)this.riddenByEntity).capabilities.isCreativeMode){
		 this.fuel = 1000;
	 }
	 if (fuel < 1){
		 if (Keyboard.isKeyDown(Keyboard.KEY_W) && this.worldObj.isRemote){
			 this.riddenByEntity.addChatMessage(new ChatComponentText("You must refuel your car with petroleum!"));
		 }
		 return;
	 }
	 if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
		 this.fuel--;
		 this.setVelocity(0, 0.5, 0);
	 }
	 if (this.riddenByEntity != null && !worldObj.isRemote && Keyboard.isKeyDown(Keyboard.KEY_D) && !this.onGround){
		 this.fuel--;
		 this.setVelocity(this.riddenByEntity.getLookVec().rotateYaw((float) -(Math.PI / 2)).xCoord, this.motionY, this.riddenByEntity.getLookVec().rotateYaw((float) -(Math.PI / 2)).zCoord);
		 if(isCollidedHorizontally)
         {

       	  if(onGround){
       		  motionY += 0.6F;     //let it try to jump over obstacle
             }
         }
	 }
	 if (this.riddenByEntity != null && !worldObj.isRemote && Keyboard.isKeyDown(Keyboard.KEY_A) && !this.onGround){
		 this.fuel--;
		 this.setVelocity(this.riddenByEntity.getLookVec().rotateYaw((float) (Math.PI / 2)).xCoord, this.motionY, this.riddenByEntity.getLookVec().rotateYaw((float) (Math.PI / 2)).zCoord);
		 if(isCollidedHorizontally)
         {

       	  if(onGround){
       		  motionY += 0.6F;     //let it try to jump over obstacle
             }
         }
	 }
	 if (this.riddenByEntity != null && !worldObj.isRemote && Keyboard.isKeyDown(Keyboard.KEY_W) && !this.onGround){
		 this.fuel--;
		 this.rotationYaw = (float) Math.toDegrees(Math.atan2(riddenByEntity.getLookVec().zCoord, riddenByEntity.getLookVec().xCoord));
		 this.setVelocity(this.riddenByEntity.getLookVec().xCoord, this.motionY, this.riddenByEntity.getLookVec().zCoord);
		 if(isCollidedHorizontally)
         {

       	  if(onGround){
       		  motionY += 0.6F;     //let it try to jump over obstacle
             }
         }
	 }
	 if (this.riddenByEntity != null && !worldObj.isRemote && Keyboard.isKeyDown(Keyboard.KEY_S) && !this.onGround){
		 
		 this.fuel--;
		 this.setVelocity(-this.riddenByEntity.getLookVec().xCoord, this.motionY, -this.riddenByEntity.getLookVec().zCoord);
		 if(isCollidedHorizontally)
         {

       	  if(onGround){
       		  motionY += 0.6F;     //let it try to jump over obstacle
             }
         }
	 }
	 /*
	 this.prevPosX = this.posX;
	 this.prevPosY = this.posY;
	 this.prevPosZ = this.posZ;
	 byte var1 = 5;
	 double var2 = 0.0D;
	 for (int var4 = 0; var4 < var1; ++var4)
	 {
		 double var5 = this.getEntityBoundingBox().minY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * (double)(var4 + 0) / (double)var1 - 0.125D;
		 double var7 = this.getEntityBoundingBox().minY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * (double)(var4 + 1) / (double)var1 - 0.125D;
		 AxisAlignedBB var9 = AxisAlignedBB.fromBounds(this.getEntityBoundingBox().minX, var5, this.getEntityBoundingBox().minZ, this.getEntityBoundingBox().maxX, var7, this.getEntityBoundingBox().maxZ);
		 
		 //if (this.worldObj.isAABBInMaterial(var9, Material.water))
		 {
			 //var2 += 1.0D / (double)var1;
		 //}
	 }
	 double var24 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
	 double var6;
	 double var8;
	 if (var24 > 0.26249999999999996D)
	 {
		 var6 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D);
		 var8 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D);
		 for (int var10 = 0; (double)var10 < 1.0D + var24 * 60.0D; ++var10)
		 {
			 double var11 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
			 double var13 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7D;
			 double var15;
			 double var17;
			 if (this.rand.nextBoolean())
			 {
				 var15 = this.posX - var6 * var11 * 0.8D + var8 * var13;
				 var17 = this.posZ - var8 * var11 * 0.8D - var6 * var13;
				 //this.worldObj.spawnParticle("splash", var15, this.posY - 0.125D, var17, this.motionX, this.motionY, this.motionZ);
			 }
			 else
			 {
				 var15 = this.posX + var6 + var8 * var11 * 0.7D;
				 var17 = this.posZ + var8 - var6 * var11 * 0.7D;
				 //this.worldObj.spawnParticle("splash", var15, this.posY - 0.125D, var17, this.motionX, this.motionY, this.motionZ);
			 }
		 }
	 }
	 double var12;
	 double var26;
	 if (this.worldObj.isRemote && this.field_70279_a)
	 {
		 if (this.boatPosRotationIncrements > 0)
		 {
			 var6 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
			 var8 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
			 var26 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
			 var12 = MathHelper.wrapAngleTo180_double(this.boatYaw - (double)this.rotationYaw);
			 this.rotationYaw = (float)((double)this.rotationYaw + var12 / (double)this.boatPosRotationIncrements);
			 this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
			 --this.boatPosRotationIncrements;
			 this.setPosition(var6, var8, var26);
			 this.setRotation(this.rotationYaw, this.rotationPitch);
		 }
		 else
		 {
			 var6 = this.posX + this.motionX;
			 var8 = this.posY + this.motionY;
			 var26 = this.posZ + this.motionZ;
			 this.setPosition(var6, var8, var26);
			 
			 this.motionX *= 0.9900000095367432D;
			 this.motionY *= 0.949999988079071D;
			 this.motionZ *= 0.9900000095367432D;
		 }
	 }
	 else
	 {
		 if (var2 < 1.0D)
		 {
			 var6 = var2 * 2.0D - 1.0D;
			 this.motionY += 0.03999999910593033D * var6;
		 }
		 else
		 {
			 if (this.motionY < 0.0D)
			 {
				 this.motionY /= 2.0D;
			 }
			 this.motionY += 0.007000000216066837D;
		 }
		 if (this.riddenByEntity != null)
		 {
			 this.motionX += this.riddenByEntity.motionX;
			 this.motionZ += this.riddenByEntity.motionZ;
		 }
		 var6 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		 if (var6 > 0.35D)
		 {
			 var8 = 0.35D / var6;
			 this.motionX *= var8;
			 this.motionZ *= var8;
			 var6 = 0.35D;
		 }
		 if (var6 > var24 && this.field_70276_b < 0.35D)
		 {
			 this.field_70276_b += (0.35D - this.field_70276_b) / 35.0D;
			 if (this.field_70276_b > 0.35D)
			 {
				 this.field_70276_b = 0.35D;
			 }
		 }
		 else
		 {
			 this.field_70276_b -= (this.field_70276_b - 0.07D) / 35.0D;
			 if (this.field_70276_b < 0.07D)
			 {
				 this.field_70276_b = 0.07D;
			 }
		 }
		 this.moveEntity(this.motionX, this.motionY, this.motionZ);
		 if (this.isCollidedHorizontally && var24 > 0.2D)
		 {
			 
		 }
		 else
		 {
			 this.motionX *= 0.9900000095367432D;
			 this.motionY *= 0.949999988079071D;
			 this.motionZ *= 0.9900000095367432D;
		 }
		 this.rotationPitch = 0.0F;
		 var8 = (double)this.rotationYaw;
		 var26 = this.prevPosX - this.posX;
		 var12 = this.prevPosZ - this.posZ;
		 if (var26 * var26 + var12 * var12 > 0.001D)
		 {
			 var8 = (double)((float)(Math.atan2(var12, var26) * 180.0D / Math.PI));
		 }
		 double var14 = MathHelper.wrapAngleTo180_double(var8 - (double)this.rotationYaw);
		 if (var14 > 20.0D)
		 {
			 var14 = 20.0D;
		 }
		 if (var14 < -20.0D)
		 {
			 var14 = -20.0D;
		 }
		 this.rotationYaw = (float)((double)this.rotationYaw + var14);
		 this.setRotation(this.rotationYaw, this.rotationPitch);
		 if (!this.worldObj.isRemote)
		 {
			 List var16 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
			 int var27;
			 if (var16 != null && !var16.isEmpty())
			 {
				 for (var27 = 0; var27 < var16.size(); ++var27)
				 {
					 Entity var18 = (Entity)var16.get(var27);
					 if (var18 != this.riddenByEntity && var18.canBePushed() && var18 instanceof EntityCar)
					 {
						 var18.applyEntityCollision(this);
					 }
				 }
			 }
			 for (var27 = 0; var27 < 4; ++var27)
			 {
				 int var28 = MathHelper.floor_double(this.posX + ((double)(var27 % 2) - 0.5D) * 0.8D);
				 int var19 = MathHelper.floor_double(this.posZ + ((double)(var27 / 2) - 0.5D) * 0.8D);
				 for (int var20 = 0; var20 < 2; ++var20)
				 {
					 
				 }
			 }
			 if (this.riddenByEntity != null && this.riddenByEntity.isDead)
			 {
				 this.riddenByEntity = null;
			 }
		 }
	 }
	 */
}
public void updateRiderPosition()
{
	 if (this.riddenByEntity != null)
	 {
		 double var1 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
		 double var3 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
		 this.riddenByEntity.setPosition(this.posX + var1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + var3);
	 }
}
/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
	super.writeEntityToNBT(par1NBTTagCompound);
	par1NBTTagCompound.setInteger("fuel", fuel);
}
/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
	super.readEntityFromNBT(par1NBTTagCompound);
	this.fuel = par1NBTTagCompound.getInteger("fuel");
}
/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
public boolean interact(EntityPlayer par1EntityPlayer)
{
	if (par1EntityPlayer.getCurrentEquippedItem() != null){
		if (par1EntityPlayer.getCurrentEquippedItem().getItem() == ZombieApocalypse.petroleum){
			par1EntityPlayer.inventory.consumeInventoryItem(ZombieApocalypse.petroleum);
			this.fuel += 1200;
			if (this.worldObj.isRemote){
				par1EntityPlayer.addChatMessage(new ChatComponentText("Car Refuelled"));
			}
			
		}
	}
	
	 if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != par1EntityPlayer)
	 {
		 return true;
	 }
	 else
	 {
		 if (!this.worldObj.isRemote)
		 {
			 par1EntityPlayer.mountEntity(this);
		 }
		 return true;
	 }
}
/**
	 * Sets the damage taken from the last hit.
	 */
public void setDamageTaken(int par1)
{
	 this.dataWatcher.updateObject(19, Integer.valueOf(par1));
}
//@SideOnly(Side.CLIENT)
public float getShadowSize()
{
	 return 0.0F;
}
/**
	 * Gets the damage taken from the last hit.
	 */
/**
	 * Sets the time to count down from since the last time entity was hit.
	 */
/**
	 * Gets the time since the last hit.
	 */
/**
	 * Sets the forward direction of the entity.
	 */
/**
	 * Gets the forward direction of the entity.
	 */
//@SideOnly(Side.CLIENT)
public void func_70270_d(boolean par1)
{
	 this.field_70279_a = par1;
}
@Override
public boolean canDespawn(){
	return false;
}
@Override
protected void applyEntityAttributes()
{
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.67D);
    this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(Double.MAX_VALUE);
    
}
@Override
public EntityAgeable createChild(EntityAgeable arg0) {
	// TODO Auto-generated method stub
	return null;
}
}
