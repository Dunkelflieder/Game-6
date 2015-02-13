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
		byte[] tileIDs = new byte[map.getSizeX() * map.getSizeY()];
		for (int x = 0; x < map.getSizeX(); x++) {
			for (int y = 0; y < map.getSizeY(); y++) {
					tileIDs[y * map.getSizeX() + x] = map.getTile(x, y).getID();
			}
		}
		node.add('t', tileIDs);

		return node;
	}

	@Override
	public void loadNode(NodeFile node) {
		int sizeX = node.getInt('x');
		int sizeY = node.getInt('y');

		byte[] tileIDs = node.getByteArray('t');

		Tile[][] tiles = new Tile[sizeX][sizeY];

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
					tiles[x][y] = Tile.byID(tileIDs[y * sizeX + x]);
			}
		}

		map = new CoreMap(tiles);
	}

}
