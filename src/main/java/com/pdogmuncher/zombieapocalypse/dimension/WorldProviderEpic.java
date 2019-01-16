package com.pdogmuncher.zombieapocalypse.dimension;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderEpic extends WorldProvider
{
    private static final String __OBFID = "CL_00000387";

    public void registerWorldChunkManager()
    {
    	
        this.worldChunkMgr = new WorldChunkManagerHell(ZombieApocalypse.obsidianSpikesBiome, 0.0F);
        //this.isHellWorld = true;
        this.hasNoSky = true;
        this.dimensionId = 3;
    }
    /*
    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float p_76562_1_, float p_76562_2_)
    {
        return new Vec3(0.20000000298023224D, 0.029999999329447746D, 0.029999999329447746D);
    }
    */
    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float p_76562_1_, float p_76562_2_)
    {
        int i = 10518688;
        float f2 = MathHelper.cos(p_76562_1_ * (float)Math.PI * 2.0F) * 2.0F + 0.5F;
        f2 = MathHelper.clamp_float(f2, 0.0F, 1.0F);
        float f3 = (float)(i >> 16 & 255) / 255.0F;
        float f4 = (float)(i >> 8 & 255) / 255.0F;
        float f5 = (float)(i & 255) / 255.0F;
        f3 *= f2 * 0.0F + 0.15F;
        f4 *= f2 * 0.0F + 0.15F;
        f5 *= f2 * 0.0F + 0.15F;
        return new Vec3((double)f3, (double)f4, (double)f5);
    }
    public float getSunBrightness(float f){
    	return 15.0f;
    }
    protected void generateLightBrightnessTable()
    {
        float f = 0.1F;

        for (int i = 0; i <= 15; ++i)
        {
            float f1 = 1.0F - (float)i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
    }
	
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderEpic(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), "");
    }

    public boolean isSurfaceWorld()
    {
        return false;
    }

    public boolean canCoordinateBeSpawn(int x, int z)
    {
        return false;
    }

    public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_)
    {
        return 0.0F;
    }

    public boolean canRespawnHere()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z)
    {
        return false;
    }
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored()
    {
        return false;
    }
    public String getDimensionName()
    {
        return "Epic";
    }

    public String getInternalNameSuffix()
    {
        return "_epic";
    }

    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 8.0F;
    }
    public WorldBorder getWorldBorder()
    {
        return new WorldBorder()
        {
            //private static final String __OBFID = "CL_00002008";
            public double getCenterX()
            {
                return super.getCenterX() / 8.0D;
            }
            public double getCenterZ()
            {
                return super.getCenterZ() / 8.0D;
            }
        };
    }
    public float getStarBrightness(float f){
    	return 15.0f;
    	
    }
    public String getDepartMessage(){
    	return "Leaving Dimension";
    }
}
