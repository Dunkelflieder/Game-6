package game6.core.networking.packets;

import game6.core.world.CoreMap;
import game6.core.world.Tile;
import de.felk.NodeFile.NodeFile;

public class PacketMap extends Packet {

	public CoreMap map;

	public PacketMap() {
	}

	public PacketMap(CoreMap map) {
		this.map = map;
	}

	@Override
	public NodeFile toNode() {
		NodeFile node = new NodeFile();

		// save the size
		node.add('x', map.getSizeX());
		node.add('y', map.getSizeY());

		// serialize the tiles
		int[] tileIDs = new int[map.getSizeX() * map.getSizeY()];
		float[] heights = new float[(map.getSizeX() + 1) * (map.getSizeY() + 1)];
		for (int x = 0; x <= map.getSizeX(); x++) {
			for (int y = 0; y <= map.getSizeY(); y++) {
				if (x < map.getSizeX() && y < map.getSizeY()) {
					tileIDs[y * map.getSizeX() + x] = map.getTile(x, y).getID();
				}
				heights[y * map.getSizeX() + x] = map.getHeight(x, y);
			}
		}
		node.add('t', tileIDs);
		node.add('h', heights);

		return node;
	}

	@Override
	public void loadNode(NodeFile node) {
		int sizeX = node.getInt('x');
		int sizeY = node.getInt('y');

		int[] tileIDs = node.getIntArray('t');
		float[] heights = node.getFloatArray('h');

		Tile[][] tiles = new Tile[sizeX][sizeY];
		float[][] heights2d = new float[sizeX + 1][sizeY + 1];

		for (int x = 0; x <= sizeX; x++) {
			for (int y = 0; y <= sizeY; y++) {
				if (x < sizeX && y < sizeY) {
					tiles[x][y] = Tile.byID(tileIDs[y * sizeX + x]);
				}
				heights2d[x][y] = heights[y * sizeX + x];
			}
		}

		map = new CoreMap(tiles, heights2d);
	}

}
