package customclient.file;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import customclient.Mod;
import customclient.mods.*;

public class ModAdapter implements JsonSerializer<Mod> {

	 @Override
	 public JsonElement serialize(Mod src, Type typeOfSrc,
	            JsonSerializationContext context) {
	        JsonObject obj = new JsonObject();
	        JsonElement jsonElement = ClientFileManager.gson.toJsonTree(src.position);
	        if(!(src instanceof MinimalViewBobbingMod))
	        	obj.add("position", jsonElement);
	        obj.addProperty("enabled", src.enabled);

	        if(src instanceof ParticleMultiplier) {
	        	obj.addProperty("multiplier", ((ParticleMultiplier)src).multiplier);
	        	obj.addProperty("randomness", ((ParticleMultiplier)src).randomness);
	        	obj.addProperty("sharp_always", ((ParticleMultiplier)src).sharpAlways);
	        }else if(src instanceof TimeChangerMod) {
	        	obj.addProperty("selection", ((TimeChangerMod)src).selection);
	        }else if(src instanceof HypixelChatFilterMod) {
	        	obj.addProperty("show_team_messages", ((HypixelChatFilterMod)src).showTeamMessages);
	        	obj.addProperty("show_friend_messages", ((HypixelChatFilterMod)src).showFriendMessages);
	        	obj.addProperty("show_party_messages", ((HypixelChatFilterMod)src).showPartyMessages);
	        	obj.addProperty("show_join_messages", ((HypixelChatFilterMod)src).showJoinMessages);
	        	obj.addProperty("show_quit_messages", ((HypixelChatFilterMod)src).showQuitMessages);
	        	obj.addProperty("show_chat_messages", ((HypixelChatFilterMod)src).showChatMessages);
	        	obj.addProperty("show_other_messages", ((HypixelChatFilterMod)src).showOtherMessages);
	        }else if(src instanceof ScoreboardMod) {
	        	obj.addProperty("show_numbers", ((ScoreboardMod)src).showNums);
	        }else if(src instanceof AutoGGMod) {
	        	obj.addProperty("gg_on_win", ((AutoGGMod)src).ggOnWin);
	        	obj.addProperty("gg_on_loss", ((AutoGGMod)src).ggOnLoss);
	        }else if(src instanceof PingMod) {
	        	obj.addProperty("show_hud", ((PingMod)src).showHud);
	        	obj.addProperty("show_bars", ((PingMod)src).showBars);
	        	obj.addProperty("show_tab", ((PingMod)src).showInTab);
	        }else if(src instanceof EnchantmentGlintMod) {
	        	obj.addProperty("red", ((EnchantmentGlintMod)src).red);
	        	obj.addProperty("green", ((EnchantmentGlintMod)src).green);
	        	obj.addProperty("blue", ((EnchantmentGlintMod)src).blue);
	        }else if(src instanceof PerspectiveMod) {
	        	obj.addProperty("toggle", ((PerspectiveMod)src).toggle);
	        }
	        return obj;
	    }

	}
