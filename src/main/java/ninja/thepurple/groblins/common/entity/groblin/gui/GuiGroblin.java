package ninja.thepurple.groblins.common.entity.groblin.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import ninja.thepurple.groblins.GroblinsMod;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;

public class GuiGroblin extends GuiContainer {
    private static final ResourceLocation groblinGuiTextures =
            new ResourceLocation(GroblinsMod.MODID
                    +":textures/gui/groblin_54.png");
    private final InventoryPlayer playerInventory;
    private final IInventory groblinInventory;
    private final int inventoryRows;

    public GuiGroblin(InventoryPlayer playerInventory,
                      IInventory groblinInventory)
    {
        super(new ContainerGroblin(playerInventory, groblinInventory));
        this.playerInventory = playerInventory;
        this.groblinInventory = groblinInventory;

        this.inventoryRows = (int)Math.ceil(groblinInventory.getSizeInventory() / 9.0D);
        this.ySize = 114 + this.inventoryRows * 18;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = groblinInventory.getDisplayName().getUnformattedText();
        fontRendererObj.drawString(s, xSize/2-fontRendererObj
                .getStringWidth(s)/2, 6, 4210752);
        fontRendererObj.drawString(playerInventory.getDisplayName()
                .getUnformattedText(), 8, ySize - 96 + 2, 4210752);
    }

    /**
     * Args : renderPartialTicks, mouseX, mouseY
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks,
                                                   int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(groblinGuiTextures);
        int marginHorizontal = (width - xSize) / 2;
        int marginVertical = (height - ySize) / 2;

        this.drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(marginHorizontal, marginVertical + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }

}
