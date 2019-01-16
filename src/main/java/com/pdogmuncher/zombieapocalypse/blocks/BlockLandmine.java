package com.pdogmuncher.zombieapocalypse.blocks;

import java.util.Random;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockLandmine extends Block{
	public enum MineType {
		EXPLOSIVE, INCENDIARY, ALARM
	};
	public static final PropertyInteger ENABLED = PropertyInteger.create("enabled", 0, 5);
	public MineType type;
	public BlockLandmine(Material arg0, MineType type) {
		super(Material.tnt);
		this.setHarvestLevel("pickaxe", 1);
		this.setUnlocalizedName("landmine");
		this.setCreativeTab(ZombieApocalypse.tabAmmo);
		this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.9f, 1.0f);
		this.type = type;
		this.setHardness(1f);
		this.setResistance(1f);
		this.setTickRandomly(true);
		
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onEntityCollidedWithBlock(World w, BlockPos p, IBlockState s, Entity e){
		
		if (type == MineType.EXPLOSIVE){
			w.setBlockState(p.add(0, 1, 0), Blocks.air.getDefaultState());
			w.createExplosion(null, p.getX(), p.getY(), p.getZ(), 2.0f, false);
			w.setBlockState(p, Blocks.air.getDefaultState());
		}
		else if (type == MineType.INCENDIARY){
			e.setFire(5);
		}
		else if (type == MineType.ALARM){
			if (((Integer)s.getValue(ENABLED)).intValue() != 1){
				w.playSoundAtEntity(e, "zombieapocalypse:alarm", 2.0f, 1.0f);
				w.setBlockState(p, w.getBlockState(p).withProperty(ENABLED, Integer.valueOf(1)));
				if (!w.isRemote){
					e.addChatMessage(new ChatComponentText(e.getName() + " has triggered an alarm at " + p.getX() + " " + p.getY() + " " + p.getZ()));
				}
			}
		}
	}
	@Override
	public void updateTick(World parWorld, BlockPos p, IBlockState state, Random parRand)
	{
	    super.updateTick(parWorld, p, state, parRand);
	    if (((Integer)state.getValue(ENABLED)).intValue() == 1){
	    	parWorld.setBlockState(p, parWorld.getBlockState(p).withProperty(ENABLED, Integer.valueOf(0)));
	    }
	}
	@Override
	protected BlockState createBlockState()
	{
	    return new BlockState(this, new IProperty[] {ENABLED});
	}
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
	    return getDefaultState().withProperty(ENABLED, Integer.valueOf(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
	    return (Integer) (state.getValue(ENABLED));
	}
}
