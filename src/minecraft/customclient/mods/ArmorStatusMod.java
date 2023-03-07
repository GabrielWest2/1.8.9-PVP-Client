package customclient.mods;

import java.awt.Color;

import customclient.Mod;
import customclient.RelativePosition;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ArmorStatusMod extends Mod {
	
	public ArmorStatusMod() {
		this.hudWidth = 50;
		this.hudHeight = 80;
		position = new RelativePosition(-this.hudWidth, -this.hudHeight, RelativePosition.ModPosition.BOTTOM_RIGHT);
	}

	public void OnUIRender() {
		int hudX = position.calculateX();
		int hudY = position.calculateY();
		GlStateManager.pushMatrix();
		for(int i = 0; i < 4; i++) {
			int j = 60-i * 15;
			ItemStack item = mc.thePlayer.getInventory()[i];
			
			if(item != null) {
				item.setItemDamage(0);
				String dam = item.getMaxDamage()-item.getItemDamage()+"";
				fr.drawStringWithShadow(dam, hudX - fr.getStringWidth(dam) + 22, hudY + j - 10, new Color(255, 255, 255).getRGB());
				drawItem(item, hudX+25, hudY+j-15);
			}
		}
		if(mc.thePlayer.getCurrentEquippedItem() != null) {
			ItemStack item = mc.thePlayer.getCurrentEquippedItem().copy();
			String dam = item.getMaxDamage()-item.getItemDamage()+"";
			item.setItemDamage(0);
			if(item.getMaxDamage() > 0)
				fr.drawStringWithShadow(mc.thePlayer.getCurrentEquippedItem().getMaxDamage()-mc.thePlayer.getCurrentEquippedItem().getItemDamage()+"", hudX - fr.getStringWidth(dam) + 22, hudY+75 - 7, new Color(255, 255, 255).getRGB());
			drawItem(item, hudX+25, hudY+75-12);
		}
		
		GlStateManager.popMatrix();
	}
	
	@Override
	public void OnDummyUIRender() {
		int hudX = position.calculateX();
		int hudY = position.calculateY();
		ItemStack[] items = new ItemStack[] {new ItemStack(Items.chainmail_boots), new ItemStack(Items.chainmail_leggings), new ItemStack(Items.chainmail_chestplate), new ItemStack(Items.chainmail_helmet), new ItemStack(Items.iron_sword)};
		GlStateManager.pushMatrix();
		for(int i = 0; i < 4; i++) {
			int j = 60-i * 15;

			String dam = "999";
			fr.drawStringWithShadow(dam, hudX - fr.getStringWidth(dam) + 22, hudY + j - 10, new Color(255, 255, 255).getRGB());
			drawItem(items[i], hudX+25, hudY+j-15);

		}
		drawItem(items[4], hudX+25, hudY+75-12);
		
		GlStateManager.popMatrix();
	}
	
	private void drawItem(ItemStack item, int x, int y)
    {
		RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(item, x, y);
    }
	
	@Override
	public String getName() {
		return I18n.format("mod.armorstatus.name");
	}
	@Override
	public String getInternalName() {
		return "Armor Status Mod";
	}
}
