package game6.core;

import game6.core.buildings.CoreBuilding;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.util.Color;

public enum Faction {

	BLUE(new Color(0.2f, 0.3f, 1.0f, 1.0f)),
	RED(new Color(1.0f, 0.2f, 0.1f, 1.0f)),
	GREEN(new Color(0.1f, 1.0f, 0.3f, 1.0f)),
	YELLOW(new Color(1.0f, 0.5f, 0.0f, 1.0f));

	//TODO change to private (later)
	public List<CoreBuilding> ownBuildings;
	public int buildingMaterial;
	public Color color;

	private Faction(Color color) {
		ownBuildings = new ArrayList<CoreBuilding>();
		buildingMaterial = 0;
		this.color = color;
	}

	public static Faction getRandom() {
		return values()[(int) (Math.random() * values().length)];
	}

}
