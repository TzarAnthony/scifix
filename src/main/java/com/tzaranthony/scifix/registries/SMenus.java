package com.tzaranthony.scifix.registries;

import com.tzaranthony.scifix.Scifix;
import com.tzaranthony.scifix.core.container.menus.CrusherGrinderMenu;
import com.tzaranthony.scifix.core.container.menus.ElectricFurnaceMenu;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SMenus {
    public static final DeferredRegister<MenuType<?>> reg = DeferredRegister.create(Registry.MENU_REGISTRY, Scifix.MOD_ID);

    public static final RegistryObject<MenuType<ElectricFurnaceMenu>> FURNACE = reg.register("electric_furnace", () -> IForgeMenuType.create((id, inv, data) -> new ElectricFurnaceMenu(id, inv, data.readBlockPos())));
    public static final RegistryObject<MenuType<CrusherGrinderMenu>> CRUSHER_GRINDER = reg.register("crusher_grinder", () -> IForgeMenuType.create((id, inv, data) -> new CrusherGrinderMenu(id, inv, data.readBlockPos())));
}