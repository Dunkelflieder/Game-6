package game6.networking.packets;

public enum PacketChannel {
	// Don't use CONNECTION_INFO for anything except ConnectionInfo!
	CONNECTION_INFO(0), MAP(1), GAME(2);

	private int id;

	PacketChannel(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}
}