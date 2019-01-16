package com.pdogmuncher.zombieapocalypse.mobs;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityNametag extends EntityLiving{

	public EntityNametag(World p_i1595_1_) {
		super(p_i1595_1_);
		this.setSize(1.25f, 2.5f);
		// TODO Auto-generated constructor stub
	}
	public boolean interact (EntityPlayer p){
		if (this.ridingEntity != null){
			((EntityLiving)this.ridingEntity).interactFirst(p);
		}
		return true;
	}
	
}
