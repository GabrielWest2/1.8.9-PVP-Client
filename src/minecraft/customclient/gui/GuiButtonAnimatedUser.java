package customclient.gui;

import java.awt.Color;

import customclient.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonAnimatedUser extends GuiButton
{


	private ResourceLocation btnbg = new ResourceLocation("customclient/textures/gui/transparentButtons.png");
	private ResourceLocation exit = new ResourceLocation("customclient/textures/gui/icon/exitButton.png");
	private ResourceLocation exitHover = new ResourceLocation("customclient/textures/gui/icon/exitButtonHover.png");
	private ResourceLocation headImage;
	private float scaleSpeed = 25f;
	private float maxWidth = 115;
	private float exitOffset;
	private float width;
    public GuiButtonAnimatedUser(int buttonID, int xPos, int yPos, ResourceLocation headImage)
    {
        super(buttonID, xPos, yPos, 20, 20, "");
        this.headImage = headImage;
	    this.maxWidth = 20 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(displayString);
        this.width = 20;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
    	this.exitOffset =  36 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(mc.session.getUsername());
    	this.maxWidth = 13 + exitOffset-3;
        if (this.visible)
        {

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            
            if(flag) {
            	this.width += scaleSpeed;
            	if(this.width > maxWidth)
            		this.width = maxWidth;
            }else {
            	this.width -= scaleSpeed;
            	if(this.width < 20)
            		this.width = 20;
            }
            
            
            mc.getTextureManager().bindTexture(btnbg);
            drawTransparentBtn(this.xPosition, this.yPosition, (int)this.width, this.height, flag);
            if(mc.session != null) {
	            float f = this.width-20;
	            float percentage = f/(this.maxWidth-20);
	            
	            if(headImage != null) {
	    	        mc.getTextureManager().bindTexture(headImage);
	    	        GlStateManager.disableColorLogic();
	    	        this.drawModalRectWithCustomSizedTexture(this.xPosition, this.yPosition+1, 0, 0, 200/8, 185/8, 200/8, 185/8);

	            }
	            
	            if(this.width == this.maxWidth) {
	            	int btnX = (int)exitOffset;
	            	int btnY = this.yPosition + 3;
	            	int btnW = 13;
	            	int btnH = 13;
	            	boolean hovered = mouseX >= btnX && mouseY >= btnY && mouseX < btnX + btnW && mouseY < btnY + btnH;
		            mc.getTextureManager().bindTexture(hovered ? exitHover : exit);
		            GlStateManager.enableColorLogic();
		            this.drawModalRectWithCustomSizedTexture((int)exitOffset, this.yPosition+3, 0, 0, 13, 13, 13, 13);
	    	        GlStateManager.disableColorLogic();
	            }
	            
	            GlStateManager.enableAlpha();
	            GlStateManager.enableBlend();
	            percentage *= (254/2);
	            percentage += (254/2);
	            if(percentage < (256/2))
	            	percentage = 5;
	            else if(percentage > 255)
	            	percentage=255;
	            mc.fontRendererObj.drawString(mc.session.getUsername(), this.xPosition+24, this.yPosition+6, new Color(255, 255, 255, (int)percentage).getRGB());

            }
        }
        
    }
    
    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
    	int btnX = this.xPosition + 90;
    	int btnY = this.yPosition + 3;
    	int btnW = 13;
    	int btnH = 13;
    	boolean hovered = mouseX >= btnX && mouseY >= btnY && mouseX < btnX + btnW && mouseY < btnY + btnH;
    	return this.width == this.maxWidth && hovered;
    }
    
    
    private void drawTransparentBtn(int x, int y, int w, int h, boolean hovered) {
    	GlStateManager.pushMatrix();
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
    	int leftWidth = w-(w/2);
    	this.drawTexturedModalRect(x, y, 0, hovered ? 40 : 0, (w/2), h);
    	this.drawTexturedModalRect(x + (int)(w/2), y, 200 - leftWidth, hovered ? 40 : 0, w/2, h);
    	GlStateManager.popMatrix();
    }
}
