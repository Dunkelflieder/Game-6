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
		
		// Create the image, that will hold all terrain textures
		BufferedImage img = new BufferedImage(TEXSIZE * ROWCOUNT, TEXSIZE * ROWCOUNT, BufferedImage.TYPE_INT_ARGB);

		// iterate over all tiles
		for (int index = 0; index < Tile.values().length; index++) {

			Tile tile = Tile.values()[index];

			// Calculate coorinates for tile ID
			int x = (tile.getID() % ROWCOUNT) * TEXSIZE;
			int y = (tile.getID() / ROWCOUNT) * TEXSIZE;

			// If the next tile texture lies out of stitching-image range, raise an exception
			if (y == ROWCOUNT) {
				throw new RuntimeException("Too many terrain textures! " +
						"Unable to stitch " + Tile.values().length + " " + TEXSIZE + "� textures on a " + (ROWCOUNT * TEXSIZE) + "� texture.");
			}

			// Read the tile texture and put it into the big stitching texture
			try {
				BufferedImage texture = ImageIO.read(new File("res/terrain/" + tile.getTex()));
				img.setRGB(x, y, TEXSIZE, TEXSIZE, texture.getRGB(0, 0, texture.getWidth(), texture.getHeight(), null, 0, texture.getWidth()), 0, TEXSIZE);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// create texture out of the image
		texture = Texture2DLoader.loadTexture(img, "terrain", InterpolationType.NEAREST);
	}

	/**
	 * Returns the texture-x-coordinate for a Tile-ID
	 * @param id ID of the Tile
	 * @return texture-x-coordinate between 0 and 1
	 */
	public static float getOffsetXForID(int id) {
		return (id % ROWCOUNT) / (float) ROWCOUNT;
	}
	
	/**
	 * Returns the texture-y-coordinate for a Tile-ID
	 * @param id ID of the Tile
	 * @return texture-y-coordinate between 0 and 1
	 */
	public static float getOffsetYForID(int id) {
		return (id / ROWCOUNT) / (float) ROWCOUNT;
	}
	
	/**
	 * Returns the Texture for the big stitched image with all terrain textures.
	 * @return Texture2D object
	 */
	public static Texture2D getTexture() {
		return texture;
	}

}
