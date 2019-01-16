package com.pdogmuncher.zombieapocalypse.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.nbt.NBTException;
import net.minecraft.util.ResourceLocation;

public class RenderKitPicker extends RenderZombie
{
    private static ResourceLocation Your_Texture = new ResourceLocation("zombieapocalypse:textures/entity/Soldier.png");  //refers to:assets/yourmod/textures/entity/yourtexture.png

    public RenderKitPicker(RenderManager manager, ModelBase par1ModelBase, float par2, String resourceLocation)
    {
        super(manager);
        this.Your_Texture = new ResourceLocation(resourceLocation);
    }
    @Override
    public void doRender(Entity base, double x, double y, double z, float rotation, float otherrotation){
    	EntityZombie overlayzombie = new EntityZombie(base.worldObj);
    	try {
			overlayzombie.readEntityFromNBT(net.minecraft.nbt.JsonToNBT.func_180713_a("{CustomNameVisible:1,}"));
		} catch (NBTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	overlayzombie.setPositionAndRotation(x, y, z, rotation, otherrotation);
    	overlayzombie.setCurrentItemOrArmor(0, ((EntityLiving)base).getHeldItem());
    	overlayzombie.setCurrentItemOrArmor(1, ((EntityLiving)base).getCurrentArmor(0));
    	overlayzombie.setCurrentItemOrArmor(2, ((EntityLiving)base).getCurrentArmor(1));
    	overlayzombie.setCurrentItemOrArmor(3, ((EntityLiving)base).getCurrentArmor(2));
    	overlayzombie.setCurrentItemOrArmor(4, ((EntityLiving)base).getCurrentArmor(3));
    	overlayzombie.setCustomNameTag(base.getCustomNameTag());
    	this.renderZombies(overlayzombie, x, y, z, rotation, otherrotation);
    }
    @Override
    public void doRender(EntityLiving base, double x, double y, double z, float rotation, float otherrotation){
    	EntityZombie overlayzombie = new EntityZombie(base.worldObj);
    	try {
			overlayzombie.readEntityFromNBT(net.minecraft.nbt.JsonToNBT.func_180713_a("{CustomNameVisible:1,}"));
		} catch (NBTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	overlayzombie.setPositionAndRotation(x, y, z, rotation, otherrotation);
    	overlayzombie.setCurrentItemOrArmor(0, ((EntityLiving)base).getHeldItem());
    	overlayzombie.setCurrentItemOrArmor(1, ((EntityLiving)base).getCurrentArmor(0));
    	overlayzombie.setCurrentItemOrArmor(2, ((EntityLiving)base).getCurrentArmor(1));
    	overlayzombie.setCurrentItemOrArmor(3, ((EntityLiving)base).getCurrentArmor(2));
    	overlayzombie.setCurrentItemOrArmor(4, ((EntityLiving)base).getCurrentArmor(3));
    	overlayzombie.setCustomNameTag(base.getCustomNameTag());
    	this.renderZombies(overlayzombie, x, y, z, rotation, otherrotation);
    }
    @Override
    public void doRender(EntityLivingBase base, double x, double y, double z, float rotation, float otherrotation){
    	EntityZombie overlayzombie = new EntityZombie(base.worldObj);
    	try {
			overlayzombie.readEntityFromNBT(net.minecraft.nbt.JsonToNBT.func_180713_a("{CustomNameVisible:1,}"));
		} catch (NBTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	overlayzombie.setPositionAndRotation(x, y, z, rotation, otherrotation);
    	overlayzombie.setCurrentItemOrArmor(0, ((EntityLiving)base).getHeldItem());
    	overlayzombie.setCurrentItemOrArmor(1, ((EntityLiving)base).getCurrentArmor(0));
    	overlayzombie.setCurrentItemOrArmor(2, ((EntityLiving)base).getCurrentArmor(1));
    	overlayzombie.setCurrentItemOrArmor(3, ((EntityLiving)base).getCurrentArmor(2));
    	overlayzombie.setCurrentItemOrArmor(4, ((EntityLiving)base).getCurrentArmor(3));
    	overlayzombie.setCustomNameTag(base.getCustomNameTag());
    	
    	this.renderZombies(overlayzombie, x, y, z, rotation, otherrotation);
    }
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return Your_Texture;
    }
    private void renderZombies (EntityZombie zombie, double x, double y, double z, float rotation, float otherrotation){
    	super.doRender(zombie, x, y, z, rotation, otherrotation);
    }
}
