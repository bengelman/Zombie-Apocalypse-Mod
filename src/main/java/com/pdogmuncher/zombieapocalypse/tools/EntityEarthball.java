package com.pdogmuncher.zombieapocalypse.tools;

import com.pdogmuncher.zombieapocalypse.mobs.EntityBossDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityEarthDragon;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityEarthball extends EntityFireball{
	EntityLivingBase thrower;
	boolean earth;
	public EntityEarthball(World worldIn, EntityLivingBase throwerIn, double one, double two, double three, boolean earth) {
		super(worldIn, throwerIn, one, two, three);
		this.setFire(0);
		this.setSize(0.5f, 0.5f);
		this.earth = earth;
		thrower = throwerIn;
		// TODO Auto-generated constructor stub
	}
	public EntityEarthball(World worldIn) {
		super(worldIn);
		this.setFire(0);
		
		
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onUpdate(){
		super.onUpdate();
		this.setFire(0);
		this.isImmuneToFire = true;
	}
	@Override
	protected void onImpact(MovingObjectPosition hit) {
		// TODO Auto-generated method stub
		
		if (worldObj.isRemote){
			return;
		}
		if (hit.typeOfHit == MovingObjectType.ENTITY){
			if (hit.entityHit instanceof EntityBossDragon){
				return;
			}
			if (hit.entityHit instanceof EntityPlayer){
				EntityPlayer living = (EntityPlayer) hit.entityHit;
				
				EntityEarthball par2World = this;
				int xm = living.getPosition().getX();
				int ym = living.getPosition().getY();
				int zm = living.getPosition().getZ();
				if (earth && !worldObj.isRemote){
					living.attackEntityFrom(DamageSource.causeFireballDamage(this, thrower), 5);
					IBlockState block = Blocks.dirt.getDefaultState();
					this.playSound("dig.grass", 1.0f, 1.0f);
					for (int num = 0; num < 3; num++){
		    			par2World.setBlockState(new BlockPos(xm + 2, ym + num, zm), block);
		        		par2World.setBlockState(new BlockPos(xm + 2, ym + num, zm + 1), block);
		        		par2World.setBlockState(new BlockPos(xm + 2, ym + num, zm - 1), block);
		        		
		        		par2World.setBlockState(new BlockPos(xm - 2, ym + num, zm - 1), block);
		        		par2World.setBlockState(new BlockPos(xm - 2, ym + num, zm + 1), block);
		        		par2World.setBlockState(new BlockPos(xm - 2, ym + num, zm), block);
		        		
		        		par2World.setBlockState(new BlockPos(xm, ym + num, zm + 2), block);
		        		par2World.setBlockState(new BlockPos(xm + 1, ym + num, zm + 2), block);
		        		par2World.setBlockState(new BlockPos(xm - 1, ym + num, zm + 2), block);
		        		
		        		par2World.setBlockState(new BlockPos(xm, ym + num, zm - 2), block);
		        		par2World.setBlockState(new BlockPos(xm + 1, ym + num, zm - 2), block);
		        		par2World.setBlockState(new BlockPos(xm - 1, ym + num, zm - 2), block);
		    		}
				}
				else{
					EntityLightningBolt bolt = new EntityLightningBolt(worldObj, xm, ym, zm);
					worldObj.spawnEntityInWorld(bolt);
				}
				
				this.setDead();
			}
			if (worldObj.isRemote){
				return;
			}
			if (hit.entityHit instanceof EntityLiving){
				EntityLiving living = (EntityLiving) hit.entityHit;
				living.attackEntityFrom(DamageSource.generic, 5);
				EntityEarthball par2World = this;
				int xm = living.getPosition().getX();
				int ym = living.getPosition().getY();
				int zm = living.getPosition().getZ();
				IBlockState block = Blocks.dirt.getDefaultState();
				if (earth){
					for (int num = 0; num < 3; num++){
		    			par2World.setBlockState(new BlockPos(xm + 2, ym + num, zm), block);
		        		par2World.setBlockState(new BlockPos(xm + 2, ym + num, zm + 1), block);
		        		par2World.setBlockState(new BlockPos(xm + 2, ym + num, zm - 1), block);
		        		
		        		par2World.setBlockState(new BlockPos(xm - 2, ym + num, zm - 1), block);
		        		par2World.setBlockState(new BlockPos(xm - 2, ym + num, zm + 1), block);
		        		par2World.setBlockState(new BlockPos(xm - 2, ym + num, zm), block);
		        		
		        		par2World.setBlockState(new BlockPos(xm, ym + num, zm + 2), block);
		        		par2World.setBlockState(new BlockPos(xm + 1, ym + num, zm + 2), block);
		        		par2World.setBlockState(new BlockPos(xm - 1, ym + num, zm + 2), block);
		        		
		        		par2World.setBlockState(new BlockPos(xm, ym + num, zm - 2), block);
		        		par2World.setBlockState(new BlockPos(xm + 1, ym + num, zm - 2), block);
		        		par2World.setBlockState(new BlockPos(xm - 1, ym + num, zm - 2), block);
		    		}
				}
				
				this.setDead();
			}
		}
		else{
			this.setDead();
		}
	}
	public void setBlockState(BlockPos p, IBlockState b){
		IBlockState state = worldObj.getBlockState(p);
		if (!worldObj.isAirBlock(p)){
			worldObj.setBlockState(p, b);
			return;
		}
		if (state != null){
			if (!state.getBlock().isSolidFullCube()){
				worldObj.setBlockState(p, b);
			}
		}
		
		
	}
}
