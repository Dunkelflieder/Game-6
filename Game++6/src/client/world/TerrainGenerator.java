package client.world;

public class TerrainGenerator {

	public static Terrain getTerrain(int sizeX, int sizeY) {

		Tile[][] tiles = new Tile[sizeX][sizeY];

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				if (Math.random() > 0.01) tiles[x][y] = Tile.CHROME;
				else tiles[x][y] = Tile.REACTOR;
			}
		}
		return new Terrain(tiles);

	}

}
