package com.tzaranthony.scifix;

import com.mojang.logging.LogUtils;
import com.tzaranthony.scifix.registries.SBlockEntities;
import com.tzaranthony.scifix.registries.SBlocks;
import com.tzaranthony.scifix.registries.SItems;
import com.tzaranthony.scifix.registries.SRecipes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

@Mod("scifix")
public class Scifix {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "scifix";

    //TODO: Configs
    //  Cool GUI vs Vanilla GUI
    //  See Items
    //  See Blocks
    //  Do I wan Patchouli or make my own book....

    //TODO:
    //  Base Game:
    //      Phase 1: steam & energy generation, ore refining, electrolysis, world generation, item/fluid/energy storage and transport, wood stuffs
    //      Phase 2: advanced production / automation, photovoltaics and wind (maybe water too)
    //  Expansion 0: decoration additions
    //  Expansion 1: nuclear engineering, particle accelerators, portal system, & quantum computers
    //  Expansion 2: bio engineering, cybernetics, nano tech (& defence?)
    //  Expansion 3: defense
    //  Compatibility: HSR & Maglevs for create, programmable drones for CC Tweaked, Tinker's multi-casting (seems kinda useless to me since tinkers doesn't work on an industrial scale)

    public Scifix() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::CommonSetup);
        bus.addListener(this::ClientSetup);
        bus.addListener(this::CompleteSetup);

        SBlocks.reg.register(bus);
        SItems.reg.register(bus);
        SBlockEntities.reg.register(bus);
//        SFeatures.reg.register((bus));
//        SSounds.reg.register(bus);
//        SParticleTypes.reg.register(bus);
//        SEffects.reg.register(bus);
//        SMenus.reg.register(bus);
        SRecipes.reg.register(bus);
//        SFluids.reg.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
//        MinecraftForge.EVENT_BUS.register(new ServerEvents());
    }

    private void CommonSetup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void ClientSetup(final FMLClientSetupEvent event) {
//        SKeybinds.registerKeybinds();
//        SBlocksRender.renderBlocks();
//        SBlockEntityRender.renderBlockEntities();
//        SItemsRender.renderItemProperties();
//        SScreenRender.renderScreens();
//        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }

    private void CompleteSetup(final FMLLoadCompleteEvent event) {
    }

    public static final CreativeModeTab TAB = new CreativeModeTab("scifix") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(SItems.GEAR_BOX_MK1.get());
        }
    };
}