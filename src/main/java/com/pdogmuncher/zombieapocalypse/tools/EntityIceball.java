package com.pdogmuncher.zombieapocalypse.tools;

import com.pdogmuncher.zombieapocalypse.mobs.EntityEarthDragon;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityIceball extends EntityFireball{
	EntityLivingBase thrower;
	public EntityIceball(World worldIn, EntityLivingBase throwerIn, double one, double two, double three) {
		super(worldIn, throwerIn, one, two, three);
		this.setFire(0);
		this.setSize(0.5f, 0.5f);
		
		thrower = throwerIn;
		// TODO Auto-generated constructor stub
	}
	public EntityIceball(World worldIn) {
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
			if (hit.entityHit instanceof EntityEarthDragon){
				return;
			}
			if (hit.entityHit instanceof EntityPlayer){
				EntityPlayer living = (EntityPlayer) hit.entityHit;
				living.attackEntityFrom(DamageSource.causeFireballDamage(this, thrower), 5);
				living.addVelocity(this.motionX * 5, this.motionY, this.motionZ * 5);
				this.setDead();
			}
			if (hit.entityHit instanceof EntityLiving){
				EntityLiving living = (EntityLiving) hit.entityHit;
				living.attackEntityFrom(DamageSource.generic, 5);
				living.addVelocity(this.motionX * 5, this.motionY, this.motionZ * 5);
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
