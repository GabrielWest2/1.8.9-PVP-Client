package customclient.file.deserialization;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import customclient.RelativePosition;
import customclient.file.ClientFileManager;
import customclient.mods.*;

public class HypixelChatFilterModDeserializer implements JsonDeserializer<HypixelChatFilterMod> {

	@Override
	public HypixelChatFilterMod deserialize(JsonElement element, Type t, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = element.getAsJsonObject();
		RelativePosition pos = (RelativePosition)ClientFileManager.gson.fromJson(obj.get("position"), RelativePosition.class);
		HypixelChatFilterMod m = new HypixelChatFilterMod();
		boolean enabled = obj.get("enabled").getAsBoolean();
		m.enabled = enabled;
		m.position = pos;
		m.showTeamMessages = obj.get("show_team_messages").getAsBoolean();
		m.showFriendMessages = obj.get("show_friend_messages").getAsBoolean();
		m.showPartyMessages = obj.get("show_party_messages").getAsBoolean();
		m.showJoinMessages = obj.get("show_join_messages").getAsBoolean();
		m.showQuitMessages = obj.get("show_quit_messages").getAsBoolean();
		m.showChatMessages = obj.get("show_chat_messages").getAsBoolean();
		m.showOtherMessages = obj.get("show_other_messages").getAsBoolean();
		return m;
	}

}
