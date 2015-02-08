package game6.core.world;

public class MapGenerator {

	public static Tile[][] getTiles(int sizeX, int sizeY) {

		Tile[][] tiles = new Tile[sizeX][sizeY];

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				tiles[x][y] = Tile.CHROME;
			}
		}

		return tiles;

	}

}
