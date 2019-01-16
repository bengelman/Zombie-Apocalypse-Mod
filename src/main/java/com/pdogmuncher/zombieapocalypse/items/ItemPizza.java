package com.pdogmuncher.zombieapocalypse.items;

import com.pdogmuncher.zombieapocalypse.dimension.EpicTeleporter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemPizza extends ItemFood{

	public ItemPizza(int p_i45340_1_, boolean p_i45340_2_) {
		super(p_i45340_1_, p_i45340_2_);
		this.setAlwaysEdible();
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        if (!par2World.isRemote)
        { 
            par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.heal.id, 1, 2));
            //par3EntityPlayer.travelToDimension(3);
            
        }
 
    }
}
