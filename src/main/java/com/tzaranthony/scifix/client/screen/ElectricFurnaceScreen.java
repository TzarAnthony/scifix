package com.tzaranthony.scifix.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tzaranthony.scifix.core.container.menus.ElectricFurnaceMenu;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ElectricFurnaceScreen extends EnergyScreen<ElectricFurnaceMenu> {
    public static final ResourceLocation MENU = createSCIFIXrl("electric_furnace");

    public ElectricFurnaceScreen(ElectricFurnaceMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    protected void renderBg(PoseStack stack, float partialTick, int x, int y) {
        int progX = 79;
        int progY = 34;
        int progXo = 176;
        int progYo = 51;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MENU);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(stack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        renderEnergy(stack, i, j);

        this.blit(stack, i + progX, j + progY, progXo, progYo, this.menu.getProcessProgress(), 17);
    }
}