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

public class ParticleMultiplierDeserializer implements JsonDeserializer<ParticleMultiplier> {

	@Override
	public ParticleMultiplier deserialize(JsonElement element, Type t, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = element.getAsJsonObject();

		ParticleMultiplier m = new ParticleMultiplier();
		m.enabled = obj.get("enabled").getAsBoolean();
		m.multiplier = obj.get("multiplier").getAsInt();
		m.randomness = obj.get("randomness").getAsDouble();
		m.sharpAlways = obj.get("sharp_always").getAsBoolean();
		return m;
	}

}
