package com.tzaranthony.scifix.api.handlers;

public interface IGasHandler {
    float getWeight();

    float drainGas(float kg);
}