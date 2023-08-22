package com.tzaranthony.scifix.api.handlers;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class SCapabilities {
    public static final Capability<IHeatHandler> HEAT = CapabilityManager.get(new CapabilityToken<>(){});

    public static final Capability<IGasHandler> GAS = CapabilityManager.get(new CapabilityToken<>(){});
}