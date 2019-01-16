package com.pdogmuncher.zombieapocalypse.generation;

import java.util.List;
import java.util.Random;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.mobs.Ferret;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
public class HauntedForest extends BiomeGenBase
{
private WorldGenerator UnDeadworldGeneratorBigTree;
public final Material blockMaterial;
public HauntedForest(int par1)
{
super(par1);
this.blockMaterial = Material.water;
this.minHeight = 0.1F;
this.maxHeight = 0.6F;
this.minHeight = BiomeGenBase.coldTaiga.minHeight;
this.maxHeight = BiomeGenBase.coldTaiga.maxHeight;
this.spawnableMonsterList.clear();
this.spawnableCreatureList.clear();
this.theBiomeDecorator.treesPerChunk = 50;
//System.out.println("Roofed Trees Per Chunk: " + BiomeGenBase.roofedForest.theBiomeDecorator.treesPerChunk);

//this.spawnableCreatureList.add(new Ferret(Minecraft.getMinecraft().theWorld));
this.topBlock = Blocks.grass.getBlockState().getBaseState();
//this.topBlock = Blocks.grass.getBlockState().getBaseState();
this.fillerBlock = Blocks.dirt.getBlockState().getBaseState();
this.setBiomeName("Tutorial");
/** this changes the water colour, its set to red now but ggole the java colours **/
this.waterColorMultiplier = 0x000000;

}
@Override
public int getGrassColorAtPos(BlockPos p)
{
	//System.out.println("Grass Requested");
	return 0x424242;
}
public int getFoliageColorAtPos(BlockPos p){
	return 0x424242;
}
public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
{
	return new net.minecraft.world.gen.feature.WorldGenCanopyTree(false);
}
public int getSkyColorByTemp(float f){
	return 0;
}
}
