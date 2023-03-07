package customclient.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonTransparent extends GuiButton {
	private ResourceLocation btnbg = new ResourceLocation("customclient/textures/gui/transparentButtons.png");

	
	public GuiButtonTransparent(int buttonID, int xPos, int yPos, int widthIn, int heightIn, String text)
    {
        super(buttonID, xPos, yPos, widthIn, heightIn, text);
    }
	
	public GuiButtonTransparent(int buttonID, int xPos, int yPos, String text)
    {
        super(buttonID, xPos, yPos, 200, 20, text);
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
    	if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRendererObj;
            
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            mc.getTextureManager().bindTexture(btnbg);
            int i = this.getHoverState(this.hovered);

            drawTransparentBtn(this.xPosition, this.yPosition, this.width, this.height, this.hovered);
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = 16777120;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
        }
    }
    

    /**
     * Renders the specified text to the screen, center-aligned. Args : renderer, string, x, y, color
     */
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawString(text, (int)(x - fontRendererIn.getStringWidth(text) / 2), (int)y, color);
    }
    
    private void drawTransparentBtn(int x, int y, int w, int h, boolean hovered) {
    	GlStateManager.pushMatrix();
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
    	int leftWidth = w-(w/2);
    	this.drawTexturedModalRect(x, y, 0, hovered ? 20 : 0, (w/2), h);
    	this.drawTexturedModalRect(x + (int)(w/2), y, 200 - leftWidth, hovered ? 20 : 0, w/2, h);
    	GlStateManager.popMatrix();
    }

}
