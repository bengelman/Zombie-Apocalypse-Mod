package com.pdogmuncher.zombieapocalypse.items;

import com.pdogmuncher.zombieapocalypse.dimension.EpicTeleporter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
//This key is the portal to the dragon dimension. OOOOOOOOOOH SPOOOOOOOOOKY
public class ItemEntranceKey extends Item{
	boolean entrance;
	public ItemEntranceKey(boolean entrance){
		super();
		this.entrance = entrance;
	}
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer p){
		if (p instanceof EntityPlayerMP){
			EntityPlayerMP player = (EntityPlayerMP) p;
            player.playSound("portal.portal", 1.0f, 1.0f);
            if (player.dimension != 3 && entrance){
            	player.mcServer.getConfigurationManager().transferPlayerToDimension(player, 3, new EpicTeleporter(player.mcServer.worldServerForDimension(3)));
            }
            if (player.dimension == 3 && !entrance){
            	player.mcServer.getConfigurationManager().transferPlayerToDimension(player, 0, new EpicTeleporter(player.mcServer.worldServerForDimension(0)));
            }
		}
		return stack;
	}
}
