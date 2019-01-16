package com.pdogmuncher.zombieapocalypse.generation;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class WorldTypeCustom extends WorldType {

	public WorldTypeCustom(int par1, String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	   /**
     * Creates the GenLayerBiome used for generating the world
     * 
     * @param worldSeed The world seed
     * @param parentLayer The parent layer to feed into any layer you return
     * @return A GenLayer that will return ints representing the Biomes to be generated, see GenLayerBiome
     */
	@Override
	  public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer, String args)
    {
        GenLayer ret = new CustomGenLayer(200, parentLayer, this);
        ret = GenLayerZoom.magnify(1000L, ret, 2);
        ret = new GenLayerBiomeEdge(1000L, ret);
        if (this.getWorldTypeID() == 9 || this.getWorldTypeID() == ZombieApocalypse.APOCALYPSE.getWorldTypeID()){
        	return ret;
        }
        else{
        	return super.getBiomeLayer(worldSeed, parentLayer, args);
        }
    }
}
