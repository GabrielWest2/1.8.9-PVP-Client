package customclient.mods;

import customclient.Mod;
import customclient.RelativePosition;
import net.minecraft.client.resources.I18n;

public class BossBarMod extends Mod {
	public BossBarMod() {		
		this.hudWidth = 182;
		this.hudHeight = 17;
		position = new RelativePosition(-this.hudWidth/2, 14, RelativePosition.ModPosition.CENTER);
		
	}
	
	@Override
	public void OnUIRender() {
		mc.ingameGUI.renderBossHealth(false);
	}
	
	@Override
	public void OnDummyUIRender() {
		mc.ingameGUI.renderBossHealth(true);
	}
	
	@Override
	public String getName() {
		return I18n.format("mod.bossbar.name");
	}
	@Override
	public String getInternalName() {
		return "Boss Bar Mod";
	}
}
