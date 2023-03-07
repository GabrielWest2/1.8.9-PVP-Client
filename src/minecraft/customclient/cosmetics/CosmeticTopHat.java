package customclient.cosmetics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class CosmeticTopHat extends CosmeticBase
{
    private final RenderPlayer playerRenderer;
    private final ResourceLocation res = new ResourceLocation("customclient/cosmetics/hats/hat.png");
    private ModelTopHat topHatModel;
    public CosmeticTopHat(RenderPlayer playerRendererIn)
    {
    	topHatModel = new ModelTopHat(playerRendererIn);
        this.playerRenderer = playerRendererIn;
    }

    @Override
    public void render(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        	GlStateManager.pushMatrix();
            this.playerRenderer.bindTexture(res);
            if(entitylivingbaseIn.isSneaking()) {
            	GlStateManager.translate(0D, 0.225D, 0D);
            }
            GlStateManager.color(1.0F, 0.0F, 0.0F);
            topHatModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.popMatrix();

    }

	private class ModelTopHat extends CosmeticModelBase {

		private ModelRenderer rim;
		private ModelRenderer top;
		
		public ModelTopHat(RenderPlayer rplayer) {
			super(rplayer);
			rim = new ModelRenderer(player, 0, 0);
			rim.addBox(-5.5f, -9F, -5.5F, 11, 2, 11);
			top = new ModelRenderer(player, 0, 13);
			top.addBox(-3.5f, -17F, -3.5F, 7, 8, 7);

		}

		@Override
		public void render(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount,
				float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
			rim.rotateAngleX = player.bipedHead.rotateAngleX;
			rim.rotateAngleY = player.bipedHead.rotateAngleY;
			rim.rotationPointX = 0.0F;
			rim.rotationPointY = 0.0F;
			rim.render(scale);
			
			top.rotateAngleX = player.bipedHead.rotateAngleX;
			top.rotateAngleY = player.bipedHead.rotateAngleY;
			top.rotationPointX = 0.0F;
			top.rotationPointY = 0.0F;
			top.render(scale);
			
			
		}
		
	}
}
