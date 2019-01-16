package com.pdogmuncher.zombieapocalypse.render;

import org.lwjgl.opengl.GL11;

import com.pdogmuncher.zombieapocalypse.mobs.EntityFireDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityIceDragon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderRedDragon extends RenderLiving
{
    private ResourceLocation Your_Texture;

    public RenderRedDragon(RenderManager manager, ModelBase par1ModelBase, float par2, String name)
    {
        super(manager, par1ModelBase, par2);
        Your_Texture = new ResourceLocation("zombieapocalypse:textures/entity/" + name + ".png");
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return Your_Texture;
    }
    protected void preRenderCallback(EntityLivingBase entity, float f){
    	if (entity instanceof EntityFireDragon){
    		GL11.glScalef(0.75F, 0.75F, 0.75F);
    	}
    	if (entity instanceof EntityIceDragon){
    		GL11.glScalef(0.75F, 0.75F, 0.75F);
    	}
    }
}
