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
    //  Phase 1: finish basic steam generation, basic and intermediate production, world generation, item/fluid/energy storage and transport
    //      (1.1 create machine upgrade system?)
    //  Phase 2: finish advanced resource production, wood stuffs, & renewables
    //  Phase 3: nuclear engineering, portal system, & quantum computers
    //  Phase 4: bio engineering, nano tech, & particle accelerators (defence?)
    //  Phase 5: HSR & Maglevs for create

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