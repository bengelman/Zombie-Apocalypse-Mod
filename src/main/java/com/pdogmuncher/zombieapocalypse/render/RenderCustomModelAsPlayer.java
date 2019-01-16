package com.pdogmuncher.zombieapocalypse.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderCustomModelAsPlayer extends RendererLivingEntity //I get a crash if I try to extend RenderLiving so must extend RendererLivingEntity for some reason
{
	private ResourceLocation texture;
    

    public RenderCustomModelAsPlayer(ModelBase model, String textureName, float shadowRadius)
    {
       super(Minecraft.getMinecraft().getRenderManager(), model, shadowRadius);
       //this.renderManager = Minecraft.getMinecraft().getRenderManager();
       
       this.texture = new ResourceLocation("zombieapocalypse:textures/mob/" + textureName);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.texture;
    }
//Call the doRender function directly inside EventChangePlayerModel class in the  onRenderPlayerPre() method to render the model you want
    @Override
    public void doRender(EntityLivingBase entity, double x, double y, double z, float par8, float par9)
    {
    	 	super.doRender(entity, x, y, z, par8, par9);
    }
      

   
}