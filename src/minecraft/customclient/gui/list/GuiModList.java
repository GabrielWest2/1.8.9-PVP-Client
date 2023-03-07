package customclient.gui.list;

import customclient.Main;
import customclient.Mod;
import customclient.gui.settings.GuiButtonSettings;
import customclient.gui.settings.GuiScreenModSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;

public class GuiModList extends GuiListExtended{

	private final GuiListExtended.IGuiListEntry[] listEntries;
	private int maxListLabelWidth = 0;
	
	public GuiModList(GuiScreenModList settings, Minecraft mcIn) {
		super(mcIn, settings.width, settings.height, 27, settings.height - 32, 20);
		listEntries = new GuiListExtended.IGuiListEntry[getSize()];
		int i = 0;
		for(Mod mod : Main.INSTANCE.getMods()) {
			this.listEntries[i++] = new GuiModList.ModEntry(mod, settings);
			
            int j = mcIn.fontRendererObj.getStringWidth(mod.getName());

            if (j > this.maxListLabelWidth)
            {
                this.maxListLabelWidth = j;
            }
		}
	}

	@Override
	public IGuiListEntry getListEntry(int index) {
		return listEntries[index];
	}

	@Override
	protected int getSize() {
		return Main.INSTANCE.getMods().size();
	}
	
    protected int getScrollBarX()
    {
        return super.getScrollBarX() + 15;
    }

    /**
     * Gets the width of the list
     */
    public int getListWidth()
    {
        return super.getListWidth() + 32;
    }

	
	
	public class ModEntry implements GuiListExtended.IGuiListEntry
    {

        private final String keyDesc;
        private final GuiButton btnToggleEnabled;
        private final GuiButton btnSettings;
        private final Mod mod;
        private GuiScreen parent;
        private ModEntry(Mod mod, GuiScreen p)
        {
        	this.parent = p;
        	this.mod = mod;
            this.keyDesc = mod.getName();
            String s = mod.isEnabled() ? "§aEnabled" : "§cDisabled";
            this.btnToggleEnabled = new GuiButton(0, 0, 0, 75, 20, s);
            //this.btnSettings = new GuiButton(0, 0, 0, 50, 20, "Settings");
            this.btnSettings = new GuiButtonSettings(23, 0, 0);
            btnSettings.visible = mod.hasSettings();
        }

        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected)
        {
            GuiModList.this.mc.fontRendererObj.drawString(this.keyDesc, x + 90 - GuiModList.this.maxListLabelWidth, y + slotHeight / 2 - GuiModList.this.mc.fontRendererObj.FONT_HEIGHT / 2, 16777215);
            this.btnSettings.xPosition = x + 220;
            this.btnSettings.yPosition = y;
            this.btnToggleEnabled.xPosition = x + 130;
            this.btnToggleEnabled.yPosition = y;
            String s = mod.isEnabled() ? "§aEnabled" : "§cDisabled";
            this.btnToggleEnabled.displayString = s;
            this.btnToggleEnabled.drawButton(GuiModList.this.mc, mouseX, mouseY);
            this.btnSettings.drawButton(GuiModList.this.mc, mouseX, mouseY);
        }

        public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int p_148278_4_, int p_148278_5_, int p_148278_6_)
        {
            if (this.btnToggleEnabled.mousePressed(GuiModList.this.mc, mouseX, mouseY))
            {
            	mod.SetEnabled(!mod.isEnabled());
                return true;
            }
            else if (this.btnSettings.mousePressed(GuiModList.this.mc, mouseX, mouseY))
            {
            	mc.displayGuiScreen(new GuiScreenModSettings(this.parent, this.mod));
                return true;
            }
            else
            {
                return false;
            }
        }

        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
        {
            this.btnToggleEnabled.mouseReleased(x, y);
            this.btnSettings.mouseReleased(x, y);
        }

        public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_)
        {
        }
    }
}
