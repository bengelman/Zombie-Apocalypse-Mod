package com.pdogmuncher.zombieapocalypse.dimension;

import com.pdogmuncher.zombieapocalypse.dimension.BlockEpicPortal.Size;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.world.World;

public class BlockActivator extends Block
{
public BlockActivator()
{
super(Material.rock);
setCreativeTab(CreativeTabs.tabBlock);
}

public void onBlockAdded(World par1World, BlockPos p)
{
	EpicTeleporter porter = new EpicTeleporter(MinecraftServer.getServer().worldServerForDimension(0));
	porter.makePortal(p);
}



}
