package com.pdogmuncher.zombieapocalypse.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;


public class KeyInputHandler {
	public static boolean scoped;
	public static boolean shifting;
	
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        
        if(KeyBindings.pong.isPressed()){
        	scoped = false;
        	Minecraft.getMinecraft().gameSettings.fovSetting = 70;
        	GuiBuffBar.fovNormal = 70;
        }
    }

}