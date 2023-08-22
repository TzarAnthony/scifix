package com.tzaranthony.scifix.api.handlers;

public interface IDirectional {
    enum Direction {
        INPUT(true, false),
        OUTPUT(false, true),
        EITHER(true, true),
        NONE(false, false);

        private final boolean canInput;
        private final boolean canOutput;

        Direction(boolean canInput, boolean canOutput) {
            this.canInput = canInput;
            this.canOutput = canOutput;
        }

        public boolean canInput() {
            return this.canInput;
        }

        public boolean canOutput() {
            return this.canOutput;
        }
    }

    void setDirection(Direction direction);

    Direction getDirection();
}