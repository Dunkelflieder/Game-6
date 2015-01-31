package client.main;

public class MathHelper {

	public static float clamp(float val, float min, float max) {
		return Math.min(Math.max(min, val), max);
	}

}
