package com.pdogmuncher.zombieapocalypse.blocks;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBomb extends TileEntity implements IUpdatePlayerListBox{
	public int fusetime = 0;
	@Override
	public void writeToNBT(NBTTagCompound par1)
	{
		super.writeToNBT(par1);
		par1.setInteger("fusetime", Integer.valueOf(fusetime));
	}

	@Override
	public void readFromNBT(NBTTagCompound par1)
	{
		super.readFromNBT(par1);
		fusetime = par1.getInteger("fusetime");
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		fusetime++;
		if (fusetime > 600){
			this.worldObj.createExplosion(null, this.pos.getX(), this.pos.getY(), this.pos.getZ(), 50.0f, false);
			worldObj.setBlockState(this.pos, Blocks.air.getDefaultState());
		}
	}
}
