package com.tzaranthony.scifix.api.handlers.BEHelpers;

import com.tzaranthony.scifix.api.handlers.XpHandler;
import net.minecraft.server.level.ServerLevel;

public interface XpHoldingBE {
    XpHandler getXpHandler();

    void dropXp(ServerLevel level);
}