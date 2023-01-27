package emt.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import emt.EMT;
import emt.util.EMTTextHelper;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class EMTKeys {
    public static KeyBinding keyUnequip;

    public static void registerKeys() {
        keyUnequip = new KeyBinding(EMTTextHelper.localize("gui.EMT.key.unequip"), Keyboard.KEY_NONE, EMT.NAME);
        ClientRegistry.registerKeyBinding(keyUnequip);
    }
}
