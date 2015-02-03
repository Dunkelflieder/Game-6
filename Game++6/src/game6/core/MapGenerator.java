package game6.core;

import game6.client.world.buildings.BuildingReactor;

public class MapGenerator {

	public static CoreMap getMap(int sizeX, int sizeY) {

		Tile[][] tiles = new Tile[sizeX][sizeY];

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				tiles[x][y] = Tile.CHROME;
			}
		}

		CoreMap map = new CoreMap(tiles);
		for (int x = 0; x < tiles.length-1; x++) {
			for (int y = 0; y < tiles[x].length-1; y++) {
				if (Math.random() < 0.01) {
					// TODO remove any references to non-core classes! Use core Classes.
					// The following line causes the server to crash, because it causes OpenGL stuff.
					//map.addBuilding(x, y, new BuildingReactor());
				}
			}
		}
		
		return map;

	}

}
