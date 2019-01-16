package com.pdogmuncher.zombieapocalypse.render;

import com.pdogmuncher.zombieapocalypse.mobs.Survivor;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderSurvivor extends RenderBiped
{
    private static ResourceLocation maleTexture = new ResourceLocation("zombieapocalypse:textures/entity/Survivor.png");  //refers to:assets/yourmod/textures/entity/yourtexture.png
    private static ResourceLocation femaleTexture = new ResourceLocation("zombieapocalypse:textures/entity/SurvivorGirl.png");
    
    public RenderSurvivor(RenderManager manager, ModelBiped par1ModelBase, float par2)
    {
        super(manager, par1ModelBase, par2);
        this.mainModel = par1ModelBase;
        //this.
        
        //this.Your_Texture = new ResourceLocation(resourceLocation);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
    	Survivor survivor = (Survivor) par1Entity;
    	if (survivor.gender){
    		return femaleTexture;
    	}
        return maleTexture;
    }
    
}
