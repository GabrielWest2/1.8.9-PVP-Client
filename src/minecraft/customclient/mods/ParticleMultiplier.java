package customclient.mods;

import customclient.Mod;
import customclient.gui.settings.*;
import net.minecraft.client.resources.I18n;

public class ParticleMultiplier extends Mod{
	
	@CustomizableIntegerValue(defaultValue=2, max = 5, min = 1, uiName = "mod.particlemultiplier.multiplier")
	public int multiplier = 2;
	@CustomizableDoubleValue(defaultValue=0.5, max = 5, min = 0, uiName = "mod.particlemultiplier.randomness")
	public double randomness = 0.5;
	@CustomizableBooleanValue(defaultValue=false, uiName = "mod.particlemultiplier.showSharp")
	public boolean sharpAlways = false;

	
	
	@Override
	public String getName() {
		return I18n.format("mod.particlemultiplier.name");
	}
	@Override
	public String getInternalName() {
		return "Particle Multiplier";
	}

}
