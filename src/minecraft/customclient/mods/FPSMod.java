package customclient.mods;

import java.awt.Color;

import customclient.Mod;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;

public class FPSMod extends Mod {

	public FPSMod() {
		this.hudWidth = 79;
		this.hudHeight = 25;
	}
	
	@Override
	public void OnUIRender() {
		int hudX = position.calculateX();
		int hudY = position.calculateY();
		Gui.drawRect(hudX, hudY, hudX + hudWidth, hudY + hudHeight, new Color(0, 0, 0, 150).getRGB());
		int oldFontHeight = fr.FONT_HEIGHT;
		String s = mc.getDebugFPS() + " FPS";
		fr.drawString(s, hudX + hudWidth/2  - fr.getStringWidth(s)/2, hudY + hudHeight/2 - fr.FONT_HEIGHT/2, Color.white.getRGB());

	}
	
	@Override
	public String getName() {
		return I18n.format("mod.fps.name");
	}
	
	@Override
	public String getInternalName() {
		return "FPS Mod";
	}
}
