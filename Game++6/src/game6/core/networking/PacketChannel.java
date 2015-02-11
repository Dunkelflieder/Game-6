package game6.core.networking;

public enum PacketChannel {
	// Don't use CONNECTION_INFO for anything except ConnectionInfo!
	CONNECTION_INFO(0), INIT(1), BUILDINGS(2);

	private int id;

	PacketChannel(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}
}
