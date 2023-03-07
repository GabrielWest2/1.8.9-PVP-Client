package customclient.mods;

import org.lwjgl.opengl.Display;

import customclient.Mod;
import customclient.gui.settings.CustomizableBooleanValue;
import net.minecraft.client.resources.I18n;

public class PerspectiveMod extends Mod {

	@CustomizableBooleanValue(defaultValue = false, uiName = "mod.perspective.toggle")
	public boolean toggle = false;
	
	
	public boolean viewToggled = false;
	public int previousView;
	public float cameraYaw, cameraPitch;
	
	private boolean wasPressed = false;
	@Override
	public void OnUIRender() {
		boolean keyDown = mc.gameSettings.keyPerspectiveModCC.isKeyDown();
		if(toggle) {
			if(keyDown && !wasPressed) {
				viewToggled = !viewToggled;
				cameraYaw = mc.thePlayer.rotationYaw;
				cameraPitch = mc.thePlayer.rotationPitch;
				if(viewToggled) {
					previousView = mc.gameSettings.thirdPersonView;
					mc.gameSettings.thirdPersonView = 1;
				}else {
					mc.gameSettings.thirdPersonView = previousView;
				}
			}
			wasPressed = keyDown;
		}else {
			viewToggled = keyDown;
			
			if(keyDown && !wasPressed) {
				cameraYaw = mc.thePlayer.rotationYaw;
				cameraPitch = mc.thePlayer.rotationPitch;
				previousView = mc.gameSettings.thirdPersonView;
				mc.gameSettings.thirdPersonView = 1;
			}
			
			if(!keyDown && wasPressed) {
				mc.gameSettings.thirdPersonView = previousView;
			}
			
			wasPressed = keyDown;
		}
	}
	
	
	public float getYaw() {
		return this.viewToggled ? cameraYaw : mc.thePlayer.rotationYaw;
	}
	
	public float getPitch() {
		return this.viewToggled ? cameraPitch : mc.thePlayer.rotationPitch;
	}
	
	public boolean overrideMouse() {
		if(mc.inGameHasFocus && Display.isActive()) {
			if(!viewToggled) {
				return true;
			}
			mc.mouseHelper.mouseXYChange();
			float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
			float f2 = f1 * f1 * f1 * 8.0F;
			float f3 = (float) mc.mouseHelper.deltaX * f2;
			float f4 = (float) mc.mouseHelper.deltaY * f2;
			
			cameraYaw += f3 * 0.15F;
			cameraPitch += f4 * 0.15F;
			
			if (cameraPitch > 90) cameraPitch = 90;
			if (cameraPitch < -90) cameraPitch = -90;
		}
		return false;
	}
	
	
	@Override
	public String getName() {
		return I18n.format("mod.perspective.name");
	}
	
	@Override
	public String getInternalName() {
		return "Perspective Mod";
	}
}
