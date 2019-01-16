package com.pdogmuncher.zombieapocalypse.items;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionShotDamage extends Potion {

	public PotionShotDamage(int arg0, ResourceLocation arg1, boolean arg2,
			int arg3, String name, int index1, int index2) {
		super(arg0, arg1, arg2, arg3);
		this.setPotionName(name);
		this.setIconIndex(index1, index2);
		// TODO Auto-generated constructor stub
	}

}
