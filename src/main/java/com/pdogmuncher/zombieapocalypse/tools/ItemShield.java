package com.pdogmuncher.zombieapocalypse.tools;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ItemShield extends ItemSword{

	public ItemShield(ToolMaterial arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity){
	    return false;
	}

	
	
}
