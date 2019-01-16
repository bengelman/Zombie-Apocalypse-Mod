package com.pdogmuncher.zombieapocalypse.generation;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraft.world.gen.layer.IntCache;

public class CustomGenLayer extends GenLayer{
	public int size = 100;
	BiomeGenBase[] spawnBiomes = new BiomeGenBase[] {BiomeGenBase.desert, 
			BiomeGenBase.desert, 
			BiomeGenBase.desert, 
			BiomeGenBase.savanna, 
			BiomeGenBase.savanna, 
			BiomeGenBase.plains, 
			BiomeGenBase.forest, 
			BiomeGenBase.roofedForest, 
			BiomeGenBase.extremeHills, 
			BiomeGenBase.plains, 
			BiomeGenBase.birchForest, 
			BiomeGenBase.swampland, 
			BiomeGenBase.forest, 
			BiomeGenBase.extremeHills, 
			BiomeGenBase.taiga, 
			BiomeGenBase.plains, 
			BiomeGenBase.icePlains, 
			BiomeGenBase.icePlains, 
			BiomeGenBase.icePlains, 
			BiomeGenBase.coldTaiga,
			ZombieApocalypse.bronzeBiome, 
			ZombieApocalypse.bronzeBiome, 
			ZombieApocalypse.bronzeBiome, 
			ZombieApocalypse.bronzeBiome,
			ZombieApocalypse.obsidianSpikesBiome,
			ZombieApocalypse.obsidianSpikesBiome,
			ZombieApocalypse.obsidianSpikesBiome,
			ZombieApocalypse.obsidianSpikesBiome, 
			ZombieApocalypse.hauntedForestBiome,
			ZombieApocalypse.hauntedForestBiome,
			ZombieApocalypse.hauntedForestBiome,
			ZombieApocalypse.hauntedForestBiome,
			ZombieApocalypse.hauntedForestBiome,
			ZombieApocalypse.hauntedForestBiome,
			ZombieApocalypse.hauntedForestBiome,
			ZombieApocalypse.hauntedForestBiome,
			ZombieApocalypse.hauntedForestBiome,
			ZombieApocalypse.hauntedForestBiome,
			ZombieApocalypse.hauntedForestBiome,
			ZombieApocalypse.hauntedForestBiome};
	WorldType worldtype;
	public CustomGenLayer(long par1, GenLayer par3GenLayer, WorldType par4WorldType)
    {
        super(par1);
        //this.
        //this.
        this.parent = par3GenLayer;
        worldtype = par4WorldType;
        
        //this.field_151623_c = new BiomeGenBase[] {BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.savanna, BiomeGenBase.savanna, BiomeGenBase.plains};
        //this.field_151621_d = new BiomeGenBase[] {BiomeGenBase.forest, BiomeGenBase.roofedForest, BiomeGenBase.extremeHills, BiomeGenBase.plains, BiomeGenBase.birchForest, BiomeGenBase.swampland};
        //this.field_151622_e = new BiomeGenBase[] {BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.taiga, BiomeGenBase.plains};
        //this.field_151620_f = new BiomeGenBase[] {BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.coldTaiga};
        //this.parent = par3GenLayer;
        //this.
        if (par4WorldType == WorldType.DEFAULT_1_1)
        {
            //this.field_151623_c = new BiomeGenBase[] {BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga};
        }
        
    }

	@Override
	public int[] getInts(int chunkX, int chunkZ, int par3, int par4) {
		int[] current = IntCache.getIntCache(par3 * par4);

		int realX;
		int realZ;
		double distance;

		for(int z = 0; z < par4; z++) {
			for(int x = 0; x < par3; x++) {
				realX = x + chunkX;
				realZ = z + chunkZ;

				this.initChunkSeed((long) realX, (long) realZ);

				distance = 0 + realX + realZ;
				int nextbiome = this.nextInt(spawnBiomes.length);
				if(nextbiome == 0) {
					current[x + z * par3] = ZombieApocalypse.bronzeBiome.biomeID;
				} else if(nextbiome == 1) {
					current[x + z * par3] = BiomeGenBase.ocean.biomeID;
				} else if(nextbiome == 2) {
					current[x + z * par3] = BiomeGenBase.taigaHills.biomeID;
				} else if(nextbiome == 3) {
					current[x + z * par3] = BiomeGenBase.desert.biomeID;
				} else if(nextbiome == 4) {
					current[x + z * par3] = BiomeGenBase.extremeHillsPlus.biomeID;
				} else {
					current[x + z * par3] = BiomeGenBase.forest.biomeID;
				}
				//current[x + z * par3] = BronzeAge.bronzeBiome.biomeID;
				current[x + z * par3] = spawnBiomes[nextbiome].biomeID;
				if (worldtype == ZombieApocalypse.APOCALYPSE){
					current[x + z * par3] = ZombieApocalypse.apocalypseBiome.biomeID;
					
					
					
				}
			}
		}
		
		return current;
	}

}
