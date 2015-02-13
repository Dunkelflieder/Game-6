package game6.core.world;

public enum Tile {
	CHROME((byte) 1),
	MARS((byte) 2),
	ICE((byte) 3);

	private byte id;

	Tile(byte id) {
		this.id = id;
	}

	public byte getID() {
		return id;
	}

	public static Tile byID(byte id) {
		for (Tile t : values()) {
			if (t.getID() == id) {
				return t;
			}
		}
		return null;
	}

	public static byte getMaxID() {
		byte max = 0;
		for (Tile t : values()) {
			if (t.getID() > max) {
				max = t.getID();
			}
		}
		return max;
	}
}
