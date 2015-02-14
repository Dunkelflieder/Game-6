package game6.client.world;

import game6.core.world.Tile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.nerogar.render.*;
import de.nerogar.render.Texture2D.InterpolationType;

public class TerrainTexture {

	public static final int TEXSIZE = 1024;
	public static final int ROWCOUNT = 4;
	private static final Texture2D texture;

	static {
		BufferedImage img = new BufferedImage(TEXSIZE * ROWCOUNT, TEXSIZE * ROWCOUNT, BufferedImage.TYPE_INT_ARGB);

		for (int index = 0; index < Tile.values().length; index++) {

			Tile tile = Tile.values()[index];

			int x = (tile.getID() % ROWCOUNT) * TEXSIZE;
			int y = (tile.getID() / ROWCOUNT) * TEXSIZE;

			if (y == ROWCOUNT) {
				throw new RuntimeException("Too many terrain textures! " +
						"Unable to stitch " + Tile.values().length + " " + TEXSIZE + "² textures on a " + (ROWCOUNT * TEXSIZE) + "² texture.");
			}

			try {
				BufferedImage texture = ImageIO.read(new File("res/terrain/" + tile.getTex()));
				img.setRGB(x, y, TEXSIZE, TEXSIZE, texture.getRGB(0, 0, texture.getWidth(), texture.getHeight(), null, 0, texture.getWidth()), 0, TEXSIZE);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		texture = TextureLoader.loadTexture(img, "terrain", InterpolationType.NEAREST);
	}

	public static float getOffsetXForID(int id) {
		return (id % ROWCOUNT) / (float) ROWCOUNT;
	}
	
	public static float getOffsetYForID(int id) {
		return (id / ROWCOUNT) / (float) ROWCOUNT;
	}
	
	public static Texture2D getTexture() {
		return texture;
	}

}
