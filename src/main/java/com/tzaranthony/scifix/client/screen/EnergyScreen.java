package com.tzaranthony.scifix.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tzaranthony.scifix.api.handlers.BEHelpers.EnergyBE;
import com.tzaranthony.scifix.api.handlers.EnergyHandler;
import com.tzaranthony.scifix.core.container.menus.ElectricMenu;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.Optional;

public abstract class EnergyScreen<T extends ElectricMenu> extends BaseScreen<T> {
    protected Rect2i area;
    private int eX = 12;
    private int eY = 16;
    private int eXo = 176;
    private int eYo = 0;
    private int eW = 8;
    private int eH = 50;

    public EnergyScreen(T menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    public void render(PoseStack poseStack, int x, int y, float partialTick) {
        renderBackground(poseStack);
        super.render(poseStack, x, y, partialTick);
        this.renderTooltip(poseStack, x, y);
    }

    @Override
    protected void init() {
        super.init();
        createEnergyArea();
    }

    protected void createEnergyArea() {
        int xMod = (this.width - this.imageWidth) / 2;
        int yMod = (this.height - this.imageHeight) / 2;
        this.area = new Rect2i(eX + xMod, eY + yMod, eW, eH);
    }

    @Override
    protected void renderTooltip(PoseStack stack, int mouseX, int mouseY) {
        super.renderTooltip(stack, mouseX, mouseY);

        if (this.isHoveringArea(this.area, mouseX, mouseY)) {
            renderTooltip(stack, this.getTooltipFromEnergy(), Optional.empty(), mouseX, mouseY);
        }
    }

    protected List<Component> getTooltipFromEnergy() {
        EnergyHandler eh = this.menu.getEnergyHandler();
        return List.of(Component.nullToEmpty(eh.getEnergyStored() + " / " + eh.getCapacity() + " RF"));
    }

    protected int getEnergyPercentage() {
        EnergyHandler eh = this.menu.getEnergyHandler();
        return eh.getEnergyStored() * 50 / eh.getCapacity();
    }

    protected void renderEnergy(PoseStack poseStack, int i, int j) {
        int k = this.getEnergyPercentage();
        this.blit(poseStack, i + eX, j + eY + eH - k, eXo, eH - k, eYo + eH + 1, k);
    }

    protected void setEnergyComponentCoordinates(int xBlank, int yBlank, int xFull, int yFull) {
        this.eX = xBlank;
        this.eY = yBlank;
        this.eXo = xFull;
        this.eYo = yFull;
    }
}