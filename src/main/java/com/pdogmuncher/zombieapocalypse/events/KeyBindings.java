package com.pdogmuncher.zombieapocalypse.events;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindings {

    // Declare two KeyBindings, ping and pong
    //public static KeyBinding ping;
    public static KeyBinding pong;
    public static KeyBinding vehicleAbility;
    public static KeyBinding reload;
    //public static KeyBinding firegun;

    public static void init() {
        // Define the "ping" binding, with (unlocalized) name "key.ping" and
        // the category with (unlocalized) name "key.categories.mymod" and
        // key code 24 ("O", LWJGL constant: Keyboard.KEY_O)
        //ping = new KeyBinding("key.ping", Keyboard.KEY_O, "key.categories.zombieapocalypse");
        // Define the "pong" binding, with (unlocalized) name "key.pong" and
        // the category with (unlocalized) name "key.categories.mymod" and
        // key code 25 ("P", LWJGL constant: Keyboard.KEY_P)
        pong = new KeyBinding("key.pong", Keyboard.KEY_R, "key.categories.zombieapocalypse");
        reload = new KeyBinding("key.reload", Keyboard.KEY_C, "key.categories.zombieapocalypse");
        vehicleAbility = new KeyBinding("Vehicle Ability", Keyboard.KEY_F, "key.categories.zombieapocalypse");
        //firegun = new KeyBinding("Fire Gun", Keyboard.KEY_Q, "key.categories.zombieapocalypse");
        // Register both KeyBindings to the ClientRegistry
        //ClientRegistry.registerKeyBinding(ping);
        ClientRegistry.registerKeyBinding(pong);
        ClientRegistry.registerKeyBinding(reload);
        ClientRegistry.registerKeyBinding(vehicleAbility);
        //ClientRegistry.registerKeyBinding(firegun);
    }

}
