package com.pdogmuncher.zombieapocalypse.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class AluminumOre extends Block{
	private Item drop;
	private int meta;
	private int least_quantity;
	private int most_quantity;
	public AluminumOre (int i, Material carpet){
		super (carpet);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundTypeStone);
		this.setHardness(5f);
		this.setResistance(10f);
		this.setHarvestLevel("pickaxe", 3);
		this.setUnlocalizedName("aluminum_ore");
		
	}
	public int getHarvestLevel(IBlockState state){
		return 3;
	}
	public String getHarvestTool(IBlockState state){
		return "pickaxe";
	}
}
