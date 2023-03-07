package customclient.file.deserialization.util;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.Session;

public class SessionSerializer  implements JsonSerializer<Session> {
	@Override
	 public JsonElement serialize(Session src, Type typeOfSrc,
	            JsonSerializationContext context) {
	        JsonObject obj = new JsonObject();

	        obj.addProperty("username", src.getUsername());
	        obj.addProperty("uuid", src.getPlayerID());
	        obj.addProperty("token", src.getToken());
	        obj.addProperty("type", src.getSessionType().toString());
	   
	        return obj;
	    }
}