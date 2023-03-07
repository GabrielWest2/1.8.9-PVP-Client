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

public class EnchantmentGlintModDeserializer implements JsonDeserializer<EnchantmentGlintMod> {

	@Override
	public EnchantmentGlintMod deserialize(JsonElement element, Type t, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = element.getAsJsonObject();
		RelativePosition pos = (RelativePosition)ClientFileManager.gson.fromJson(obj.get("position"), RelativePosition.class);
		EnchantmentGlintMod m = new EnchantmentGlintMod();
		boolean enabled = obj.get("enabled").getAsBoolean();
		m.enabled = enabled;
		m.position = pos;
		m.red = obj.get("red").getAsInt();
		m.green = obj.get("green").getAsInt();
		m.blue = obj.get("blue").getAsInt();
		return m;
	}

}
