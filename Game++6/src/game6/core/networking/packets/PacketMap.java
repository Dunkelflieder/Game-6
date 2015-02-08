package game6.core.networking.packets;

import game6.core.world.Tile;
import de.felk.NodeFile.NodeFile;

public class PacketMap extends Packet {

	public Tile[][] tiles;

	public PacketMap() {
	}
	
	public PacketMap(Tile[][] tiles) {
		this.tiles = tiles;
	}

	@Override
	public NodeFile toNode() {
		NodeFile node = new NodeFile();

		int sizeX = tiles.length;
		int sizeY = tiles.length == 0 ? 0 : tiles[0].length;
		
		// save the size
		node.add('x', sizeX);
		node.add('y', sizeY);

		// serialize the tiles
		int[] tileIDs = new int[sizeX * sizeY];
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				tileIDs[x * y] = tiles[x][y].getID();
			}
		}
		node.add('t', tileIDs);

		return node;
	}

	@Override
	public void loadNode(NodeFile node) {
		int sizeX = node.getInt('x');
		int sizeY = node.getInt('y');

		int[] tileIDs = node.getIntArray('t');
		tiles = new Tile[sizeX][sizeY];
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				tiles[x][y] = Tile.byID(tileIDs[x * y]);
			}
		}
	}

}
