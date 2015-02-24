package game6.client.world;

import game6.core.world.Map;

import java.awt.image.BufferedImage;

import de.nerogar.render.*;
import de.nerogar.render.Texture2D.InterpolationType;

public class Minimap {

	private Texture2D tex;
	private BufferedImage img;
	private Map map;

	public Minimap() {
	}

	public Texture2D getTexture() {
		return tex;
	}

	public void setMap(Map map) {
		this.map = map;
		if (map != null) {
			img = new BufferedImage(map.getSizeX(), map.getSizeY(), BufferedImage.TYPE_INT_ARGB);
			update(0, 0, map.getSizeX(), map.getSizeY());
		} else {
			if (tex != null) {
				tex.cleanup();
				tex = null;
			}
		}
	}

	public void update(int fromX, int fromY, int sizeX, int sizeY) {
		for (int x = fromX; x < fromX + sizeX; x++) {
			for (int y = fromY; y < fromY + sizeY; y++) {
				// TODO don't reload the whole image on each update!
				img.setRGB(x, y, map.getTile(x, y).getColor().getARGB());
			}
		}
		if (tex != null) {
			tex.cleanup();
		}
		tex = TextureLoader.loadTexture(img, "minimap", InterpolationType.NEAREST);
	}

}
