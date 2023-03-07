package customclient.mods;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import customclient.Mod;
import customclient.RelativePosition;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;

public class TimeChangerMod extends Mod {
	public long time = 0;
	public int selection = 0;
	
	Map<String, Integer> timeSelections = new HashMap<String,Integer>(){{
		put("Dawn", 0);
        put("Day", 1000);
        put("Midday", 6000);
        put("Dusk", 12000);
        put("Night", 15000);
        put("Midnight", 18000);
    }};

	public TimeChangerMod() {
		this.hudWidth=fr.getStringWidth("[Time Changer(Enabled)]");
		this.hudHeight = fr.FONT_HEIGHT;
		this.position = new RelativePosition(-this.hudWidth/2, 4, RelativePosition.ModPosition.CENTER);
	}
	
	@Override
	public void OnUIRender() {
		
		if(mc.gameSettings.keyBindToggleTimeChangerCC.isPressed()) {
			selection ++;
			if(selection >= timeSelections.size())
				selection = 0;
		}
		String val = (String) timeSelections.keySet().toArray()[selection];
		time = timeSelections.get(val);
		String s = "[Time Changer(" + val + ")]";
		
		this.hudWidth=fr.getStringWidth(s);
		
		int hudX = position.calculateX();
		int hudY = position.calculateY();
		fr.drawStringWithShadow(s, hudX , hudY, new Color(255, 255, 255).getRGB());
		
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public long getTime() {
		return this.time;
	}
	
	@Override
	public String getName() {
		return I18n.format("mod.timechanger.name");
	}
	@Override
	public String getInternalName() {
		return "Time Changer Mod";
	}
}
