package com.pdogmuncher.zombieapocalypse.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class AluminumBlock extends Block{
	private Item drop;
	private int meta;
	private int least_quantity;
	private int most_quantity;
	public AluminumBlock (int i, Material carpet){
		super (carpet);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundTypeMetal);
		this.setHardness(5f);
		this.setResistance(10f);
		this.setHarvestLevel("pickaxe", 2);
		this.setUnlocalizedName("aluminum_block");
	}
}
