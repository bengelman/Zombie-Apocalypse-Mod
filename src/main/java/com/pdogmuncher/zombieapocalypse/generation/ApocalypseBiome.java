package com.pdogmuncher.zombieapocalypse.generation;

import java.util.List;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.mobs.Ferret;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
public class ApocalypseBiome extends BiomeGenBase
{
private WorldGenerator UnDeadworldGeneratorBigTree;
public final Material blockMaterial;
public ApocalypseBiome(int par1)
{
	super(par1);
	this.blockMaterial = Material.water;
	this.minHeight = BiomeGenBase.plains.minHeight;
	this.maxHeight = BiomeGenBase.plains.minHeight + ((float)((float)BiomeGenBase.plains.maxHeight - (float)BiomeGenBase.plains.minHeight) / 200.0f);
	this.spawnableMonsterList.clear();
	//this.spawnableCreatureList.clear();
	this.topBlock = Blocks.grass.getBlockState().getBaseState();
	//this.topBlock = Blocks.grass.getBlockState().getBaseState();
	this.fillerBlock = Blocks.dirt.getBlockState().getBaseState();
	this.setBiomeName("Tutorial");
	/** this changes the water colour, its set to red now but ggole the java colours **/
	//this.waterColorMultiplier = 0xE42D17;
	this.enableSnow = true;
	this.theBiomeDecorator.generateLakes = false;
	//this.temperature = 0.0f;
	//this.setEnableSnow();
}

public int getBiomeGrassColor()
{
	System.out.println("Grass Requested");
	return 0x00AA00;
}
public int getSkyColorByTemp(float f){
	return 0xAAAAAA;
}
}
