package com.pdogmuncher.zombieapocalypse.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderSoldier extends RenderBiped
{
    private static ResourceLocation Your_Texture = new ResourceLocation("zombieapocalypse:textures/entity/Soldier.png");  //refers to:assets/yourmod/textures/entity/yourtexture.png

    public RenderSoldier(RenderManager manager, ModelBiped par1ModelBase, float par2, String resourceLocation)
    {
        super(manager, par1ModelBase, par2);
        this.Your_Texture = new ResourceLocation(resourceLocation);
        
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return Your_Texture;
    }
    
}
