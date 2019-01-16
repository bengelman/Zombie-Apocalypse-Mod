package com.pdogmuncher.zombieapocalypse.tools;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

public class Blueprint extends Item{
	public Firearm createdFirearm;
	public List<ItemStack> materials = new ArrayList<ItemStack>();
	public Blueprint(List<ItemStack> materials, Firearm given){
		super();
		this.materials = materials;
		this.createdFirearm = given;
	}
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		boolean canConsume = true;
		for (int i = 0; i < materials.size(); i++){
			if (!player.inventory.hasItemStack(materials.get(i))){
				canConsume = false;
				break;
			}
		}
		if (canConsume){
			for (int i = 0; i < materials.size(); i++){
				ItemStack material = materials.get(i);
				for (int j = 0; j < material.stackSize; j++){
					player.inventory.consumeInventoryItem(material.getItem());
				}
				
			}
			player.addExperienceLevel(-10);
			player.inventory.addItemStackToInventory(new ItemStack(createdFirearm));
			player.playSound("random.anvil_use", 0.5f, 1.0f);
		}
		return stack;
	}
	@Override
	  public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	  {
		par3List.add("Creates: " + new ItemStack(createdFirearm).getDisplayName());
		par3List.add("Consumes:");
		  for (int i = 0; i < materials.size(); i++){
			  String addition = materials.get(i).getDisplayName();
			  addition += ": " + materials.get(i).stackSize;
			  par3List.add(addition);
		  }
	  }
}
