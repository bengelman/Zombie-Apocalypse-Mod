package com.pdogmuncher.zombieapocalypse.tools;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;

public class ItemKnife extends ItemSword{

	public ItemKnife(ToolMaterial material) {
		super(material);
		
		// TODO Auto-generated constructor stub
	}
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (!world.isRemote){
			world.playSoundAtEntity(player, "random.bow", 1.0f, 1.0f);
			EntityKnife knife = new EntityKnife(world, player);
			//knife.addVelocity(knife.motionX, knife.motionY, knife.motionZ);
			world.spawnEntityInWorld(knife);
			player.inventory.consumeInventoryItem(this);
		}
		return stack;
	}
}
