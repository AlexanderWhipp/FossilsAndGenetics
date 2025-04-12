package net.shadowtek.fossilgencraft.screen.custom.genesplicer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.screen.custom.FreezeDryerMenu;
import net.shadowtek.fossilgencraft.util.CoverageValidator;

import java.awt.*;

public class GeneSplicerComputerScreen extends AbstractContainerScreen<GeneSplicerComputerMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "textures/gui/genesplicer/gene_splicer_gui.png");
    private static final int imageWidth = 512;  // Adjust according to your image dimensions
    private static final int imageHeight = 512;



    public GeneSplicerComputerScreen(GeneSplicerComputerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);


    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth)/2;
        int y = (height - imageHeight)/2;

        pGuiGraphics.blit(GUI_TEXTURE, x, y, -125, -100, imageWidth, imageHeight, 512, 512);


    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);





    }

}

