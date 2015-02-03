package game6.core;

public enum Tile {
	CHROME(1);

	private int id;

	Tile(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public static Tile byID(int id) {
		for (Tile t : values()) {
			if (t.getID() == id) {
				return t;
			}
		}
		return null;
	}
}
