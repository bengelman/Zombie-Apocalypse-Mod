package com.pdogmuncher.zombieapocalypse.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderFerret extends RenderLiving
{
    private static final ResourceLocation Your_Texture = new ResourceLocation("zombieapocalypse:textures/entity/Ferret.png");  //refers to:assets/yourmod/textures/entity/yourtexture.png

    public RenderFerret(RenderManager manager, ModelBase par1ModelBase, float par2)
    {
        super(manager, par1ModelBase, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return Your_Texture;
    }
}
