package com.tzaranthony.scifix.api.handlers;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class SCapabilities {
    public static final Capability<IHeatExchanger> HEAT = CapabilityManager.get(new CapabilityToken<>(){});

}