package customclient.file.deserialization.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import customclient.RelativePosition;
import net.minecraft.util.Session;

public class SessionDeserializer implements JsonDeserializer<Session> {

	@Override
	public Session deserialize(JsonElement element, Type t, JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject obj = element.getAsJsonObject();
		String name = obj.get("username").getAsString();
		String uuid = obj.get("uuid").getAsString();
		String token = obj.get("token").getAsString();
		String type = obj.get("type").getAsString();

		
		return new Session(name, uuid, token, type);
	}

}
