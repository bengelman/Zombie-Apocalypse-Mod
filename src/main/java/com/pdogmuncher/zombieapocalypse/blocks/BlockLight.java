package com.pdogmuncher.zombieapocalypse.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockLight extends BlockGlass{

	public BlockLight(Material arg0) {
		super(arg0, false);
		this.setLightLevel(1.0f);
		this.setBlockUnbreakable();
		this.setUnlocalizedName("light");
		this.setTickRandomly(true);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isOpaqueCube(){
		return false;
	}
	@Override
	public AxisAlignedBB getCollisionBoundingBox(World p_149668_1_, BlockPos p, IBlockState state) {
		return null;
	}
	@Override
	public void updateTick(World parWorld, BlockPos p, IBlockState state, Random parRand){
		parWorld.setBlockState(p, Blocks.air.getDefaultState());
	}
	
}
