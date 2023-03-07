package customclient.mods;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import customclient.Mod;
import customclient.RelativePosition;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;

public class CPSMod extends Mod {
	
	private List<Long> clicks = new ArrayList<Long>();
	private boolean wasPressed = false;
	private long lastPressed;
	
	public CPSMod() {
		this.hudWidth = 79;
		this.hudHeight = 25;
		position = new RelativePosition(10, 100 + 79 + 2, RelativePosition.ModPosition.TOP_LEFT);
	}
	
	@Override
	public void OnUIRender() {
		
		final boolean pressed = Mouse.isButtonDown(0);
		if(pressed != wasPressed) {
			lastPressed = System.currentTimeMillis();
			this.wasPressed = pressed;
			if(pressed) {
				this.clicks.add(lastPressed);
			}
		}
		
		int hudX = position.calculateX();
		int hudY = position.calculateY();
		Gui.drawRect(hudX, hudY, hudX + hudWidth, hudY + hudHeight, new Color(0, 0, 0, 150).getRGB());
		
		String s = getCPS() + " CPS";
		fr.drawString(s, hudX + hudWidth/2  - fr.getStringWidth(s)/2, hudY + hudHeight/2 - fr.FONT_HEIGHT/2, Color.white.getRGB());

	}
	
	private int getCPS() {
		final long time  = System.currentTimeMillis();
		this.clicks.removeIf(t -> t + 1000 < time);
		return clicks.size();
	}
	
	@Override
	public String getName() {
		return I18n.format("mod.cps.name");
	}
	@Override
	public String getInternalName() {
		return "CPS Mod";
	}
}
