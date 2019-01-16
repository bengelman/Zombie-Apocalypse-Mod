package com.pdogmuncher.zombieapocalypse.tools;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

public class EntityGrenade extends EntityThrowable
{
	public enum GrenadeType{
		EXPOSIVE, SMOKE, FLASHBANG, INCENDIARY
	}
	public GrenadeType type;
	public boolean emittingSmoke;
	int smokeAge;
	BlockPos emitterPos;
	
    public EntityGrenade(World par1World)
    {
        super(par1World);
    }
    public EntityGrenade(World par1World, EntityLivingBase par2EntityLivingBase, GrenadeType type)
    {
        super(par1World, par2EntityLivingBase);
        this.type = type;
    }
    public EntityGrenade(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }
    final float explosionRadius = 2.5f;
    
    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition){
        // TODO Auto-generated method stub
    	
    	if (type == GrenadeType.EXPOSIVE){
    		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, false);
        	this.setDead();
    	}
    	else if (type == GrenadeType.FLASHBANG){
    		System.out.println("KAPOW");
    		
    		ArrayList <Entity> list = (ArrayList) this.worldObj.getEntitiesWithinAABBExcludingEntity(
    				this, AxisAlignedBB.fromBounds(
    						(double)this.posX, 
    						(double)this.posY, 
    						(double)this.posZ, 
    						(double)(this.posX + 1), 
    						(double)(this.posY + 1), 
    						(double)(this.posZ + 1)).expand((double)(4), 4.0D, (double)(4)));
    		for (int i = 0; i < list.size(); i++){
    			if (list.get(i) instanceof EntityLiving){
    				((EntityLiving)list.get(i)).addPotionEffect(new PotionEffect(Potion.blindness.id, 200, 4));
    				((EntityLiving)list.get(i)).addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 4));
    			}
    			if (list.get(i) instanceof EntityPlayer){
    				((EntityPlayer)list.get(i)).addPotionEffect(new PotionEffect(Potion.blindness.id, 200, 4));
    				((EntityPlayer)list.get(i)).addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 4));
    			}
    			
    		}
    		MinecraftServer.getServer().getCommandManager().executeCommand(this.getCommandSenderEntity(), "/particle largeexplode ~ ~1 ~ 1 1 1 4 5");
    		this.setDead();
    	}
    	else if (type == GrenadeType.SMOKE){
    		//System.out.println("Remote: " + worldObj.isRemote);
    		/*this.posX++;
    		this.motionX = 0;
    		this.motionY = 0;
    		this.motionZ = 0;*/
    		if (!emittingSmoke){
    			emitterPos = this.getPosition();
    			emitterPos = emitterPos.add(0, 1, 0);
    		}
    		emittingSmoke = true;
    	}
    	else if (type == GrenadeType.INCENDIARY){
    		ArrayList <Entity> list = (ArrayList) this.worldObj.getEntitiesWithinAABBExcludingEntity(
    				this, AxisAlignedBB.fromBounds(
    						(double)this.posX, 
    						(double)this.posY, 
    						(double)this.posZ, 
    						(double)(this.posX + 1), 
    						(double)(this.posY + 1), 
    						(double)(this.posZ + 1)).expand((double)(4), 4.0D, (double)(4)));
    		
    		for (int i = 0; i < list.size(); i++){
    			list.get(i).setFire(20);
    		}
    		//this.setDead();
    		emittingSmoke = true;
    		this.emitterPos = this.getPosition();
    	}
    	//this.setDead();
    }
    @Override
    public void onEntityUpdate(){
    	super.onEntityUpdate();
    	if (this.type == GrenadeType.INCENDIARY){
    		this.setFire(2);
    	}
    	if (emittingSmoke){
    		if (smokeAge > 1000000){
    			this.setDead();
    			return;
    		}
    		if (!worldObj.isRemote){
        		smokeAge++;
        		double motionX = rand.nextGaussian() * 0.02D;
        	    double motionY = rand.nextGaussian() * 0.02D;
        	    double motionZ = rand.nextGaussian() * 0.02D;
        	    worldObj.spawnParticle(
        	          EnumParticleTypes.EXPLOSION_HUGE, 
        	          emitterPos.getX(), 
        	          emitterPos.getY(), 
        	          emitterPos.getZ(), 
        	          motionX, 
        	          motionY, 
        	          motionZ);
        	    EntityFireworkRocket firework = new EntityFireworkRocket(worldObj);
        	    firework.setPosition(this.emitterPos.getX(), this.emitterPos.getY() + 1, this.emitterPos.getZ());
        	    try {
					firework.writeToNBT(JsonToNBT.func_180713_a("{LifeTime:1,FireworksItem:{id:401,Count:1,tag:{Fireworks:{Explosions:[{Type:2,Flicker:0,Trail:0,Colors:[16252672],FadeColors:[16777215]}]}}}}"));
				} catch (NBTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	    //MinecraftServer.getServer().getCommandManager().executeCommand(this.getCommandSenderEntity(), "/summon FireworksRocketEntity ~0 ~1 ~0 {LifeTime:0,FireworksItem:{id:401,Count:1,tag:{Fireworks:{Explosions:[{Type:1,Flicker:0,Trail:0,Colors:[65297,16770560],FadeColors:[16777215]}]}}}}");
        	    //worldObj.spawnEntityInWorld(firework);
        	    if (this.type == GrenadeType.INCENDIARY){
        	    	MinecraftServer.getServer().getCommandManager().executeCommand(this.getCommandSenderEntity(), "/particle flame " + this.emitterPos.getX() + " " + this.emitterPos.getY() + " " + this.emitterPos.getZ() +  " 1 1 1 4 5");
        	    }
        	    else{
        	    	MinecraftServer.getServer().getCommandManager().executeCommand(this.getCommandSenderEntity(), "/particle largeexplode " + this.emitterPos.getX() + " " + this.emitterPos.getY() + " " + this.emitterPos.getZ() +  " 1 1 1 4 5");
        	    }
        	    
    		}
    	}
    }
	@Override
	protected float getGravityVelocity() 
	{
		return 0.05f;
	}
    
}
