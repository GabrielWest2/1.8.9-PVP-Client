package customclient.mods;

import java.awt.Color;

import customclient.Mod;
import customclient.RelativePosition;
import customclient.RelativePosition.ModPosition;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.*;


public class CoordinatesMod extends Mod{
	
	public CoordinatesMod() {
		this.hudHeight = fr.FONT_HEIGHT * 4 + 17;
		this.hudWidth = 100;
		this.position = new RelativePosition(0, -hudHeight, ModPosition.BOTTOM_LEFT);
	}
	
	public void OnUIRender() {
		mc.mcProfiler.startSection("coordinates mod");
		GlStateManager.pushMatrix();
		
		int opacity = 150;
		int bg = new Color(0, 0, 0, opacity).getRGB();
		int fg = Color.white.getRGB();
		int hudX = position.calculateX();
		int hudY = position.calculateY();
		Gui.drawRect(hudX, hudY, hudX + hudWidth, hudY + hudHeight, bg);

        Entity entity = this.mc.getRenderViewEntity();
        EnumFacing enumfacing = entity.getHorizontalFacing();
        String s = "";

        boolean isZ = false;
        boolean isPos = false;
        switch (enumfacing.ordinal())
        {
            case 2:
            	isZ = true;
            	isPos = false;
            	s="N";
                break;

            case 3:
            	isZ = true;
            	isPos = true;
            	s="S";
                break;

            case 4:
            	isZ = false;
            	isPos = false;
            	s="W";
                break;

            case 5:
            	isZ =false;
            	isPos = true;
            	s="E";
        }

		//Draw Direction 
        String sign = isPos ? "+" : "-";
        
		if(!isZ)
			fr.drawString(sign, hudX+hudWidth-fr.getStringWidth(sign) - 5 - (fr.getStringWidth(s)/2 - fr.getStringWidth(sign)/2), hudY+5, fg);
		fr.drawString(s, hudX+hudWidth-fr.getStringWidth(s) - 5, hudY+8 + fr.FONT_HEIGHT, fg);
		if(isZ)
			fr.drawString(sign, hudX+hudWidth-fr.getStringWidth(sign) - 5 - (fr.getStringWidth(s)/2 - fr.getStringWidth(sign)/2), hudY+11 + fr.FONT_HEIGHT * 2, fg);
		
		//Draw positions
		fr.drawString("§lX:§r " + ((int)mc.getRenderViewEntity().posX), hudX+5, hudY+5, fg);
		fr.drawString("§lY:§r " + ((int)mc.getRenderViewEntity().posY), hudX+5, hudY+8 + fr.FONT_HEIGHT, fg);
		fr.drawString("§lZ:§r " + ((int)mc.getRenderViewEntity().posZ), hudX+5, hudY+11 + fr.FONT_HEIGHT * 2, fg);
		//Draw biomes
		BiomeGenBase b = getPlayerBiome();
		fr.drawString("Biome: " + getBiomeColor(b) + b.getBiomeClass().getSimpleName().replaceAll("BiomeGen", ""), hudX+5, hudY+13 + fr.FONT_HEIGHT * 3, fg);
		
		GlStateManager.popMatrix();
		mc.mcProfiler.endSection();
	}
	public <T extends BiomeGenBase> T getPlayerBiome() {
		return (T) mc.getRenderViewEntity().getEntityWorld().getBiomeGenForCoords(new BlockPos(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ));
	}
	
	public String getBiomeColor(BiomeGenBase g) {
		if(g instanceof BiomeGenBeach || g instanceof BiomeGenDesert) {
			return "§e";
		}
		if(g instanceof BiomeGenHell) {
			return "§4";
		}
		if(g instanceof BiomeGenEnd) {
			return "§6";
		}
		if(g instanceof BiomeGenSavanna) {
			return "§2";
		}
		
		
		if(g instanceof BiomeGenRiver || g instanceof BiomeGenSwamp || g instanceof BiomeGenOcean) {
			return "§9";
		}
		if(g instanceof BiomeGenPlains || g instanceof BiomeGenJungle || g instanceof BiomeGenForest  || g instanceof BiomeGenHills){
			return "§a";
		}
		return "";
	}
	
	@Override
	public String getName() {
		return I18n.format("mod.coordinates.name");
	}
	@Override
	public String getInternalName() {
		return "XYZ Mod";
	}
}
