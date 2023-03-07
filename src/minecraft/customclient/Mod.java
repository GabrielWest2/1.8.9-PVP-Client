package customclient;

import java.awt.Color;
import java.lang.reflect.Field;

import com.google.gson.annotations.Expose;

import customclient.RelativePosition.ModPosition;
import customclient.file.Exclude;
import customclient.gui.settings.CustomizableBooleanValue;
import customclient.gui.settings.CustomizableDoubleValue;
import customclient.gui.settings.CustomizableFloatValue;
import customclient.gui.settings.CustomizableIntegerValue;
import customclient.gui.settings.GuiModSettingsList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class Mod {
	
	@Exclude
	public Minecraft mc = Minecraft.getMinecraft();
	@Exclude
	public FontRenderer fr = mc.fontRendererObj;
	

	public int hudWidth, hudHeight;

	public RelativePosition position = new RelativePosition(10, 10, ModPosition.TOP_LEFT);

	public boolean enabled = true;
	
	@Exclude
	public boolean wasPressed = false;
	
	@Exclude
	public int dragRelativeX, dragRelativeY;
	
	public void Enable() {	
		enabled = true;
	}
	
	public void OnUIRender() {}
	public void OnDummyUIRender() {
		this.OnUIRender();
	}
	public void RenderOutline(int mouseX, int mouseY) {
		int hudX = position.calculateX();
		int hudY = position.calculateY();
		int color =  isHovered(mouseX, mouseY) ? new Color(255, 100, 100, 100).getRGB() : new Color(100, 100, 100, 100).getRGB();
		Gui.drawRect(hudX, hudY, hudX + hudWidth, hudY + hudHeight, color);
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		int hudX = position.calculateX();
		int hudY = position.calculateY();
		return (mouseX > hudX && mouseX < hudX + hudWidth && mouseY > hudY && mouseY < hudY + hudHeight);
	}
	
	public void Disable(){	
		enabled = false;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void SetEnabled(boolean _enabled) {
		enabled = _enabled;
	}
	
	/**
	 * Get the localized name of the mod
	 * @return the name
	 */
	public String getName() {
		return "mod";
	}
	
	/**
	 * Get the unlocalized name of the mod
	 * @return the name
	 */
	public String getInternalName() {
		return "mod-int";
	}
	
	public boolean hasSettings() {
		for(Field f : this.getClass().getDeclaredFields()) {
			boolean intVal = f.isAnnotationPresent(CustomizableIntegerValue.class);
			boolean floatVal = f.isAnnotationPresent(CustomizableFloatValue.class);
			boolean doubleVal = f.isAnnotationPresent(CustomizableDoubleValue.class);
			boolean boolVal = f.isAnnotationPresent(CustomizableBooleanValue.class);
			if(intVal ||floatVal || doubleVal || boolVal) {
				return true;
			}
		}
		return false;
	}

}
