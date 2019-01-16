package com.pdogmuncher.zombieapocalypse.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockBlock1 extends Block {
	public BlockBlock1 (int i, Material carpet){
		super (carpet);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundTypeCloth);
		this.setHardness(5f);
		this.setResistance(10f);
		this.setLightLevel(1.0f);
		
	}
	@Override
	public void onNeighborBlockChange(World world, BlockPos p, IBlockState state, Block block) {
		//System.out.println("Method Called");
	    if (!world.isRemote &&
		    world.isBlockPowered(p)) {
	    	
	    	System.out.println(world.getBlockState(new BlockPos(maxX, maxY + 1, maxZ)).getBlock().getUnlocalizedName());
	    	world.createExplosion((Entity)null, p.getX(), p.getY(), p.getZ(), 100.0f, true);
	    	//Do stuff here
	    }
    }
}
