package game6.core.world;

public class MapGenerator {

	public static CoreMap getMap(long seed, int sizeX, int sizeY) {

		Tile[][] tiles = new Tile[sizeX][sizeY];
		float[][] heights = new float[sizeX + 1][sizeY + 1];

		for (int x = 0; x <= sizeX; x++) {
			for (int y = 0; y <= sizeY; y++) {
				if (x < sizeX && y < sizeY) {
					tiles[x][y] = Tile.CHROME;
				}
				// heights[x][y] = (float) SimplexNoise.noise(x / 40f, y / 40f) * 2;
				heights[x][y] = 0;
			}
		}

		return new CoreMap(tiles, heights);

	}

}
