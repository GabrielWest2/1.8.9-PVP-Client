package customclient.cosmetics;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;

public class Cosmetics {
	public static Cape TWITCH;
	public static Cape RICK_ROLL;
	public static Cape DOGE;
	public static Cape DISCORD;
	public static Cape GRAFFITI;
	public static Cape PURPLED;
	public static Cape E;
	public static Cape FOREST_DUSK;
	public static Cape SAD;
	public static Cape CHICKEN;
	public static Cape PASTEL_AESTHETIC;
	public static Cape YOUTUBE;
	public static Cape GLITCHSTORM;
	
	static {
		new Cosmetics();
	}
	
	public Cosmetics() {
		TWITCH = new Cape(0, "Twitch", "customclient/cosmetics/capes/twitch/0.png");
		RICK_ROLL = new Cape(1, "Rick Roll", "customclient/cosmetics/capes/rickroll", 17);
		DOGE = new Cape(2, "Doge", "customclient/cosmetics/capes/doge", 10);
		DISCORD = new Cape(3, "Discord", "customclient/cosmetics/capes/discord/0.png");
		GRAFFITI = new Cape(4, "Graffiti", "customclient/cosmetics/capes/graffiti/0.png");
		PURPLED = new Cape(5, "Purpled", "customclient/cosmetics/capes/purpled", 15);
		E = new Cape(6, "E", "customclient/cosmetics/capes/e/0.png");
		FOREST_DUSK = new Cape(7, "Forest Dusk", "customclient/cosmetics/capes/forest_dusk/0.png");
		SAD = new Cape(8, "Sad", "customclient/cosmetics/capes/sad/0.png");
		CHICKEN = new Cape(9, "Chicken", "customclient/cosmetics/capes/chicken/0.png");
		PASTEL_AESTHETIC = new Cape(10, "Pastel Aesthetic", "customclient/cosmetics/capes/pastel_aesthetic/0.png");
		YOUTUBE = new Cape(11, "Youtube", "customclient/cosmetics/capes/youtube/0.png");
		GLITCHSTORM = new Cape(12, "Glitchstorm", "customclient/cosmetics/capes/glitchstorm", 23);
	}
	
	public static void update() {
		((AnimatedResourceLocation)RICK_ROLL.getTextureLocation()).update();
		((AnimatedResourceLocation)DOGE.getTextureLocation()).update();
		((AnimatedResourceLocation)PURPLED.getTextureLocation()).update();
		((AnimatedResourceLocation)GLITCHSTORM.getTextureLocation()).update();
	}
	
	public class Cape {

		private final int id;
		private final String name;
		private final ResourceLocation location;
		
		public Cape(int id, String name, String path) {
			this.id = id;
			this.name = name;
			this.location = new ResourceLocation(path);

		}
		
		
		public Cape(int id, String name, String path, int frames) {
			this.id = id;
			this.name = name;
			this.location = new AnimatedResourceLocation(path, frames);
	
		}
		
		public int getId() {
			return this.id;
		}
		
		public String getName() {
			return this.name;
		}
		public ResourceLocation getTextureLocation() {
			return this.location;
		}
	}
}
