package game6.core.world;

import de.nerogar.util.Color;

public enum Tile {
	MARS((byte) 1, 1, "mars.png", new Color(0.4f, 0.1f, 0f, 1f)),
	ICE((byte) 2, 1, "ice.png", new Color(0.8f, 0.8f, 1f, 1f));

	private byte id;
	private float cost;
	private String tex;
	private Color color;

	Tile(byte id, float cost, String tex, Color color) {
		this.id = id;
		this.cost = cost;
		this.tex = tex;
		this.color = color;
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
	
	public Color getColor() {
		return color;
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
