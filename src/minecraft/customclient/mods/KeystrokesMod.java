package customclient.mods;

import java.awt.Color;

import customclient.Mod;
import customclient.RelativePosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KeystrokesMod extends Mod {

	public KeystrokesMod() {
		this.hudWidth=79;
		this.hudHeight = 79;
		position = new RelativePosition(10, 100, RelativePosition.ModPosition.TOP_LEFT);
	}

	public void OnUIRender() {
		
		mc.mcProfiler.startSection("keystrokes mod");
		GlStateManager.pushMatrix();
		int opacity = 150;
		int color = new Color(255, 255, 255, opacity).getRGB();
		int hudX = position.calculateX();
		int hudY = position.calculateY();
		
		if(mc.gameSettings.keyBindForward.isKeyDown()) {
			Gui.drawRect(27+hudX, hudY, hudX + 27+25, hudY+ 25, color);
			fr.drawString("W", hudX +28+ (25 / 2) - (fr.getStringWidth("W") / 2), hudY + ((25 / 2) - fr.FONT_HEIGHT / 2),
					Color.black.getRGB());
		}else {
			Gui.drawRect(27+hudX, hudY, hudX +27+ 25, hudY+ 25, new Color(0, 0, 0, opacity).getRGB());
			fr.drawString("W", hudX +28+ (25 / 2) - (fr.getStringWidth("W") / 2), hudY + ((25 / 2) - fr.FONT_HEIGHT / 2),
					new Color(255, 255, 255).getRGB());
		}
		
		if(mc.gameSettings.keyBindLeft.isKeyDown()) {
			Gui.drawRect(hudX, hudY + 27, hudX+25, hudY+ 25 + 27, color);
			fr.drawString("A", hudX +1+ (25 / 2) - (fr.getStringWidth("A") / 2), hudY  + 27+ ((25 / 2) - fr.FONT_HEIGHT / 2),
					Color.black.getRGB());
		}else {
			Gui.drawRect(hudX, hudY + 27, hudX+ 25, hudY+ 25 + 27, new Color(0, 0, 0, opacity).getRGB());
			fr.drawString("A", hudX +1+ (25 / 2) - (fr.getStringWidth("A") / 2), hudY + 27 + ((25 / 2) - fr.FONT_HEIGHT / 2),
					new Color(255, 255, 255).getRGB());
		}
		
		if(mc.gameSettings.keyBindBack.isKeyDown()) {
			Gui.drawRect(hudX+ 27, hudY + 27, hudX+25+ 27, hudY+ 25 + 27, color);
			fr.drawString("S", hudX + 27+1+ (25 / 2) - (fr.getStringWidth("S") / 2), hudY  + 27+ ((25 / 2) - fr.FONT_HEIGHT / 2),
					Color.black.getRGB());
		}else {
			Gui.drawRect(hudX+ 27, hudY + 27, hudX+ 25+ 27, hudY+ 25 + 27, new Color(0, 0, 0, opacity).getRGB());
			fr.drawString("S", hudX+ 27 +1+ (25 / 2) - (fr.getStringWidth("S") / 2), hudY + 27 + ((25 / 2) - fr.FONT_HEIGHT / 2),
					new Color(255, 255, 255).getRGB());
		}
		
		if(mc.gameSettings.keyBindRight.isKeyDown()) {
			Gui.drawRect(hudX+ 27+ 27, hudY + 27, hudX+25+ 27+ 27, hudY+ 25 + 27, color);
			fr.drawString("D", hudX + 27+ 27+1+ (25 / 2) - (fr.getStringWidth("D") / 2), hudY  + 27+ ((25 / 2) - fr.FONT_HEIGHT / 2),
					Color.black.getRGB());
		}else {
			Gui.drawRect(hudX+ 27+ 27, hudY + 27, hudX+ 25+ 27+ 27, hudY+ 25 + 27, new Color(0, 0, 0, opacity).getRGB());
			fr.drawString("D", hudX+ 27+ 27 +1+ (25 / 2) - (fr.getStringWidth("D") / 2), hudY + 27 + ((25 / 2) - fr.FONT_HEIGHT / 2),
					new Color(255, 255, 255).getRGB());
		}
		
		if(mc.gameSettings.keyBindAttack.isKeyDown()) {
			Gui.drawRect(hudX, hudY + 27+ 27, hudX+39, hudY+ 25 + 27+ 27, color);
			fr.drawString("LMB", hudX +1+8+ (25 / 2) - (fr.getStringWidth("LMB") / 2), hudY  + 27+ 27+ ((25 / 2) - fr.FONT_HEIGHT / 2),
					Color.black.getRGB());
		}else {
			Gui.drawRect(hudX, hudY + 27+ 27, hudX+ 39, hudY+ 25 + 27+ 27, new Color(0, 0, 0, opacity).getRGB());
			fr.drawString("LMB", hudX +1+8+ (25 / 2) - (fr.getStringWidth("LMB") / 2), hudY + 27+ 27 + ((25 / 2) - fr.FONT_HEIGHT / 2),
					new Color(255, 255, 255).getRGB());
		}
		
		if(mc.gameSettings.keyBindUseItem.isKeyDown()) {
			Gui.drawRect(hudX+41, hudY + 27+ 27, hudX+ 79, hudY+ 25 + 27+ 27, color);
			fr.drawString("RMB", hudX +1+8+ +40+(25 / 2) - (fr.getStringWidth("RMB") / 2), hudY  + 27+ 27+ ((25 / 2) - fr.FONT_HEIGHT / 2),
					Color.black.getRGB());
		}else {
			Gui.drawRect(hudX+41, hudY + 27+ 27, hudX+ 79, hudY+ 25 + 27+ 27, new Color(0, 0, 0, opacity).getRGB());
			fr.drawString("RMB", hudX +1+8+ +40+(25 / 2) - (fr.getStringWidth("RMB") / 2), hudY + 27+ 27 + ((25 / 2) - fr.FONT_HEIGHT / 2),
					new Color(255, 255, 255).getRGB());
		}

		GlStateManager.popMatrix();
		

		mc.mcProfiler.endSection();
	}
	
	@Override
	public String getName() {
		return I18n.format("mod.keystrokes.name");
	}
	@Override
	public String getInternalName() {
		return "Keystrokes Mod";
	}

	public void Disable() {
		super.Disable();
	}

}
