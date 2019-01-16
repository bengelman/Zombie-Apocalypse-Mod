package com.pdogmuncher.zombieapocalypse.items;

import java.util.Random;

import com.google.common.collect.Sets;
import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemLantern extends ItemTool{
	public ItemLantern(){
		super(0, ToolMaterial.WOOD, Sets.newHashSet());
	}
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int number, boolean bool){
		if (stack.getItemDamage() >= stack.getMaxDamage() - 1){
			stack = null;
		}
		int x = (int) entity.posX;
		int y = (int) entity.posY;
		int z = (int) entity.posZ;
		for (int i = x - 5; i < x+5; i++){
			for (int j = y - 5; j < y+5; j++){
				for (int k = z - 5; k < z+5; k++){
					if (world.getBlockState(new BlockPos(i, j, k)).getBlock() == ZombieApocalypse.light){
						world.setBlockState(new BlockPos(i, j, k), Blocks.air.getDefaultState());
					}
				}
			}
		}
		if (!(entity instanceof EntityPlayer)){
			return;
		}
		if (((EntityPlayer)entity).getCurrentEquippedItem() != stack){
			return;
		}
		if (new Random().nextInt() % 200 == 0){
			stack.attemptDamageItem(1, new Random());
		}
		if (world.getBlockState(entity.getPosition()).getBlock() == Blocks.air){
			entity.worldObj.setBlockState(
					   entity.getPosition(), 
					   ZombieApocalypse.light.getDefaultState());
		}
		
	}
}
