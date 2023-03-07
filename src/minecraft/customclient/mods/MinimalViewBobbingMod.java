package customclient.mods;

import customclient.Mod;
import net.minecraft.client.resources.I18n;

public class MinimalViewBobbingMod extends Mod {
	
	@Override
	public String getName() {
		return I18n.format("mod.minviewbob.name");
	}
	@Override
	public String getInternalName() {
		return "Minimal View Bobbing";
	}
}