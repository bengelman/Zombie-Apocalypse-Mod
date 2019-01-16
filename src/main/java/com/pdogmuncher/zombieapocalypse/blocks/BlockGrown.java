package com.pdogmuncher.zombieapocalypse.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class BlockGrown extends BlockBush{
    Item drops;
	public BlockGrown(Material arg0, String name, Item drops) {
		super(arg0);
		this.setUnlocalizedName(name);
		this.setHardness(1);
		this.stepSound = soundTypeGrass;
		this.drops = drops;
		// TODO Auto-generated constructor stub
	}
	@Override
    public Item getItemDropped(IBlockState meta, Random random, int fortune){
    	return drops;
    }
    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        
        return (int) ((Math.random() * 2) + 2);
    }
    @Override
    protected boolean canPlaceBlockOn(Block parBlock)
    {
        return parBlock == Blocks.farmland || parBlock == Blocks.dirt;
    }
}
