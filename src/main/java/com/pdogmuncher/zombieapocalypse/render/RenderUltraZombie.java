package com.pdogmuncher.zombieapocalypse.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderUltraZombie extends RenderBiped
{
    private static ResourceLocation Your_Texture = new ResourceLocation("zombieapocalypse:textures/entity/Soldier.png");  //refers to:assets/yourmod/textures/entity/yourtexture.png

    public RenderUltraZombie(RenderManager manager, ModelBase par1ModelBase, float par2)
    {
        super(manager, new ModelZombie(), 1.0f);
        Your_Texture = new ResourceLocation("zombieapocalypse:textures/entity/UltraZombie.png");
        
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return Your_Texture;
    }
}
