package game6.core.networking.packets;

import game6.client.buildings.IClientBuilding;
import game6.core.buildings.ICoreBuilding;
import game6.core.world.Map;
import game6.core.world.Tile;
import game6.server.buildings.IServerBuilding;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;

public class PacketMap extends Packet {

	public Map<? extends ICoreBuilding> map;

	public PacketMap() {
	}

	public PacketMap(Map<IServerBuilding> map) {
		this.map = map;
	}

	@SuppressWarnings("unchecked")
	public Map<IClientBuilding> getClientMap() {
		return (Map<IClientBuilding>) map;
	}
	
	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);

		int sizeX = buffer.getInt();
		int sizeY = buffer.getInt();

		byte[] tileIDs = new byte[sizeX * sizeY];
		buffer.get(tileIDs, 0, sizeX * sizeY);
		Tile[][] tiles = new Tile[sizeX][sizeY];

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				tiles[x][y] = Tile.byID(tileIDs[y * sizeX + x]);
			}
		}

		map = new Map<IClientBuilding>(tiles);
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + map.getSizeX() * map.getSizeY());
		buffer.putInt(map.getSizeX());
		buffer.putInt(map.getSizeY());

		// serialize the tiles
		byte[] tileIDs = new byte[map.getSizeX() * map.getSizeY()];
		for (int x = 0; x < map.getSizeX(); x++) {
			for (int y = 0; y < map.getSizeY(); y++) {
				tileIDs[y * map.getSizeX() + x] = map.getTile(x, y).getID();
			}
		}

		buffer.put(tileIDs, 0, tileIDs.length);
		return buffer.array();
	}

}
