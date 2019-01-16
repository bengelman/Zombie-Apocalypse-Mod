package com.pdogmuncher.zombieapocalypse.events;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.tools.Firearm;

public class FMLEventsHandler {
	@SubscribeEvent
	   public void crafting(ItemCraftedEvent event){
		   if(event.crafting.getItem() == ZombieApocalypse.amulet)
		   {
			   event.player.addStat(ZombieApocalypse.getAmuletAchievement, 1);
		   }
		   if (event.crafting.getItem() instanceof Firearm){
			   event.player.addStat(ZombieApocalypse.craftGunAchievement, 1);
		   }
	   }
}
