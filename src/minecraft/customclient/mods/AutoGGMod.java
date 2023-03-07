package customclient.mods;

import customclient.Mod;
import customclient.gui.settings.CustomizableBooleanValue;
import net.minecraft.client.resources.I18n;

public class AutoGGMod extends Mod {
	@CustomizableBooleanValue(defaultValue = true, uiName = "mod.autoGG.ggOnWin")
	public boolean ggOnWin = true;
	@CustomizableBooleanValue(defaultValue = true, uiName = "mod.autoGG.ggOnLoss")
	public boolean ggOnLoss = true;
	
	@Override
	public String getName() {
		return I18n.format("mod.autogg.name");
	}
	
	@Override
	public String getInternalName() {
		return "AutoGG Mod";
	}
}
