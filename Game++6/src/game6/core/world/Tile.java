package game6.core.world;

public enum Tile {
	MARS((byte) 1, 1, "mars.png"),
	ICE((byte) 2, 1, "ice.png");

	private byte id;
	private float cost;
	private String tex;

	Tile(byte id, float cost, String tex) {
		this.id = id;
		this.cost = cost;
		this.tex = tex;
	}

	public byte getID() {
		return id;
	}
	
	public float getCost() {
		return cost;
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
