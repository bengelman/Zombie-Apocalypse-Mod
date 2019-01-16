package com.pdogmuncher.zombieapocalypse.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderTank extends RenderLiving{
	private static final ResourceLocation Your_Texture = new ResourceLocation("zombieapocalypse:textures/entity/tank.png");  //refers to:assets/yourmod/textures/entity/yourtexture.png

    public RenderTank(RenderManager manager, ModelBase par1ModelBase, float par2)
    {
        super(manager, par1ModelBase, par2);
    	par1ModelBase.textureHeight = 2;
    }
    protected void preRenderCallback(EntityLivingBase entity, float f){
    	GL11.glScalef(1.0F, 2.0F, 1.0F);
    	GL11.glTranslatef(0, 1, 0);
    }
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return Your_Texture;
    }
}
