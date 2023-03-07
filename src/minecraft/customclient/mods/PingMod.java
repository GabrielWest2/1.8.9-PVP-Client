package customclient.mods;

import java.awt.Color;

import customclient.Main;
import customclient.Mod;
import customclient.gui.settings.CustomizableBooleanValue;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class PingMod extends Mod {
	@CustomizableBooleanValue(defaultValue = true, uiName = "mod.ping.showHud")
	public boolean showHud = true;
	@CustomizableBooleanValue(defaultValue = true, uiName = "mod.ping.showBars")
	public boolean showBars = true;
	
	@CustomizableBooleanValue(defaultValue = false, uiName = "mod.ping.showTab")
	public boolean showInTab = false;
	
    public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
	
    
    @Override
	public void OnDummyUIRender() {
    	this.hudWidth = 79;
		this.hudHeight = 25;
		int x = position.calculateX();
		int y = position.calculateY();
		
		
		String s = "42 ms";
		int textLeft = x+this.hudWidth/2-fr.getStringWidth(s)/2+6;
		if(!showBars)
			textLeft -= 6;
		Gui.drawRect(x, y, x+this.hudWidth, y+this.hudHeight, new Color(0, 0, 0, 150).getRGB());
		fr.drawString(s, textLeft, y+hudHeight/2-fr.FONT_HEIGHT/2, -1);
		
		this.mc.getTextureManager().bindTexture(icons);
        int j = 3;

        if(showBars)
        	drawTexturedModalRect(textLeft-14, y + this.hudHeight/2-4, 0, 176 + j * 8, 10, 8);

		
    }
    
	@Override
	public void OnUIRender() {
		//Hide in singleplayer
		if(mc.isSingleplayer()) {
			this.hudHeight = 0;
			this.hudWidth = 0;
			return;
		}
		
		this.hudWidth = 79;
		this.hudHeight = 25;
		int x = position.calculateX();
		int y = position.calculateY();
		
		NetworkPlayerInfo inf = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getGameProfile().getId());
		if(inf == null)
			return;
		
		String s = inf.getResponseTime() + " ms";
		int textLeft = x+this.hudWidth/2-fr.getStringWidth(s)/2+6;
		if(!showBars)
			textLeft -= 6;
		Gui.drawRect(x, y, x+this.hudWidth, y+this.hudHeight, new Color(0, 0, 0, 150).getRGB());
		fr.drawString(s, textLeft, y+hudHeight/2-fr.FONT_HEIGHT/2, -1);
		
		this.mc.getTextureManager().bindTexture(icons);
        int j = 0;

        if (inf.getResponseTime() < 0)
        {
            j = 5;
        }
        else if (inf.getResponseTime() < 150)
        {
            j = 0;
        }
        else if (inf.getResponseTime() < 300)
        {
            j = 1;
        }
        else if (inf.getResponseTime() < 600)
        {
            j = 2;
        }
        else if (inf.getResponseTime() < 1000)
        {
            j = 3;
        }
        else
        {
            j = 4;
        }

        if(showBars)
        	drawTexturedModalRect(textLeft-14, y + this.hudHeight/2-4, 0, 176 + j * 8, 10, 8);

		
	}
	
    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b((double)(x + 0), (double)(y + height), 100).func_181673_a((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)(y + height), 100).func_181673_a((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)(y + 0), 100).func_181673_a((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + 0), (double)(y + 0), 100).func_181673_a((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1)).func_181675_d();
        tessellator.draw();
    }
	
	
	@Override
	public String getName() {
		return I18n.format("mod.ping.name");
	}
	
	@Override
	public String getInternalName() {
		return "Ping Mod";
	}
}
