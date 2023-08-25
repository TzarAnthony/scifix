package com.tzaranthony.scifix;

import com.mojang.logging.LogUtils;
import com.tzaranthony.scifix.registries.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod("scifix")
public class Scifix {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "scifix";

    //TODO: Configs
    //  Cool GUI vs Vanilla GUI
    //  See Items
    //  See Blocks
    //  Do I want Patchouli or make my own book....

    //TODO:
    //  Base Game:
    //      Phase 1: steam & energy generation, ore refining, intermediate production, world generation
    //      Phase 2: item/fluid/energy storage and transport networks
    //      Phase 3: wood stuffs, advanced production / automation, photovoltaics and wind (maybe water too)
    //  Expansion 0 (Scifix Decorations): decoration additions
    //  Expansion 1 (Scifix Nuclear Engineering Plus): nuclear engineering, particle accelerators, portal system, & quantum computers, nano tech?
    //  Expansion 2 (Scifix Biomodding): bio engineering, cybernetics, nano tech?
    //  Expansion 3 (Scifix Intergalatic (Rockets sold separately)): space elevator for trading with alien species (energy and assembled products for exotic materials)
    //  Expansion 4 (Scifix Military Industrial Complex): defense
    //  Compatibility:
    //      Create: HSR & Maglevs, direct SU generation from turbines, heat compatibility, add ponders
    //      CC Tweaked: programmable drones for
    //      Tinker's: multi-casting (seems kinda useless to me since tinkers doesn't work on an industrial scale)
    //      Mekanism: gas compatibility? Who knows it may happen (i doubt it)

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
        SMenus.reg.register(bus);
        SRecipes.reg.register(bus);
        SFluids.reg.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
//        MinecraftForge.EVENT_BUS.register(new ServerEvents());
    }

    private void CommonSetup(final FMLCommonSetupEvent event) {
        SPackets.registerPackets();
    }

    private void ClientSetup(final FMLClientSetupEvent event) {
//        SKeybinds.registerKeybinds();
//        SBlocksRender.renderBlocks();
//        SBlockEntityRender.renderBlockEntities();
//        SItemsRender.renderItemProperties();
        SScreenRender.renderScreens();
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