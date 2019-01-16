package com.pdogmuncher.zombieapocalypse.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderHelicopter extends RenderLiving
{
    private static ResourceLocation Your_Texture = new ResourceLocation("zombieapocalypse:textures/entity/");  //refers to:assets/yourmod/textures/entity/yourtexture.png

    public RenderHelicopter(RenderManager manager, ModelBase par1ModelBase, float par2, String name)
    {
        super(manager, par1ModelBase, par2);
        Your_Texture = new ResourceLocation(name);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return Your_Texture;
    }
    protected void preRenderCallback(EntityLivingBase entity, float f){
    	
    	GL11.glTranslatef(0, 1, 0);
    }
}
