package game6.core.world;

import game6.server.buildings.IServerBuilding;
import game6.server.world.World;

public class WorldGenerator {

	public static World getWorld(long seed, int sizeX, int sizeY) {

		Tile[][] tiles = new Tile[sizeX][sizeY];

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {

				double r = SimplexNoise.noise(x / 100f, y / 100f, 100);

				tiles[x][y] = Tile.MARS;

				if (r > 0.7) {
					tiles[x][y] = Tile.ICE;
				}

			}
		}

		return new World(new Map<IServerBuilding>(tiles));

	}

}
