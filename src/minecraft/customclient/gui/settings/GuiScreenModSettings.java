package customclient.gui.settings;

import java.io.IOException;

import customclient.Main;
import customclient.Mod;
import customclient.gui.settings.GuiModSettingsList.ValueEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class GuiScreenModSettings extends GuiScreen
{

    /**
     * A reference to the screen object that created this. Used for navigating between screens.
     */
    private GuiScreen parentScreen;
    protected String screenTitle = "Mod Settings";
    
    private GuiModSettingsList modList;


    /** The ID of the button that has been pressed. */

    public long time;
    private Mod mod;
    private GuiButton buttonResetAll;

    public GuiScreenModSettings(GuiScreen screen, Mod mod)
    {
    	this.mod = mod;
        this.parentScreen = screen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
    	screenTitle = mod.getName() + " Settings";
    	modList = new GuiModSettingsList(this, this.mc, this.mod);
        this.buttonList.add(new GuiButton(200, this.width / 2 - 155, this.height - 29, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(this.buttonResetAll = new GuiButton(201, this.width / 2 - 155 + 160, this.height - 29, 150, 20, "Reset All"));
       
    }


    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
    	if (button.id == 200)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }else if(button.id == 201) {
        	for(GuiModSettingsList.IGuiListEntry entry : modList.getAllEntries()) {
            	((ValueEntry)entry).resetToDefault();
            }
        }
    }

    
    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        this.modList.handleMouseInput();
    }

    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
    	this.modList.mouseClicked(mouseX, mouseY, mouseButton);
    	super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        modList.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 8, 16777215);
        
        boolean flag = false;
        for(GuiModSettingsList.IGuiListEntry entry : modList.getAllEntries()) {
        	if(((ValueEntry)entry).hasBeenChanged()) {
        		flag = true;
        		break;
        	}
        }
       
        this.buttonResetAll.enabled = flag;
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
