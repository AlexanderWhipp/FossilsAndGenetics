package net.shadowtek.fossilgencraft.screen.custom.supercomputer;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.network.NetworkHandler;
import net.shadowtek.fossilgencraft.network.SButtonPressPacket;



public class SuperComputerScreen extends AbstractContainerScreen<SuperComputerMenu> {


    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "textures/gui/supercomputer/supercomputer_gui.png");


    public SuperComputerScreen(SuperComputerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        // Define GUI size based on your texture (these are typical for standard chests/furnaces)
        this.imageWidth = 176;
        this.imageHeight = 166; // Adjust if your texture is different
        // Adjust where the title is drawn (relative to top-left corner of GUI texture) if needed
        // this.titleLabelX = ...;
        // this.titleLabelY = ...;
        // Player inventory label position (usually needed if drawing player inv, but we aren't)
        // this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        // Calculate button position (example: centered at the bottom)
        int buttonX = this.width / 2 - 50;
        int buttonY = this.height - 40;
        int buttonWidth = 100;
        int buttonHeight = 20;

        Button myButton = Button.builder(Component.literal("Click Me"), button -> {
           try {
               NetworkHandler.sendToServer(new SButtonPressPacket());
           }  catch (Exception e){
               System.err.println("Packet Failed To send!");
           }
        }).pos(buttonX, buttonY).size(buttonWidth, buttonHeight).build();

        addRenderableWidget(myButton);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        // RenderSystem.setShaderTexture(0, TEXTURE); // No longer needed with GuiGraphics
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        // Calculate top-left corner position for centering GUI
        int x = this.leftPos; // Use leftPos/topPos calculated by superclass
        int y = this.topPos;
        // Draw the background texture
        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);

        // --- Draw Progress Bar based on ContainerData ---
        if (this.menu.isCrafting()) { // Use helper from menu
            int progress = this.menu.getProgress();
            int maxProgress = this.menu.getMaxProgress();
            int scaledProgress = maxProgress != 0 && progress != 0 ? progress * 24 / maxProgress : 0; // Scale to 24 pixels wide (example)
            // Assuming progress bar texture region starts at (176, 0) in the texture file and is 24x16 pixels
            pGuiGraphics.blit(TEXTURE, x + 80, y + 50, 176, 0, scaledProgress, 16); // Adjust x,y pos and texture UV (176,0)
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        // Render the dark background tint
        this.renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        // Render the GUI background and progress bar etc.
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        // Render tooltips (like for inventory slots, if any) AFTER super.render
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        // TODO: Add rendering for your research list, active state, buttons etc. here
        // TODO: Add tooltips for research topics on hover
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        // Don't call super.renderLabels if you don't want player inventory label drawn
        // Draw the main title (defined in BE's getDisplayName -> Menu -> Screen constructor)
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false); // 4210752 is default text color

        // Render other labels here if needed
    }

    // --- Add mouseClicked later for sending StartResearchPacket ---
    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        // TODO: Add logic here later:
        // 1. Calculate mouse position relative to GUI (pMouseX - leftPos, pMouseY - topPos)
        // 2. Check if click is within bounds of a research topic button/element
        // 3. If yes, get the topicId string associated with that element
        // 4. Send the packet: Networking.sendToServer(new StartResearchPacket(topicId));
        // 5. Return true (to indicate click was handled)

        // Allow default handling (e.g., for potential future widgets)
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
}
