package customclient.cosmetics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mojang.util.UUIDTypeAdapter;

import customclient.cosmetics.Cosmetics.Cape;
import customclient.database.ClientDatabaseManager;
import net.minecraft.util.Session;

public class CosmeticsManager {
	private static Map<UUID, String> playerCapes = new HashMap<>();
	
	
	public static Cosmetics.Cape getCape(UUID uuid){
		if(playerCapes.containsKey(uuid))
			getCapeStyle(uuid);
		String cape = playerCapes.get(uuid);
		
		
		if(cape.equals("TWITCH"))
			return Cosmetics.TWITCH;
		if(cape.equals("RICK_ROLL"))
			return Cosmetics.RICK_ROLL;
		if(cape.equals("DOGE"))
			return Cosmetics.DOGE;
		if(cape.equals("DISCORD"))
			return Cosmetics.DISCORD;
		if(cape.equals("GRAFFITI"))
			return Cosmetics.GRAFFITI;
		if(cape.equals("PURPLED"))
			return Cosmetics.PURPLED;
		if(cape.equals("E"))
			return Cosmetics.E;
		if(cape.equals("FOREST_DUSK"))
			return Cosmetics.FOREST_DUSK;
		if(cape.equals("SAD"))
			return Cosmetics.SAD;
		if(cape.equals("CHICKEN"))
			return Cosmetics.CHICKEN;
		if(cape.equals("PASTEL_AESTHETIC"))
			return Cosmetics.PASTEL_AESTHETIC;
		if(cape.equals("YOUTUBE"))
			return Cosmetics.YOUTUBE;
		if(cape.equals("GLITCHSTORM"))
			return Cosmetics.GLITCHSTORM;
		return null;
	}
	
	public static boolean hasCape(UUID uuid) {
		if(playerCapes.containsKey(uuid))
			return playerCapes.get(uuid) != null;
		String style = getCapeStyle(uuid);
		return style != null && !style.equals("NONE");
	}
	
	public static String getCapeStyle(Session session) {
		if(playerCapes.containsKey(UUIDTypeAdapter.fromString(session.getPlayerID())))
			return playerCapes.get(UUIDTypeAdapter.fromString(session.getPlayerID()));
		String query = "SELECT * FROM custom_client.cosmetics WHERE uuid='" + session.getPlayerID() +"'";
		ResultSet rs = ClientDatabaseManager.executeQuery(query);
		try {
			while (rs.next())
			  {
				 String capeStyle = rs.getString("cape_style");
				 playerCapes.put(UUIDTypeAdapter.fromString(session.getPlayerID()), capeStyle);
			    return capeStyle;
			  }
			
			ClientDatabaseManager.st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	     
		
		return null;
	}
	
	public static String getCapeStyle(UUID uuid) {
		if(playerCapes.containsKey(uuid))
			return playerCapes.get(uuid);
		String query = "SELECT * FROM custom_client.cosmetics WHERE uuid='" + UUIDTypeAdapter.fromUUID(uuid) +"'";
		ResultSet rs = ClientDatabaseManager.executeQuery(query);
		try {
			while (rs.next())
			  {
				 String capeStyle = rs.getString("cape_style");
				 playerCapes.put(uuid, capeStyle);
			    return capeStyle;
			  }
			
			ClientDatabaseManager.st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
