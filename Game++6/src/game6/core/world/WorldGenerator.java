package game6.core.world;

import game6.server.buildings.BuildingRock;
import game6.server.buildings.ServerBuilding;
import game6.server.world.ServerWorld;

import java.util.Random;

public class WorldGenerator {

	public static ServerWorld getWorld(long seed, int sizeX, int sizeY) {

		Tile[][] tiles = new Tile[sizeX][sizeY];

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {

				double r = SimplexNoise.noise(x / 100f, y / 100f, 100);

				tiles[x][y] = Tile.SAND;

				if (r > 0.7) {
					tiles[x][y] = Tile.ICE;
				}

			}
		}

		ServerWorld world = new ServerWorld(new Map<ServerBuilding>(tiles));

		Random random = new Random();

		for (int i = 0; i < 0.001f * world.getMap().getSizeX() * world.getMap().getSizeY(); i++) {
			world.addBuilding(random.nextInt(world.getMap().getSizeX()), random.nextInt(world.getMap().getSizeY()), new BuildingRock());
		}

		return world;

	}

}
