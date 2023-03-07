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

public class AutoGGModDeserializer implements JsonDeserializer<AutoGGMod> {

	@Override
	public AutoGGMod deserialize(JsonElement element, Type t, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = element.getAsJsonObject();
		RelativePosition pos = (RelativePosition)ClientFileManager.gson.fromJson(obj.get("position"), RelativePosition.class);
		AutoGGMod m = new AutoGGMod();
		boolean enabled = obj.get("enabled").getAsBoolean();
		m.enabled = enabled;
		m.position = pos;
		m.ggOnWin = obj.get("gg_on_win").getAsBoolean();
		m.ggOnLoss = obj.get("gg_on_loss").getAsBoolean();
		return m;
	}

}
