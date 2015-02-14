package game6.core.world;

public enum Tile {
	MARS((byte) 1, "mars.png"),
	ICE((byte) 2, "ice.png");

	private byte id;
	private String tex;

	Tile(byte id, String tex) {
		this.id = id;
		this.tex = tex;
	}

	public byte getID() {
		return id;
	}
	
	public String getTex() {
		return tex;
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
