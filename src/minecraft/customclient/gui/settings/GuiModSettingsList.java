package customclient.gui.settings;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

import org.lwjgl.input.Mouse;

import customclient.Main;
import customclient.Mod;
import customclient.gui.settings.GuiButtonSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;

public class GuiModSettingsList extends GuiListExtended{

	private final GuiListExtended.IGuiListEntry[] listEntries;
	private int maxListLabelWidth = 0;
	private Mod m;
	
	public GuiModSettingsList(GuiScreenModSettings settings, Minecraft mcIn, Mod m) {
		super(mcIn, settings.width, settings.height, 27, settings.height - 32, 20);
		this.m = m;
		listEntries = new GuiListExtended.IGuiListEntry[getSize()];
		int i = 0;
		for(Field f : m.getClass().getDeclaredFields()) {
			boolean intVal = f.isAnnotationPresent(CustomizableIntegerValue.class);
			boolean floatVal = f.isAnnotationPresent(CustomizableFloatValue.class);
			boolean doubleVal = f.isAnnotationPresent(CustomizableDoubleValue.class);
			boolean boolVal = f.isAnnotationPresent(CustomizableBooleanValue.class);
			if(intVal ||floatVal || doubleVal || boolVal) {
				if(intVal)
					this.listEntries[i++] = new GuiModSettingsList.ValueEntry(f.getAnnotation(CustomizableIntegerValue.class), f, m);
				else if(floatVal)
					this.listEntries[i++] = new GuiModSettingsList.ValueEntry(f.getAnnotation(CustomizableFloatValue.class), f, m);
				else if(doubleVal)
					this.listEntries[i++] = new GuiModSettingsList.ValueEntry(f.getAnnotation(CustomizableDoubleValue.class), f, m);
				else if(boolVal)
					this.listEntries[i++] = new GuiModSettingsList.ValueEntry(f.getAnnotation(CustomizableBooleanValue.class), f, m);
				
	            int j = mcIn.fontRendererObj.getStringWidth(f.getName());
	
	            if (j > this.maxListLabelWidth)
	            {
	                this.maxListLabelWidth = j;
	            }
			}
		}
	}

	@Override
	public IGuiListEntry getListEntry(int index) {
		return listEntries[index];
	}
	
	public GuiListExtended.IGuiListEntry[] getAllEntries() {
		return listEntries;
	}

	@Override
	protected int getSize() {
		int i = 0;
		for(Field f : m.getClass().getDeclaredFields()) {
			if(f.isAnnotationPresent(CustomizableIntegerValue.class) || f.isAnnotationPresent(CustomizableFloatValue.class) || f.isAnnotationPresent(CustomizableDoubleValue.class) || f.isAnnotationPresent(CustomizableBooleanValue.class)) {
				i++;
			}
		}
		return i;
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

	
    public static enum ValueType {
		INT, DOUBLE, FLOAT, BOOL
	}
	public class ValueEntry implements GuiListExtended.IGuiListEntry, GuiSlider.FormatHelper, GuiPageButtonList.GuiResponder
    {
        private final String uiName;
        private final GuiButton valueButton;

        
        private float min, max;
        
        private int intVal;
        private double doubleVal;
        private float floatVal;
        private boolean boolVal;
        
        private final Field f;
        
        private final ValueType type;
        private final Object refVal;
        
        private CustomizableIntegerValue intValAnnotation; 
        private CustomizableFloatValue floatValAnnotation; 
        private CustomizableDoubleValue doubleValAnnotation; 
        private CustomizableBooleanValue boolValAnnotation; 
        
        private ValueEntry(CustomizableIntegerValue val, Field f, Object refVal)
        {
        	this.intValAnnotation = val;
        	this.refVal = refVal;
        	this.f = f;
        	this.uiName = I18n.format(val.uiName());
        	this.min = val.min();
        	this.max = val.max();
    		try {
				this.intVal = f.getInt(refVal);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
        	
            this.valueButton = new GuiSlider(this, 0, 0, 0, uiName, this.min, this.max, (float)this.intVal, this);
            type = ValueType.INT;
        }

        private ValueEntry(CustomizableFloatValue val, Field f, Object refVal)
        {
        	this.floatValAnnotation = val;
        	this.refVal = refVal;
        	this.f = f;
        	this.uiName = I18n.format(val.uiName());
        	this.min = val.min();
        	this.max = val.max();
    		try {
				this.floatVal = f.getFloat(refVal);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
        	
            this.valueButton = new GuiSlider(this, 0, 0, 0, uiName, this.min, this.max, this.floatVal, this);
            type = ValueType.FLOAT;
        }

        private ValueEntry(CustomizableDoubleValue val, Field f, Object refVal)
        {
        	this.doubleValAnnotation = val;
        	this.refVal = refVal;
        	this.f = f;
        	this.uiName = I18n.format(val.uiName());
        	this.min = val.min();
        	this.max = val.max();
    		try {
				this.doubleVal = f.getDouble(refVal);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
        	
            this.valueButton = new GuiSlider(this, 0, 0, 0, uiName, this.min, this.max, (float)this.doubleVal, this);
            
            type = ValueType.DOUBLE;
        }

        private ValueEntry(CustomizableBooleanValue val, Field f, Object refVal)
        {
        	this.boolValAnnotation = val;
        	this.refVal = refVal;
        	this.f = f;
        	this.uiName = I18n.format(val.uiName());
        	try {
				this.boolVal = f.getBoolean(refVal);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
        	
            this.valueButton = new GuiButton(0, 0, 0, 75*2, 20, getText(0, "", 0));
            type = ValueType.BOOL;
        }

        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected)
        {
            GuiModSettingsList.this.mc.fontRendererObj.drawString(this.uiName, x + 60 - GuiModSettingsList.this.maxListLabelWidth, y + slotHeight / 2 - GuiModSettingsList.this.mc.fontRendererObj.FONT_HEIGHT / 2, 16777215);
            this.valueButton.xPosition = x + 105;
            this.valueButton.yPosition = y;
            this.valueButton.displayString = getText(0, "", 0);
            this.valueButton.drawButton(GuiModSettingsList.this.mc, mouseX, mouseY);
            if(!Mouse.isButtonDown(0))
            	mouseReleased( slotIndex, mouseX, mouseY, 0, 0, 0);
        }

        public boolean hasBeenChanged() {
        	boolean flag = false;
        	if(type == ValueType.INT) {
				return this.intVal != this.intValAnnotation.defaultValue();
			} else if(type == ValueType.FLOAT) {
				return this.floatVal != this.floatValAnnotation.defaultValue();
			} else if(type == ValueType.DOUBLE) {
				return this.doubleVal != this.doubleValAnnotation.defaultValue();
			}else if(type == ValueType.BOOL) {
				return this.boolVal != this.boolValAnnotation.defaultValue();
			}
        	return false;
        }
        
        public void resetToDefault() {
        	try {
	        	if(type == ValueType.INT) {
					this.intVal = this.intValAnnotation.defaultValue();
					f.set(refVal, this.intVal);
				} else if(type == ValueType.FLOAT) {
					this.floatVal = this.floatValAnnotation.defaultValue();
					f.set(refVal, this.floatVal);
				} else if(type == ValueType.DOUBLE) {
					this.doubleVal = this.doubleValAnnotation.defaultValue();
					f.set(refVal, this.doubleVal);
				}else if(type == ValueType.BOOL) {
					this.boolVal = this.boolValAnnotation.defaultValue();
					f.set(refVal, this.boolVal);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if(this.valueButton instanceof GuiSlider) {
        		if(type == ValueType.INT) {
					((GuiSlider)this.valueButton).func_175218_a(this.intVal, true);
				} else if(type == ValueType.FLOAT) {
					((GuiSlider)this.valueButton).func_175218_a(this.floatVal, true);
				} else if(type == ValueType.DOUBLE) {
					((GuiSlider)this.valueButton).func_175218_a((float)this.doubleVal, true);
				}
        		
        	}
        }
        
        public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int p_148278_4_, int p_148278_5_, int p_148278_6_)
        {
            if (this.valueButton.mousePressed(GuiModSettingsList.this.mc, mouseX, mouseY))
            {
            	if(type == ValueType.BOOL) {
            		try {
            			boolVal = !boolVal; 
						f.set(refVal, boolVal);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
                return true;
            }
            else
            {
                return false;
            }
        }

        @Override
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
        {
        	if(this.valueButton instanceof GuiSlider) {
        		((GuiSlider)this.valueButton).mouseReleased(x, y);
        		
        	}else {
        		this.valueButton.mouseReleased(x,y);
        	}
        }

        public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_)
        {
        }

		@Override
		public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTick(int id, float value) {
			try {
				if(type == ValueType.INT) {
					f.set(refVal, Math.round(value));
					intVal =  Math.round(value);
				} else if(type == ValueType.FLOAT) {
					f.set(refVal, value);
					floatVal = value;
				} else if(type == ValueType.DOUBLE) {
					f.set(refVal, (double)value);
					doubleVal = value;
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void func_175319_a(int p_175319_1_, String p_175319_2_) {
			// TODO Auto-generated method stub
			
		}
		DecimalFormat df = new DecimalFormat();
		
		@Override
		public String getText(int id, String name, float value) {
			df.setMaximumFractionDigits(1);
			if(type == ValueType.INT) {
				return intVal+"";
			} else if(type == ValueType.FLOAT) {
				return df.format(floatVal);
			} else if(type == ValueType.DOUBLE) {
				return df.format(doubleVal);
			}
			return this.boolVal ? "§aTrue" : "§cFalse";
		}
    }
}
