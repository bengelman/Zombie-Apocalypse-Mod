package com.pdogmuncher.zombieapocalypse.tools;

import java.util.Random;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.mobs.EntityTank;
import com.pdogmuncher.zombieapocalypse.mobs.EntityUltraZombie;
import com.pdogmuncher.zombieapocalypse.mobs.Soldier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBullet extends EntityThrowable
{
    public EntityBullet(World par1World)
    {
        super(par1World);
    }
    public boolean PIE;
    public EntityBullet(World par1World, EntityLivingBase par2EntityLivingBase, int damage, int range, boolean PIE)
    {
        super(par1World, par2EntityLivingBase);
        this.setSize(0.1f, 0.1f);
        this.start = par2EntityLivingBase.getPosition();
        this.damage = damage;
        this.range = range;
        this.sender = par2EntityLivingBase;
        this.PIE = PIE;
    }
    public EntityBullet(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }
    final float explosionRadius = 1.5f;
    public int damage = 7;
    public BlockPos start;
    public int range;
    public EntityLivingBase sender;
    @Override
    public void onEntityUpdate(){
    	
    }
    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition){
        // TODO Auto-generated method stub
    	//this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, false);
    	//this.setDead();
    	if (worldObj.isRemote){
    		return;
    	}
    	if (par1MovingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY){
    		//EntityLiving living = (EntityLiving) par1MovingObjectPosition.entityHit;
    		int damageamount = damage;
        	if (par1MovingObjectPosition.entityHit == null){
        		return;
        	}
        	if (par1MovingObjectPosition.entityHit.isDead){
        		return;
        	}
        	/*if (!(par1MovingObjectPosition.entityHit instanceof EntityLiving)){
        		return;
        	}*/
        	if (par1MovingObjectPosition.entityHit instanceof EntityPlayer || par1MovingObjectPosition.entityHit instanceof EntityPlayerMP){
    			EntityPlayer hitPlayer = (EntityPlayer)par1MovingObjectPosition.entityHit;
    			for (int i = 0; i < 4; i++){
    				if (hitPlayer.getCurrentArmor(i) == null){
    					continue;
    				}
    				if (hitPlayer.getCurrentArmor(i).getUnlocalizedName().contains("kevlar")){
    					damageamount -= 4;
    					hitPlayer.getCurrentArmor(i).damageItem(15, hitPlayer);
    				}
    				if (hitPlayer.getCurrentArmor(i).getUnlocalizedName().contains("military")){
						damageamount -= 2;
						hitPlayer.getCurrentArmor(i).damageItem(5, hitPlayer);
						continue;
					}
					if (hitPlayer.getCurrentArmor(i).getUnlocalizedName().contains("mecha")){
						damageamount /= 1.5;
						hitPlayer.getCurrentArmor(i).damageItem(5, hitPlayer);
						continue;
					}
				}
    			if (hitPlayer.getCurrentEquippedItem() != null){
    				if (hitPlayer.getCurrentEquippedItem().getItem() == ZombieApocalypse.riotShield){
        				hitPlayer.getCurrentEquippedItem().attemptDamageItem(10, new Random());
        				damageamount = 0;
        				return;
        			}
    			}
    			
    		}
    		else if (par1MovingObjectPosition.entityHit instanceof EntityLiving) {
    			if ((par1MovingObjectPosition.entityHit instanceof EntityPlayer) && this.sender instanceof EntityPlayer){
    				EntityPlayer shooter = (EntityPlayer) sender;
    				EntityPlayer hit = (EntityPlayer) par1MovingObjectPosition.entityHit;
    				if (shooter.inventory.hasItem(ZombieApocalypse.german_tag) && hit.inventory.hasItem(ZombieApocalypse.german_tag)){
    					return;
    				}
    				if (shooter.inventory.hasItem(ZombieApocalypse.israel_tag) && hit.inventory.hasItem(ZombieApocalypse.israel_tag)){
    					return;
    				}
    				if (shooter.inventory.hasItem(ZombieApocalypse.india_tag) && hit.inventory.hasItem(ZombieApocalypse.india_tag)){
    					return;
    				}
    				if (shooter.inventory.hasItem(ZombieApocalypse.french_tag) && hit.inventory.hasItem(ZombieApocalypse.french_tag)){
    					return;
    				}
    			}
    			try{
    				EntityLiving hitPlayer = (EntityLiving) par1MovingObjectPosition.entityHit;
        			if (hitPlayer.getCurrentArmor(2) != null){
        				if (hitPlayer.getCurrentArmor(2).getUnlocalizedName().contains("kevlar"))
        					damageamount = ((int)damage/2);
        			}
    			}
    			catch (Exception e){
    				
    			}
    			
    		}
        	try{
        		if (this.getDistance(start.getX(), start.getY(), start.getZ()) > this.range){
            		damageamount /= 2;
            	}
        		if (this.getDistance(start.getX(), start.getY(), start.getZ()) > this.range * 1.5){
            		damageamount = 0;
            		return;
            	}
        	}
        	catch (Exception e){
        		System.err.println("Exception Thrown");
        	}
        	if (damageamount <= 0){
        		damageamount = 1;
        	}
    		if (this.getThrower() instanceof EntityPlayer && !(par1MovingObjectPosition.entityHit instanceof EntityTank)){
    			if (par1MovingObjectPosition.entityHit instanceof EntityPlayer){
    				
    				EntityPlayer living = (EntityPlayer) par1MovingObjectPosition.entityHit;
    				if (living.capabilities.isCreativeMode){
    					
    				}
    				else{
    					living.setHealth(living.getHealth() - damageamount);
    				}
    				par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) this.getThrower()), 0);
    			}
    			else if (par1MovingObjectPosition.entityHit instanceof EntityLiving){
    				
    				EntityLiving living = (EntityLiving) par1MovingObjectPosition.entityHit;
    				if (living.getHealth() < 1){
    					return;
    				}
    				if (PIE && living instanceof EntityUltraZombie){
    					living.setFire(10);
    					living.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) this.getThrower()), 200);
    				}
    				living.setHealth(living.getHealth() - damageamount);
    				par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) this.getThrower()), 0);
    				if (living instanceof Soldier && living.getHealth() < 1 && !living.worldObj.isRemote){
    		        	 //System.out.println("Dropping");
    		        	 int index = Math.abs(new Random().nextInt() % ZombieApocalypse.firearms.size() - 1);
    		        	 //System.out.println("size: " + ZombieApocalypse.firearms.size());
    		        	 //System.out.println("index: " + index);
    		        	 if (index >= ZombieApocalypse.firearms.size()){
    		        		 index = 39;
    		        	 }
    		        	 living.dropItem(ZombieApocalypse.firearms.get(index), 1);
    		         }
    			}
    			else{
    				par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), this.damage);
    			}
    			
    			//System.out.println("Entity Health: " + ((EntityLiving)par1MovingObjectPosition.entityHit).getHealth());
    			((EntityPlayer)this.getThrower()).addStat(ZombieApocalypse.snipeAchievement, 1);
    			
        	}
    		else if (!(par1MovingObjectPosition.entityHit instanceof EntityTank)){
    			if (par1MovingObjectPosition.entityHit instanceof EntityPlayer){
    				EntityPlayer living = (EntityPlayer) par1MovingObjectPosition.entityHit;
    				if (!living.capabilities.isCreativeMode){
    					living.setHealth(living.getHealth() - damageamount);
    				}
    				
    			}
    			else if (par1MovingObjectPosition.entityHit instanceof EntityLiving){
    				EntityLiving living = (EntityLiving) par1MovingObjectPosition.entityHit;
    				living.setHealth(living.getHealth() - damageamount);
    			}
    			par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.generic, 0);
    		}
    		
    	}
    	else if (par1MovingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK){
    		if (worldObj.getBlockState(par1MovingObjectPosition.getBlockPos()).getBlock() == Blocks.tallgrass){
    			return;
    		}
    		for (int i = 0; i < 10; i++){
    			double motionX = rand.nextGaussian() * 0.02D;
        	    double motionY = rand.nextGaussian() * 0.02D;
        	    double motionZ = rand.nextGaussian() * 0.02D;
        	    if (this.worldObj.getBlockState(par1MovingObjectPosition.getBlockPos()).getBlock().isSolidFullCube()){
        	    	worldObj.spawnParticle(
              	          EnumParticleTypes.SNOWBALL, 
              	          posX + rand.nextFloat() * width * 2.0F - width, 
              	          posY + 0.5D + rand.nextFloat() * height, 
              	          posZ + rand.nextFloat() * width * 2.0F - width, 
              	          motionX, 
              	          motionY, 
              	          motionZ);
        	    }
        	    Block b = this.worldObj.getBlockState(par1MovingObjectPosition.getBlockPos()).getBlock();
        	    if (b instanceof BlockTNT){
        	    	BlockPos location = par1MovingObjectPosition.getBlockPos();
        	    	location = location.up(1);
        	    	worldObj.setBlockState(location, Blocks.fire.getDefaultState());
        	    }
        	    if (b.equals(Blocks.glass_pane) && !worldObj.isRemote){
        	    	
        	    	this.dropItem(new ItemStack(Blocks.glass_pane).getItem(), 1);
        	    	worldObj.setBlockState(par1MovingObjectPosition.getBlockPos(), Blocks.air.getDefaultState());
        	    	motionX = rand.nextGaussian() * 0.02D;
            	    motionY = rand.nextGaussian() * 0.02D;
            	    motionZ = rand.nextGaussian() * 0.02D;
        	    }
    		}
    	}
    	if (par1MovingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && 
    			!this.worldObj.getBlockState(par1MovingObjectPosition.getBlockPos()).getBlock().isCollidable()){
    		
    		return;
    	}
    	this.setDead();
    }
    
	@Override
	protected float getGravityVelocity() 
	{
		return 0.000f;
	}
	private boolean isBadEntity(Entity e)
	{
		if(e instanceof EntityVillager)return false;
		if(e instanceof EntityAnimal)return true;
		if(e instanceof EntityWaterMob)return true;
		if(e instanceof EntitySlime || e instanceof EntityMagmaCube)return true;
		if(e instanceof EntityDragon|| e instanceof EntityWither)return true; 
		return false;
	}
}
