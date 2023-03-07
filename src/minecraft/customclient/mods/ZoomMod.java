package customclient.mods;

import customclient.Mod;
import net.minecraft.client.resources.I18n;

public class ZoomMod extends Mod{
	@Override
	public String getName() {
		return I18n.format("mod.zoom.name");
	}
	@Override
	public String getInternalName() {
		return "Zoom Mod";
	}
}
