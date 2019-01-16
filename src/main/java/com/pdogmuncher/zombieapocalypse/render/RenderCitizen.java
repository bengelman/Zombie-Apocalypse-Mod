package com.pdogmuncher.zombieapocalypse.render;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderCitizen extends RenderLiving
{
    private static ResourceLocation Your_Texture = new ResourceLocation("zombieapocalypse:textures/entity/Soldier.png");  //refers to:assets/yourmod/textures/entity/yourtexture.png

    public RenderCitizen(RenderManager manager, ModelBase par1ModelBase, float par2)
    {
        super(manager, par1ModelBase, par2);
        Your_Texture = new ResourceLocation("minecraft:textures/entity/steve.png");
        
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return Your_Texture;
    }
}
