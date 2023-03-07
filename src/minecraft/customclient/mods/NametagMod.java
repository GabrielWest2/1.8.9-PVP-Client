package customclient.mods;

import customclient.Mod;
import net.minecraft.client.resources.I18n;

public class NametagMod extends Mod{
	
	@Override
	public String getName() {
		return I18n.format("mod.nametag.name");
	}
	@Override
	public String getInternalName() {
		return "Nametag Mod";
	}
}
