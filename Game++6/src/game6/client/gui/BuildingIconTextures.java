package game6.client.gui;

import game6.client.buildings.BuildingImageRenderer;
import game6.core.buildings.BuildingType;
import de.nerogar.render.GameDisplay;
import de.nerogar.render.Texture2D;

public class BuildingIconTextures {

	private static Texture2D[] textures;

	public static void init(GameDisplay display) {
		textures = new Texture2D[BuildingType.values().length];
		for (BuildingType type : BuildingType.values()) {
			textures[type.ordinal()] = BuildingImageRenderer.render(display, type.getClientBuilding(0));
		}
	}

	public static Texture2D get(BuildingType type) {
		return textures[type.ordinal()];
	}

	public static void cleanup() {
		for (Texture2D texture : textures) {
			texture.cleanup();
		}
	}

}
