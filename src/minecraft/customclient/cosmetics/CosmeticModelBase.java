package customclient.cosmetics;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;

public abstract class CosmeticModelBase extends ModelBase {
	protected final ModelBiped player;
	
	public CosmeticModelBase(RenderPlayer rplayer) {
		this.player = rplayer.getMainModel();
	}
	
    public abstract void render(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale);
}
