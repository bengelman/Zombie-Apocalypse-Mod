package com.pdogmuncher.zombieapocalypse.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemTent extends Item{
	public ItemTent(){
		super();
	}
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (!world.isRemote){
			BlockPos position = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
			for (int z = 0; z <5; z++){
				for (int i = 0; i < 5; i++){
					world.setBlockState(new BlockPos(position.getX() - 2 + i, position.getY(), position.getZ() - 2 + z), Blocks.wool.getStateFromMeta(13));
				}
			}
			for (int i = 0; i < 5; i++){
				world.setBlockState(new BlockPos(position.getX() - 2 + i, position.getY() + 1, position.getZ() - 2), Blocks.wool.getStateFromMeta(13));
			}
			for (int i = 0; i < 5; i++){
				world.setBlockState(new BlockPos(position.getX() - 2 + i, position.getY() + 1, position.getZ() + 2), Blocks.wool.getStateFromMeta(13));
			}
			
			for (int i = 0; i < 5; i++){
				world.setBlockState(new BlockPos(position.getX() - 2 + i, position.getY() + 2, position.getZ() - 1), Blocks.wool.getStateFromMeta(13));
			}
			for (int i = 0; i < 5; i++){
				world.setBlockState(new BlockPos(position.getX() - 2 + i, position.getY() + 2, position.getZ() + 1), Blocks.wool.getStateFromMeta(13));
			}
			
			for (int i = 0; i < 5; i++){
				world.setBlockState(new BlockPos(position.getX() - 2 + i, position.getY() + 3, position.getZ()), Blocks.wool.getStateFromMeta(13));
			}
			world.setBlockState(new BlockPos(position.getX() - 2, position.getY() + 1, position.getZ()), Blocks.wool.getStateFromMeta(13));
			world.setBlockState(new BlockPos(position.getX() - 2, position.getY() + 1, position.getZ()-1), Blocks.wool.getStateFromMeta(13));
			world.setBlockState(new BlockPos(position.getX() - 2, position.getY() + 1, position.getZ()+1), Blocks.wool.getStateFromMeta(13));
			world.setBlockState(new BlockPos(position.getX() - 2, position.getY() + 2, position.getZ()), Blocks.wool.getStateFromMeta(13));
			world.setBlockState(new BlockPos(position.getX() + 2, position.getY() + 1, position.getZ()-1), Blocks.wool.getStateFromMeta(13));
			world.setBlockState(new BlockPos(position.getX() + 2, position.getY() + 1, position.getZ()+1), Blocks.wool.getStateFromMeta(13));
			world.setBlockState(new BlockPos(position.getX(), position.getY() + 1, position.getZ()), Blocks.bed.getStateFromMeta(0x1));
			world.setBlockState(new BlockPos(position.getX() - 1, position.getY() + 1, position.getZ()), Blocks.bed.getStateFromMeta(9));
			
			world.setBlockState(new BlockPos(position.getX()+2, position.getY() + 1, position.getZ()), Blocks.acacia_door.getStateFromMeta(0));
			world.setBlockState(new BlockPos(position.getX() +2, position.getY() + 2, position.getZ()), Blocks.acacia_door.getStateFromMeta(8));
			
			world.setBlockState(new BlockPos(position.getX(), position.getY() + 1, position.getZ() - 1), Blocks.crafting_table.getDefaultState());
			world.setBlockState(new BlockPos(position.getX(), position.getY() + 1, position.getZ() + 1), Blocks.furnace.getDefaultState());
			player.inventory.consumeInventoryItem(this);
		}
		return stack;
	}
}
