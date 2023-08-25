package com.tzaranthony.scifix.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tzaranthony.scifix.core.container.menus.CrusherGrinderMenu;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CrusherGrinderScreen extends EnergyScreen<CrusherGrinderMenu> {
    public static final ResourceLocation MENU = createSCIFIXrl("crusher");

    public CrusherGrinderScreen(CrusherGrinderMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    protected void renderBg(PoseStack stack, float partialTick, int x, int y) {
        int progX = 83;
        int progY = 43;
        int progXo = 176;
        int progYo = 51;
        int progH = 20;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MENU);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(stack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        renderEnergy(stack, i, j);

        int l = this.menu.getProcessProgress();
        this.blit(stack, i + progX, j + progY, progXo, progYo, progYo + progH + 1, l - 1);
    }
}