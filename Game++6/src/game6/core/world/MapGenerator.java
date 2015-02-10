package game6.core.world;

public class MapGenerator {

	public static CoreMap getMap(long seed, int sizeX, int sizeY) {

		Tile[][] tiles = new Tile[sizeX][sizeY];

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				tiles[x][y] = Tile.CHROME;
			}
		}

		return new CoreMap(tiles);

	}

}
