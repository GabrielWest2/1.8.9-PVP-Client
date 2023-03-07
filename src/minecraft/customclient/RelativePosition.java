package customclient;

import com.google.gson.annotations.Expose;

import customclient.file.Exclude;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class RelativePosition {
	public enum ModPosition {
		TOP_LEFT, BOTTOM_LEFT, CENTER, TOP_RIGHT, BOTTOM_RIGHT;
		public int getScreenX(ScaledResolution sr) {
			switch(this) {
				case TOP_LEFT:
					return 0;
				case BOTTOM_LEFT:
					return 0;
				case CENTER:
					return sr.getScaledWidth()/2;
				case TOP_RIGHT:
					return sr.getScaledWidth();
				case BOTTOM_RIGHT:
					return sr.getScaledWidth();
			}
			return 0;
		}
		public int getScreenY(ScaledResolution sr) {
			switch(this) {
				case TOP_LEFT:
					return 0;
				case BOTTOM_LEFT:
					return sr.getScaledHeight();
				case CENTER:
					return 0;
				case TOP_RIGHT:
					return 0;
				case BOTTOM_RIGHT:
					return sr.getScaledHeight();
			}
			return 0;
		}
	}
	

	private int x, y;

	private ModPosition anchor;
	
	@Exclude
	private ScaledResolution sr;
	
	public RelativePosition(int defaultX, int defaultY, ModPosition defaultAnchor) {
		this.sr = new ScaledResolution(Minecraft.getMinecraft());
		this.x = defaultX;
		this.y = defaultY;
		this.anchor = defaultAnchor;
	}
	
	public void setPositionRelativeToAnchor(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPositionRelativeToScreen(int x, int y, ModPosition anchor) {
		this.anchor = anchor;
		this.x = x - anchor.getScreenX(sr);
		this.y = y - anchor.getScreenY(sr);
	}
	
	public void setPositionRelativeToScreen(int x, int y) {
		this.x = x - anchor.getScreenX(sr);
		this.y = y - anchor.getScreenY(sr);
	}
	
	public int calculateX() {
		this.sr = new ScaledResolution(Minecraft.getMinecraft());
		return anchor.getScreenX(sr) + x;
	}
	
	public int calculateY() {
		this.sr = new ScaledResolution(Minecraft.getMinecraft());
		return anchor.getScreenY(sr) + y;
	}

	public int getRelativeX() {
		return this.x;
	}
	
	public int getRelativeY() {
		return this.y;
	}

	public ModPosition getAnchor() {
		return this.anchor;
	}
}
