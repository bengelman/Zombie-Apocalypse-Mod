package com.pdogmuncher.zombieapocalypse.tools;

import java.util.Set;

import scala.collection.mutable.MultiMap;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemSwissArmyKnife extends ItemPickaxe{
	public static Set effectiveBlocks = Sets.newHashSet(new Block[]{});
	public static Set effectiveBlocksAxe = Sets.newHashSet(new Block[]{Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, 
		    Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin});
	public static Set effectiveBlocksSpade = Sets.newHashSet(new Block[]{Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, 
		    Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, 
		    Blocks.soul_sand, Blocks.mycelium});
	int attachment = 0;
	public ItemSwissArmyKnife(ToolMaterial p_i45327_1_) {
		super(p_i45327_1_);
		this.efficiencyOnProperMaterial = p_i45327_1_.getEfficiencyOnProperMaterial();
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity){
		if (stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setInteger("tool", 3);
		attachment = 3;
		this.effectiveBlocks = Sets.newHashSet();
		if (player.worldObj.isRemote){
			player.addChatMessage(new ChatComponentText("Switched To Knife Tool"));
		}
		return super.onLeftClickEntity(stack, player, entity);
	}
	@Override
	public float getDigSpeed(ItemStack stack, IBlockState state){
		if (/*stack.getTagCompound().getInteger("tool")*/ attachment == 2){
			return effectiveBlocks.contains(state.getBlock()) ? this.efficiencyOnProperMaterial : super.getDigSpeed(stack, state);
		}
		else{
			return effectiveBlocks.contains(state.getBlock()) ? this.efficiencyOnProperMaterial : new ItemSword(ToolMaterial.IRON).getDigSpeed(stack, state);
		}
	}
	@Override
	public Set getToolClasses(ItemStack stack){
		return ImmutableSet.of("pickaxe", "axe", "shovel", "hoe");
	}
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (world.isRemote){
			return stack;
		}
		if (Minecraft.getMinecraft().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK){
			if ((world.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock() == Blocks.dirt) || (world.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock() == Blocks.grass)){
				world.setBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos(), Blocks.farmland.getDefaultState());
				this.attachment = 4;
				this.effectiveBlocks = Sets.newHashSet();
				if (world.isRemote){
					player.addChatMessage(new ChatComponentText("Switched To Hoe Tool"));
				}
				return stack;
			}
		}
		if (stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
		}
		int currentTool = /*stack.getTagCompound().getInteger("tool")*/ attachment;
		currentTool++;
		currentTool = currentTool % 3;
		if (currentTool == 0){
			attachment = 0;
			this.effectiveBlocks = effectiveBlocksAxe;
			if (!world.isRemote){
				player.addChatMessage(new ChatComponentText("Switched To Axe Tool"));
			}
			
		}
		else if (currentTool == 1){
			attachment = 1;
			this.effectiveBlocks = effectiveBlocksSpade;
			if (!world.isRemote){
				player.addChatMessage(new ChatComponentText("Switched To Shovel Tool"));
			}
			
		}
		else if (currentTool == 2){
			attachment = 2;
			if (!world.isRemote){
				player.addChatMessage(new ChatComponentText("Switched To Pickaxe Tool"));
			}
			
		}
		stack.getTagCompound().setInteger("tool", currentTool);
		attachment = currentTool;
		return stack;
	}
}
