package game6.core.ai.goalfinding;

import game6.core.buildings.CoreBuilding;

public class Node {

	public CoreBuilding building;
	public Node origin;
	
	public Node(CoreBuilding building, Node origin) {
		this.building = building;
		this.origin = origin;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			return ((Node) obj).building == building;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) building.getID();
	}
	
}
