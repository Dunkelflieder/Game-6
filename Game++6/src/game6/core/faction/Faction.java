package game6.core.faction;

import game6.core.buildings.CoreBuilding;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.util.Color;

public enum Faction {
	BLUE(1, new Color(0.2f, 0.3f, 1.0f, 1.0f)),
	RED(2, new Color(1.0f, 0.2f, 0.1f, 1.0f)),
	GREEN(3, new Color(0.1f, 1.0f, 0.3f, 1.0f)),
	YELLOW(4, new Color(1.0f, 0.5f, 0.1f, 1.0f));

	private int id;

	// TODO change to private (later)
	public List<CoreBuilding> ownBuildings;
	public int buildingMaterial;
	public Color color;

	private Faction(int id, Color color) {
		ownBuildings = new ArrayList<CoreBuilding>();
		buildingMaterial = 0;
		this.id = id;
		this.color = color;
	}

	public static Faction getRandom() {
		return values()[(int) (Math.random() * values().length)];
	}

	public int getID() {
		return id;
	}

	public static Faction byID(int id) {
		for (Faction faction : values()) {
			if (faction.getID() == id) {
				return faction;
			}
		}
		return null;
	}

}
