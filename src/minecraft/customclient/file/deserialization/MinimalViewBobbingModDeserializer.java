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

public class MinimalViewBobbingModDeserializer implements JsonDeserializer<MinimalViewBobbingMod> {

	@Override
	public MinimalViewBobbingMod deserialize(JsonElement element, Type t, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = element.getAsJsonObject();
		MinimalViewBobbingMod m = new MinimalViewBobbingMod();
		boolean enabled = obj.get("enabled").getAsBoolean();
		m.enabled = enabled;
		return m;
	}

}
