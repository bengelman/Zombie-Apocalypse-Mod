package com.pdogmuncher.zombieapocalypse.items;

import java.util.List;
import java.util.Random;

import com.pdogmuncher.zombieapocalypse.mobs.EntityBossDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityCosmoDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityEarthDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityFireDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityIceDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityLightningDragon;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemDragonFlute extends ItemFood{
	//A flute that summons a dragon and draws dragons towards you. Fun.
	public ItemDragonFlute(){
		super(0, true);
		this.setAlwaysEdible();
		this.setMaxStackSize(1);
	}
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		//List list = world.getEn
		super.onItemRightClick(stack, world, player);
		if (world.isRemote){
			return stack;
		}
		
		List pounded = world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.fromBounds((double)player.posX, (double)player.posY, (double)player.posZ, (double)(player.posX + 1), (double)(player.posY + 1), (double)(player.posZ + 1)).expand(200, 300.0D, 200));
		player.playSound("mob.zombie.unfect", 1.0f, 1.0f);
		for (int i = 0; i < pounded.size(); i++){
			if (pounded.get(i) instanceof EntityBossDragon){
				EntityBossDragon living = (EntityBossDragon) pounded.get(i);
				living.getMoveHelper().setMoveTo(player.posX, player.posY, player.posZ, 1.0D);
				//System.out.println("Dragon moving from " + living.posX + " " + living.posZ + " to " + living.getMoveHelper().func_179917_d() + " " + living.getMoveHelper().func_179919_e());
			}
		}
		
		return stack;
	}
	public void onFoodEaten(ItemStack stack, World world, EntityPlayer player){
		stack.stackSize++;
		if (world.isRemote || player.dimension != 3){
			return;
		}
		EntityBossDragon d = null;
		switch(new Random().nextInt() % 5){
			case 0:
				d = new EntityFireDragon(world);
				break;
			case 1:
				d = new EntityEarthDragon(world);
				break;
			case 2:
				d = new EntityCosmoDragon(world);
			case 3:
				d = new EntityLightningDragon(world);
			default:
				d = new EntityIceDragon(world);
				break;
		}
		d.setPosition(player.posX, player.posY + 50, player.posZ);
		world.spawnEntityInWorld(d);
		d.getMoveHelper().setMoveTo(player.posX, player.posY, player.posZ, 1.0D);
	}
	public EnumAction getItemUseAction(ItemStack stack){
		return EnumAction.DRINK;
	}
}
