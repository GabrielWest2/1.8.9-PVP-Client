package customclient.mods;

import customclient.Mod;
import customclient.gui.settings.CustomizableBooleanValue;
import net.minecraft.client.resources.I18n;

public class HypixelChatFilterMod extends Mod{
	
	public static final String[] joinTexts = new String[] {"joined ", "connected "};
	public static final String[] leaveTexts = new String[] {" left", " quit", " disconnected"};
	public static final String[] friendTexts = new String[] {"Friend >"};
	public static final String[] partyTexts = new String[] {"Party >", "to accept"};
	public static final String[] teamTexts = new String[] {"TEAM", "team", "Team", "RED", "red","Red", "GREEN", "green","Green", "BLUE", "blue","Blue", "YELLOW", "YELLOW","Yellow"};
	
	@CustomizableBooleanValue(defaultValue=true, uiName = "mod.hypixelchatfilter.showTeamMessages")
	public boolean showTeamMessages = true;
	
	@CustomizableBooleanValue(defaultValue=true, uiName = "mod.hypixelchatfilter.showFriendMessages")
	public boolean showFriendMessages = true;
	
	@CustomizableBooleanValue(defaultValue=true, uiName = "mod.hypixelchatfilter.showPartyMessages")
	public boolean showPartyMessages = true;
	
	@CustomizableBooleanValue(defaultValue=false, uiName = "mod.hypixelchatfilter.showJoinMessages")
	public boolean showJoinMessages = false;
	
	@CustomizableBooleanValue(defaultValue=false, uiName = "mod.hypixelchatfilter.showQuitMessages")
	public boolean showQuitMessages = false;
	
	@CustomizableBooleanValue(defaultValue=false, uiName = "mod.hypixelchatfilter.showChatMessages")
	public boolean showChatMessages = false;
	
	@CustomizableBooleanValue(defaultValue=false, uiName = "mod.hypixelchatfilter.showOtherMessages")
	public boolean showOtherMessages = false;
	
	@Override
	public String getName() {
		return I18n.format("mod.hypixelchatfilter.name");
	}
	@Override
	public String getInternalName() {
		return "Hypixel Chat Filter";
	}
	
	
	
	public static boolean hasAny(String s, String[] contains) {
		boolean flag = false;
		for(int i = 0; i < contains.length; i++) {
			if(s.contains(contains[i])) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public static String removeColorCodes(String s) {
		s = s.replaceAll("§0", "");
		s = s.replaceAll("§1", "");
		s = s.replaceAll("§2", "");
		s = s.replaceAll("§3", "");
		s = s.replaceAll("§4", "");
		s = s.replaceAll("§5", "");
		s = s.replaceAll("§6", "");
		s = s.replaceAll("§7", "");
		s = s.replaceAll("§8", "");
		s = s.replaceAll("§9", "");
		s = s.replaceAll("§a", "");
		s = s.replaceAll("§b", "");
		s = s.replaceAll("§c", "");
		s = s.replaceAll("§d", "");
		s = s.replaceAll("§e", "");
		s = s.replaceAll("§f", "");
		s = s.replaceAll("§k", "");
		s = s.replaceAll("§l", "");
		s = s.replaceAll("§m", "");
		s = s.replaceAll("§n", "");
		s = s.replaceAll("§o", "");
		s = s.replaceAll("§r", "");
		return s;
	}
}
