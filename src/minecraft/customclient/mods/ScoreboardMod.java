package customclient.mods;

import customclient.Main;
import customclient.Mod;
import customclient.RelativePosition;
import customclient.gui.settings.CustomizableBooleanValue;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.scoreboard.ScoreObjective;

public class ScoreboardMod extends Mod {
	
	@CustomizableBooleanValue(defaultValue=true, uiName = "mod.scoreboard.nums")
	public boolean showNums = true;
	
	public ScoreboardMod() {		
		ScaledResolution sr = new ScaledResolution(mc);
		this.hudWidth = 150;
		this.hudHeight = fr.FONT_HEIGHT * 16+2;
		position = new RelativePosition(-this.hudWidth, -sr.getScaledHeight()/2 - (hudHeight/2), RelativePosition.ModPosition.BOTTOM_RIGHT);
		
	}
	
	@Override
	public void OnUIRender() {
		this.hudWidth = 110;
		GuiIngame gui = mc.ingameGUI;
		this.hudHeight = fr.FONT_HEIGHT * 16; 
		
		gui.renderScoreboard(false);
	}
	
	@Override
	public void OnDummyUIRender() {
		this.hudWidth = 110;
		GuiIngame gui = mc.ingameGUI;
		this.hudHeight = fr.FONT_HEIGHT * 16+1;
		
		gui.renderScoreboard(true);
	}
	
	@Override
	public String getName() {
		return I18n.format("mod.scoreboard.name");
	}
	@Override
	public String getInternalName() {
		return "Scoreboard Mod";
	}
}
