package customclient.gui.settings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonSettings extends GuiButton {

	private ResourceLocation btn = new ResourceLocation("customclient/textures/gui/icon/settingsButton.png");
    public GuiButtonSettings(int buttonID, int xPos, int yPos)
    {
        super(buttonID, xPos, yPos, 20, 20, "");
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            
            super.drawButton(mc, mouseX, mouseY);

            mc.getTextureManager().bindTexture(btn);
            GlStateManager.enableBlend();
            
            float scale = 15f/256f;
            GlStateManager.pushMatrix();
            GlStateManager.color(0.6f , 0.6f, 0.6f);
            GlStateManager.scale(scale, scale, 1);
            this.drawTexturedModalRect((int)(this.xPosition+2.5)/scale, (int)(this.yPosition+2.5)/scale, 0, 0, 256, 256);
            GlStateManager.popMatrix();
        }
    }
    
    

}
