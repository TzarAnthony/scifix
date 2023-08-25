package com.tzaranthony.scifix.api.handlers;

import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.INBTSerializable;

public class XpHandler implements INBTSerializable<Tag> {
    protected int xp;

    public XpHandler() {
        this.xp = 0;
    }

    public int getXp() {
        return this.xp;
    }

    public void addXp(int amt) {
        this.xp += xp;
    }

    public void popXp(ServerLevel level, Vec3 vec3) {
        ExperienceOrb.award(level, vec3, this.xp);
        this.xp = 0;
    }

    public Tag serializeNBT() {
        return IntTag.valueOf(this.getXp());
    }

    public void deserializeNBT(Tag nbt) {
        if (!(nbt instanceof IntTag intNbt))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        this.xp = intNbt.getAsInt();
    }
}