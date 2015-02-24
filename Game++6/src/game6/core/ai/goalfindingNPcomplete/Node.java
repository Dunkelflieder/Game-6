package game6.core.ai.goalfindingNPcomplete;

import game6.core.buildings.CoreBuilding;

public class Node {

	private CoreBuilding building;
	private Link[] neighbours;

	public Node(CoreBuilding building) {
		this.building = building;
	}

	public void setNeighbours(Link[] neighbours) {
		this.neighbours = neighbours;
	}

	public CoreBuilding getBuilding() {
		return building;
	}

	public Link[] getNeighbours() {
		return neighbours;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			return ((Node) obj).getBuilding() == building;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) building.getID();
	}

}
