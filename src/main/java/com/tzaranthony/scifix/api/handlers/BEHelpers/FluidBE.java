package com.tzaranthony.scifix.api.handlers.BEHelpers;

import com.tzaranthony.scifix.api.handlers.FluidHandler;
import net.minecraft.server.level.ServerLevel;

public interface FluidBE {
    void setFluidHandler(FluidHandler fh);

    FluidHandler getFluidHandler();
}