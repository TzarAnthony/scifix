package com.tzaranthony.scifix.registries;

import com.tzaranthony.scifix.client.screen.CrusherGrinderScreen;
import com.tzaranthony.scifix.client.screen.ElectricFurnaceScreen;
import net.minecraft.client.gui.screens.MenuScreens;

public class SScreenRender {
    public static void renderScreens() {
        MenuScreens.register(SMenus.FURNACE.get(), ElectricFurnaceScreen::new);
        MenuScreens.register(SMenus.CRUSHER_GRINDER.get(), CrusherGrinderScreen::new);
    }
}