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
public class ObsidianSpikes extends BiomeGenBase
{
private WorldGenerator UnDeadworldGeneratorBigTree;
public final Material blockMaterial;
public ObsidianSpikes(int par1)
{
super(par1);
this.blockMaterial = Material.water;
this.minHeight = 0.1F;
this.maxHeight = 1.5F;
//this.minHeight = BiomeGenBase.coldTaiga.minHeight;
//this.maxHeight = BiomeGenBase.coldTaiga.maxHeight;
this.spawnableMonsterList.clear();
this.spawnableCreatureList.clear();
//this.spawnableCreatureList.add(new Ferret(Minecraft.getMinecraft().theWorld));
this.topBlock = Blocks.obsidian.getBlockState().getBaseState();
//this.topBlock = Blocks.grass.getBlockState().getBaseState();
this.fillerBlock = Blocks.obsidian.getBlockState().getBaseState();
this.setBiomeName("Tutorial");
/** this changes the water colour, its set to red now but ggole the java colours **/
this.waterColorMultiplier = 0xE42D17;

}

public int getBiomeGrassColor()
{
	System.out.println("Grass Requested");
	return 0xE42D17;
}
}
