package com.tzaranthony.scifix.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tzaranthony.scifix.Scifix;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class BaseScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    public BaseScreen(T menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    public void render(PoseStack poseStack, int x, int y, float partialTick) {
        renderBackground(poseStack);
        super.render(poseStack, x, y, partialTick);
        this.renderTooltip(poseStack, x, y);
    }

    protected static ResourceLocation createSCIFIXrl(String name) {
        return createResourceLocation(Scifix.MOD_ID, name);
    }

    protected static ResourceLocation createResourceLocation(String modID, String name) {
        return new ResourceLocation(modID, "textures/gui/container/" + name + ".png");
    }

    protected boolean isHoveringArea(Rect2i area, int mouseX, int mouseY) {
        return (area.getX() <= mouseX) && ((area.getX() + area.getWidth()) > mouseX) && (area.getY() <= mouseY) && ((area.getY() + area.getHeight()) > mouseY);
    }
}