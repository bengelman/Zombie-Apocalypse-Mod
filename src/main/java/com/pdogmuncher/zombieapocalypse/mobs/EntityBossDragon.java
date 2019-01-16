package com.pdogmuncher.zombieapocalypse.mobs;

import java.util.Random;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBossDragon extends EntityGhast{
	public double[][] ringBuffer = new double[64][3];
    public int ringBufferIndex = -1;
    public float animTime;
    public float prevAnimTime;
    boolean hasDropped = false;
	public EntityBossDragon(World worldIn) {
		super(worldIn);
		this.setSize(16.0F, 8.0F);
		this.moveHelper = new EntityBossDragon.GhastMoveHelper();
		this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityBossDragon.AIRandomFly());
        this.tasks.addTask(7, new EntityBossDragon.AILookAround());
        //this.tasks.addTask(7, new EntityDragon.AIFireballAttack());
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
        this.noClip = true;
		// TODO Auto-generated constructor stub
	}
	public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else if ("fireball".equals(source.getDamageType()) && source.getEntity() instanceof EntityPlayer)
        {
            super.attackEntityFrom(DamageSource.generic, amount);
            ((EntityPlayer)source.getEntity()).triggerAchievement(AchievementList.ghast);
            return true;
        }
        else
        {
            return super.attackEntityFrom(source, amount);
        }
    }
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
    }

    protected String getLivingSound()
    {
        return "mob.enderdragon.growl";
    }

    protected String getHurtSound()
    {
        return "mob.enderdragon.hit";
    }

    protected String getDeathSound()
    {
        return "mob.enderdragon.hit";
    }
    public double[] getMovementOffsets(int p_70974_1_, float p_70974_2_)
    {
        if (this.getHealth() <= 0.0F)
        {
            p_70974_2_ = 0.0F;
        }

        p_70974_2_ = 1.0F - p_70974_2_;
        int j = this.ringBufferIndex - p_70974_1_ * 1 & 63;
        int k = this.ringBufferIndex - p_70974_1_ * 1 - 1 & 63;
        double[] adouble = new double[3];
        double d0 = this.ringBuffer[j][0];
        double d1 = MathHelper.wrapAngleTo180_double(this.ringBuffer[k][0] - d0);
        adouble[0] = d0 + d1 * (double)p_70974_2_;
        d0 = this.ringBuffer[j][1];
        d1 = this.ringBuffer[k][1] - d0;
        adouble[1] = d0 + d1 * (double)p_70974_2_;
        adouble[2] = this.ringBuffer[j][2] + (this.ringBuffer[k][2] - this.ringBuffer[j][2]) * (double)p_70974_2_;
        return adouble;
    }

    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
        float f;
        float f1;

        if (this.worldObj.isRemote)
        {
            f = MathHelper.cos(this.animTime * (float)Math.PI * 2.0F);
            f1 = MathHelper.cos(this.prevAnimTime * (float)Math.PI * 2.0F);

            if (f1 <= -0.3F && f >= -0.3F && !this.isSilent())
            {
                this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.enderdragon.wings", 5.0F, 0.8F + this.rand.nextFloat() * 0.3F, false);
            }
        }

        this.prevAnimTime = this.animTime;
        float f2;
        //BossStatus.setBossStatus(this, true);
        if (this.getHealth() <= 0.0F)
        {
            f = (this.rand.nextFloat() - 0.5F) * 8.0F;
            f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
            f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
            if (!this.worldObj.isRemote && !hasDropped){
            	//this.dropItem(ZombieApocalypse.fireStaff, 1);
            }
            hasDropped = true;
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + (double)f, this.posY + 2.0D + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D, new int[0]);
        }
        else
        {
            //this.updateDragonEnderCrystal();
            f = 0.2F / (MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F + 1.0F);
            f *= (float)Math.pow(2.0D, this.motionY);
            this.animTime += f * 0.25;

            this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);

            if (this.ringBufferIndex < 0)
            {
                for (int i = 0; i < this.ringBuffer.length; ++i)
                {
                    this.ringBuffer[i][0] = (double)this.rotationYaw;
                    this.ringBuffer[i][1] = this.posY;
                }
            }

            if (++this.ringBufferIndex == this.ringBuffer.length)
            {
                this.ringBufferIndex = 0;
            }

            this.ringBuffer[this.ringBufferIndex][0] = (double)this.rotationYaw;
            this.ringBuffer[this.ringBufferIndex][1] = this.posY;
        }
    }
    public int getExperiencePoints(EntityPlayer player){
    	return 1000;
    }
    

    class AILookAround extends EntityAIBase
    {
        private EntityGhast field_179472_a = EntityBossDragon.this;
        private static final String __OBFID = "CL_00002217";

        public AILookAround()
        {
            this.setMutexBits(2);
        }

        public boolean shouldExecute()
        {
            return true;
        }

        public void updateTask()
        {
            if (this.field_179472_a.getAttackTarget() == null)
            {
                this.field_179472_a.renderYawOffset = this.field_179472_a.rotationYaw = -((float)Math.atan2(this.field_179472_a.motionX, this.field_179472_a.motionZ)) * 180.0F / (float)Math.PI;
            }
            else
            {
                EntityLivingBase entitylivingbase = this.field_179472_a.getAttackTarget();
                double d0 = 64.0D;

                if (entitylivingbase.getDistanceSqToEntity(this.field_179472_a) < d0 * d0)
                {
                    double d1 = entitylivingbase.posX - this.field_179472_a.posX;
                    double d2 = entitylivingbase.posZ - this.field_179472_a.posZ;
                    this.field_179472_a.renderYawOffset = this.field_179472_a.rotationYaw = -((float)Math.atan2(d1, d2)) * 180.0F / (float)Math.PI;
                }
            }
        }
    }

    class AIRandomFly extends EntityAIBase
    {
        private EntityGhast field_179454_a = EntityBossDragon.this;
        private static final String __OBFID = "CL_00002214";

        public AIRandomFly()
        {
            this.setMutexBits(1);
        }

        public boolean shouldExecute()
        {
            EntityMoveHelper entitymovehelper = this.field_179454_a.getMoveHelper();

            if (!entitymovehelper.isUpdating())
            {
                return true;
            }
            else
            {
                double d0 = entitymovehelper.func_179917_d() - this.field_179454_a.posX;
                double d1 = entitymovehelper.func_179919_e() - this.field_179454_a.posY;
                double d2 = entitymovehelper.func_179918_f() - this.field_179454_a.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        public boolean continueExecuting()
        {
            return false;
        }

        public void startExecuting()
        {
            Random random = this.field_179454_a.getRNG();
            double d0 = this.field_179454_a.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.field_179454_a.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.field_179454_a.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            if (d1 > 100){
            	d1 = 50;
            }
            this.field_179454_a.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        }
    }

    class GhastMoveHelper extends EntityMoveHelper
    {
    	private boolean justClipped = false;
        private EntityGhast field_179927_g = EntityBossDragon.this;
        private int field_179928_h;
        private static final String __OBFID = "CL_00002216";

        public GhastMoveHelper()
        {
            super(EntityBossDragon.this);
        }

        public void onUpdateMoveHelper()
        {
            if (this.update)
            {
                double d0 = this.posX - this.field_179927_g.posX;
                double d1 = this.posY - this.field_179927_g.posY;
                double d2 = this.posZ - this.field_179927_g.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (this.field_179928_h-- <= 0)
                {
                    this.field_179928_h += this.field_179927_g.getRNG().nextInt(5) + 2;
                    d3 = (double)MathHelper.sqrt_double(d3);
                    /*if (justClipped){
                    	this.field_179927_g.motionY = 0;
                    }*/
                    if (this.func_179926_b(this.posX, this.posY, this.posZ, d3))
                    {
                    	justClipped = false;
                        this.field_179927_g.motionX += d0 / d3 * 0.1D;
                        this.field_179927_g.motionY += d1 / d3 * 0.1D;
                        this.field_179927_g.motionZ += d2 / d3 * 0.1D;
                    }
                    else
                    {
                    	justClipped = true;
                    	this.field_179927_g.motionX += d0 / d3 * 0.1D;
                    	this.field_179927_g.motionY += d1 / d3 * 0.1D;
                        this.field_179927_g.motionZ += d2 / d3 * 0.1D;
                    }
                }
            }
        }

        private boolean func_179926_b(double p_179926_1_, double p_179926_3_, double p_179926_5_, double p_179926_7_)
        {
            double d4 = (p_179926_1_ - this.field_179927_g.posX) / p_179926_7_;
            double d5 = (p_179926_3_ - this.field_179927_g.posY) / p_179926_7_;
            double d6 = (p_179926_5_ - this.field_179927_g.posZ) / p_179926_7_;
            AxisAlignedBB axisalignedbb = this.field_179927_g.getEntityBoundingBox();

            for (int i = 1; (double)i < p_179926_7_; ++i)
            {
                axisalignedbb = axisalignedbb.offset(d4, d5, d6);

                if (!this.field_179927_g.worldObj.getCollidingBoundingBoxes(this.field_179927_g, axisalignedbb).isEmpty())
                {
                    return false;
                }
            }

            return true;
        }
    }
}
