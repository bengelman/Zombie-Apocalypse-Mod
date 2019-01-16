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

public class EntityFireDragon extends EntityBossDragon implements IBossDisplayData{
	public double[][] ringBuffer = new double[64][3];
    public int ringBufferIndex = -1;
    public float animTime;
    public float prevAnimTime;
    boolean hasDropped = false;
	public EntityFireDragon(World worldIn) {
		super(worldIn);
		this.setSize(12.0F, 6.0F);
		this.moveHelper = new EntityFireDragon.GhastMoveHelper();
		this.tasks.taskEntries.clear();
		this.targetTasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityFireDragon.AIRandomFly());
        this.tasks.addTask(7, new EntityFireDragon.AILookAround());
        this.tasks.addTask(7, new EntityFireDragon.AIFireballAttack());
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
        this.noClip = true;
		// TODO Auto-generated constructor stub
	}
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
    }

    @Override
	public IChatComponent getDisplayName(){
		ChatComponentText message = new ChatComponentText("Fire Dragon");
		ChatStyle colour = new ChatStyle();
		colour.setColor(EnumChatFormatting.RED);
		
		message.setChatStyle(colour);
		return message;
		
	}
    

    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
        BossStatus.setBossStatus(this, true);
    }
    public int getExperiencePoints(EntityPlayer player){
    	return 1000;
    }
    class AIFireballAttack extends EntityAIBase
    {
        private EntityGhast field_179470_b = EntityFireDragon.this;
        public int field_179471_a;
        private static final String __OBFID = "CL_00002215";

        public boolean shouldExecute()
        {
            return this.field_179470_b.getAttackTarget() != null;
        }

        public void startExecuting()
        {
            this.field_179471_a = 0;
        }

        public void resetTask()
        {
            this.field_179470_b.func_175454_a(false);
        }

        public void updateTask()
        {
            EntityLivingBase entitylivingbase = this.field_179470_b.getAttackTarget();
            double d0 = 64.0D;

            if (entitylivingbase.getDistanceSqToEntity(this.field_179470_b) < d0 * d0 && this.field_179470_b.canEntityBeSeen(entitylivingbase))
            {
                World world = this.field_179470_b.worldObj;
                ++this.field_179471_a;

                if (this.field_179471_a == 10)
                {
                    world.playAuxSFXAtEntity((EntityPlayer)null, 1007, new BlockPos(this.field_179470_b), 0);
                }

                if (this.field_179471_a == 20)
                {
                    double d1 = 4.0D;
                    Vec3 vec3 = this.field_179470_b.getLook(1.0F);
                    double d2 = entitylivingbase.posX - (this.field_179470_b.posX + vec3.xCoord * d1);
                    double d3 = entitylivingbase.getEntityBoundingBox().minY + (double)(entitylivingbase.height / 2.0F) - (0.5D + this.field_179470_b.posY + (double)(this.field_179470_b.height / 2.0F));
                    double d4 = entitylivingbase.posZ - (this.field_179470_b.posZ + vec3.zCoord * d1);
                    world.playAuxSFXAtEntity((EntityPlayer)null, 1008, new BlockPos(this.field_179470_b), 0);
                    EntitySmallFireball entitylargefireball = new EntitySmallFireball(world, this.field_179470_b, d2, d3, d4);
                    
                    //entitylargefireball.explosionPower = this.field_179470_b.func_175453_cd();
                    
                    entitylargefireball.posX = this.field_179470_b.posX + vec3.xCoord * d1;
                    entitylargefireball.posY = this.field_179470_b.posY + (double)(this.field_179470_b.height / 2.0F) + 0.5D;
                    entitylargefireball.posZ = this.field_179470_b.posZ + vec3.zCoord * d1;
                    world.spawnEntityInWorld(entitylargefireball);
                    this.field_179471_a = 0;
                }
            }
            else if (this.field_179471_a > 0)
            {
                --this.field_179471_a;
            }

            this.field_179470_b.func_175454_a(this.field_179471_a > 10);
        }
    }

   
}
