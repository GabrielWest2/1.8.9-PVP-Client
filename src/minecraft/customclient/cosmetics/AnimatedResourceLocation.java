package customclient.cosmetics;

import java.io.File;

import net.minecraft.util.ResourceLocation;

public class AnimatedResourceLocation extends ResourceLocation {
	private ResourceLocation[] frames;
	private int frame = 3;
	private int subFrame = 0;
	private int subFrames = 50;
	private int maxFrames;
	public AnimatedResourceLocation(String resourceName, int frameCount) {
		super(resourceName + "/0.png");
		maxFrames = frameCount;
		frames = new ResourceLocation[frameCount];
		for(int i = 0; i < frameCount; i++) {
			frames[i] = new ResourceLocation(resourceName + "/" + i+".png");
		}
	}
	
	public void update() {
		
		this.subFrame++;
		if(this.subFrame == this.subFrames) {
			this.subFrame = 0;
			this.frame ++;
			if(this.frame == maxFrames) {
				this.frame = 0;
			}
		}
	}
	
	public ResourceLocation getFrame() {
		return frames[frame];
	}

}
