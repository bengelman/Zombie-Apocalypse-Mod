package com.pdogmuncher.zombieapocalypse.blocks;

import java.util.Random;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCrops extends BlockBush implements IGrowable
{
    protected int maxGrowthStage = 7;

    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 5);
    Block turnsInto;
    Item drops;
    @Override
    protected boolean canPlaceBlockOn(Block parBlock)
    {
        return parBlock == Blocks.farmland;
    }
    public BlockCrops(Block turnsInto, Item drops, String name)
    {
    	this.setUnlocalizedName(name);
     // Basic block setup
    	this.turnsInto = turnsInto;
    	this.drops = drops;
        setTickRandomly(true);
        float f = 0.5F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        setHardness(0.0F);
        setStepSound(soundTypeGrass);
        disableStats();
        
    }


    @Override
    public Item getItemDropped(IBlockState meta, Random random, int fortune){
    	return drops;
    }
    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        
        return 1;
    }
	@Override
	public boolean canGrow(World arg0, BlockPos arg1, IBlockState arg2,
			boolean arg3) {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean canUseBonemeal(World arg0, Random arg1, BlockPos arg2,
			IBlockState arg3) {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public void grow(World arg0, Random arg1, BlockPos arg2, IBlockState arg3) {
		// TODO Auto-generated method stub
		arg0.setBlockState(new BlockPos(arg2.getX(), arg2.getY() - 1, arg2.getZ()), Blocks.farmland.getDefaultState());
		arg0.setBlockState(arg2, turnsInto.getDefaultState());
		arg0.setBlockState(new BlockPos(arg2.getX(), arg2.getY() - 1, arg2.getZ()), Blocks.farmland.getDefaultState());
		
	}
	@Override
	public void updateTick(World parWorld, BlockPos p, IBlockState state, Random parRand)
	{
	    super.updateTick(parWorld, p, state, parRand);
	    int stage = ((Integer)state.getValue(AGE)).intValue();
	    if (++stage > 4){
	    	this.grow(parWorld, parRand, p, state);
	    	return;
	    }
	    if (parWorld.getBlockState(p).getBlock() == this){
	    	parWorld.setBlockState(p, parWorld.getBlockState(p).withProperty(AGE, Integer.valueOf(stage)));
	    }
	    
	    
	}
	@Override
	protected BlockState createBlockState()
	{
	    return new BlockState(this, new IProperty[] {AGE});
	}
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
	    return getDefaultState().withProperty(AGE, Integer.valueOf(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
	    return ((Integer)state.getValue(AGE)).intValue();
	}
}
