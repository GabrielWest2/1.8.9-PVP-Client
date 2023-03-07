package customclient.gui.layout;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import customclient.KeyCode;
import customclient.Main;
import customclient.Mod;
import customclient.RelativePosition;
import customclient.RelativePosition.ModPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;

public class GuiScreenModLayout extends GuiScreen {


	private boolean movingElement = false;
	private Mod selectedElement = null;
	
	private int lineX = -1;
	private int lineY = -1;
	
	/**
	 * Fired when a key is typed (except F11 which toggles full screen). This is the
	 * equivalent of KeyListener.keyTyped(KeyEvent e). Args : character (character
	 * on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == KeyCode.KEY_ESCAPE) {
			mc.displayGuiScreen(null);
		}
	}
	
	/**
     * Called when a mouse button is released.  Args : mouseX, mouseY, releaseButton
     */
	protected void mouseReleased(int mouseX, int mouseY, int state)
    {
		for (Mod m : Main.INSTANCE.getMods()) {
			m.wasPressed = false;			
		}
		movingElement = false;
		selectedElement = null;
		lineX = -1;
		lineY = -1;
    }

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		ScaledResolution sr= new ScaledResolution(mc); 
		boolean isLeftClickPressed = clickedMouseButton == 0;
		ModPosition pos = calculateMouseRegion(true, mouseX, mouseY, sr);
		for (Mod m : Main.INSTANCE.getMods()) {
			if (m.isHovered(mouseX, mouseY) || (isLeftClickPressed && m.wasPressed)) {
				if (isLeftClickPressed && !m.wasPressed) {
					m.dragRelativeX = mouseX - m.position.calculateX();
					m.dragRelativeY = mouseY - m.position.calculateY();
					m.wasPressed = true;
					movingElement = true;
					selectedElement = m;
				} else if (isLeftClickPressed && m.wasPressed) {
					
					List<Integer> horizontalSnaps = getHorizontalSnaps(m, sr);
					List<Integer> verticalSnaps = getVerticalSnaps(m, sr);
					
					int leftX = mouseX-m.dragRelativeX;
					int rightX = leftX + m.hudWidth;
					int centerX = leftX + m.hudWidth/2;
					
					int topY = mouseY-m.dragRelativeY;
					int bottomY = topY + m.hudHeight;
					int centerY = topY + m.hudHeight/2;
					lineX = -1;
					lineY = -1;
					int tolerance = 2;
					
					int moveToX = leftX;
					int moveToY = topY;
					
					
					//Horizontal Snapping
					for(int snapX : horizontalSnaps) {
						if(Math.abs(snapX - leftX) < tolerance) {
							moveToX = snapX;
							lineX = snapX;
							break;
						}
						
						if(Math.abs(rightX - snapX) < tolerance) {
							moveToX = snapX - m.hudWidth;
							lineX = snapX;
							break;
						}
						
						if(Math.abs(centerX - snapX) < tolerance) {
							moveToX = snapX - m.hudWidth / 2;
							lineX = snapX;
							break;
						}
					}
					
					
					//Vertical Snapping
					for(int snapY : verticalSnaps) {
						if(Math.abs(topY - snapY) < tolerance) {
							moveToY = snapY;
							lineY = snapY;
							break;
						}
						
						if(Math.abs(bottomY - snapY) < tolerance) {
							moveToY = snapY - m.hudHeight;
							lineY = snapY;
							break;
						}
						
						if(Math.abs(centerY - snapY) < tolerance) {
							moveToY = snapY - m.hudHeight/2;
							lineY = snapY;
							break;
						}
					}
					
					m.position.setPositionRelativeToScreen(moveToX, moveToY, pos);
					
					drawAnchorDebug(sr, m.position);
				}
			}
		}
	}
	
	
	public void drawAnchorDebug(ScaledResolution sr, RelativePosition pos) {
		String s1 = "Anchor: " + pos.getAnchor().toString();
		String s2 = "Relative: (" + pos.getRelativeX() + ", "+pos.getRelativeY()+")";
		String s3 = "Absolute: (" + pos.calculateX() + ", "+pos.calculateY()+")";
		
		fontRendererObj.drawString(s1, sr.getScaledWidth() - fontRendererObj.getStringWidth(s1)- 5, 5, Color.white.getRGB());
		fontRendererObj.drawString(s2, sr.getScaledWidth() - fontRendererObj.getStringWidth(s2)- 5, 5 + fontRendererObj.FONT_HEIGHT, Color.white.getRGB());
		fontRendererObj.drawString(s3, sr.getScaledWidth() - fontRendererObj.getStringWidth(s3)- 5, 5 + fontRendererObj.FONT_HEIGHT*2, Color.white.getRGB());
	}
	
	/**
	 * Gets the X values that UI elements can snap to
	 */
	private List<Integer> getHorizontalSnaps(Mod draggingMod, ScaledResolution sr){
		List<Integer> snaps = new ArrayList();
		
		//Left of screen
		snaps.add(0);
		//Right of screen
		snaps.add(sr.getScaledWidth());
		//Center of screen
		snaps.add(sr.getScaledWidth()/2);
		
		for(Mod mod : Main.INSTANCE.getMods()) {
			if(mod == draggingMod || !mod.isEnabled())
				continue;
			//Left Snap
			snaps.add(mod.position.calculateX());
			//Right Snap
			snaps.add(mod.position.calculateX() + mod.hudWidth);
			//Center Snap
			snaps.add(mod.position.calculateX() + mod.hudWidth/2);
		}
		return snaps;
	}
	
	/**
	 * Gets the Y values that UI elements can snap to
	 */
	private List<Integer> getVerticalSnaps(Mod draggingMod, ScaledResolution sr){
		List<Integer> snaps = new ArrayList();
		
		//Top of screen
		snaps.add(0);
		//Bottom of screen
		snaps.add(sr.getScaledHeight());
		//Center of screen
		snaps.add(sr.getScaledHeight()/2);
		
		for(Mod mod : Main.INSTANCE.getMods()) {
			if(mod == draggingMod || !mod.isEnabled())
				continue;
			//Top Snap
			snaps.add(mod.position.calculateY());
			//Bottom Snap
			snaps.add(mod.position.calculateY() + mod.hudHeight);
			//Center Snap
			snaps.add(mod.position.calculateY() + mod.hudHeight/2);
		}
		
		return snaps;
	}
	

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		ScaledResolution sr= new ScaledResolution(mc);
		if(movingElement)
			drawAnchorDebug(sr, selectedElement.position);
		Main.INSTANCE.renderMods(true, mouseX, mouseY);
		Gui.drawVerticalLine(lineX, 0, sr.getScaledHeight(), lineX == sr.getScaledWidth()/2 ? Color.cyan.getRGB() : Color.green.getRGB());
		Gui.drawHorizontalLine(0, sr.getScaledWidth(), lineY, lineY == sr.getScaledHeight()/2 ? Color.cyan.getRGB() : Color.green.getRGB());
	}
	
	private ModPosition calculateMouseRegion(boolean render, int mouseX, int mouseY, ScaledResolution sr) {
		int hover = new Color(0, 0, 0, 100).getRGB();
		
		RelativePosition.ModPosition anchor;
		if(mouseX > 0 && mouseY > 0 && mouseX < sr.getScaledWidth()/3 && mouseY < sr.getScaledHeight()/2) {
			anchor = RelativePosition.ModPosition.TOP_LEFT;
			if(render)
				Gui.drawRect(0, 0, sr.getScaledWidth()/3, sr.getScaledHeight()/2, hover);
			return anchor;
		}else if(mouseX > 0 && mouseY > sr.getScaledHeight()/2 && mouseX < sr.getScaledWidth()/3 && mouseY < sr.getScaledHeight()) {
			anchor = RelativePosition.ModPosition.BOTTOM_LEFT;
			if(render)
				Gui.drawRect(0, sr.getScaledHeight()/2, sr.getScaledWidth()/3, sr.getScaledHeight(),  hover);
			return anchor;
		}else if(mouseX > sr.getScaledWidth()/3 && mouseX < sr.getScaledWidth()/3 * 2 && mouseY > 0 &&  mouseY < sr.getScaledHeight()) {
			anchor = RelativePosition.ModPosition.CENTER;
			if(render)
				Gui.drawRect(sr.getScaledWidth()/3, 0, sr.getScaledWidth()/3 * 2, sr.getScaledHeight(),  hover);
			return anchor;
		}else if(mouseX > sr.getScaledWidth()/3 * 2 && mouseY > 0 && mouseX < sr.getScaledWidth() && mouseY < sr.getScaledHeight()/2) {
			anchor = RelativePosition.ModPosition.TOP_RIGHT;
			if(render)
				Gui.drawRect(sr.getScaledWidth()/3 * 2, 0, sr.getScaledWidth(), sr.getScaledHeight()/2,  hover);
			return anchor;
		}else if(mouseX > sr.getScaledWidth()/3 * 2 && mouseY > sr.getScaledHeight()/2 && mouseX < sr.getScaledWidth() && mouseY < sr.getScaledHeight()) {
			anchor = RelativePosition.ModPosition.BOTTOM_RIGHT;
			if(render)
				Gui.drawRect(sr.getScaledWidth()/3 * 2, sr.getScaledHeight()/2, sr.getScaledWidth(), sr.getScaledHeight(),  hover);
			return anchor;
		}
		return RelativePosition.ModPosition.TOP_LEFT;
	}
}
