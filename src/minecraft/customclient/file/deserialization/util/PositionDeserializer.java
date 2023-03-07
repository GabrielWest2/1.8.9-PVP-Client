package customclient.file.deserialization.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import customclient.Mod;
import customclient.RelativePosition;
import customclient.mods.KeystrokesMod;

public class PositionDeserializer implements JsonDeserializer<RelativePosition> {

	@Override
	public RelativePosition deserialize(JsonElement element, Type t, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = element.getAsJsonObject();
		int relX = obj.get("x").getAsInt();
		int relY = obj.get("y").getAsInt();
		String anchorName = obj.get("anchor").getAsString();
		RelativePosition.ModPosition pos = RelativePosition.ModPosition.valueOf(anchorName);
		
		return new RelativePosition(relX, relY, pos);
	}

}
