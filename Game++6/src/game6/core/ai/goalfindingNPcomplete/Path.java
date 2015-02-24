package game6.core.ai.goalfindingNPcomplete;

import game6.core.buildings.CoreBuilding;

public class Path {

	private Node[] nodes;
	private double distance;
	private CoreBuilding goal;

	public Path(Link[] links) {
		nodes = new Node[links.length];
		distance = 0;
		int i = 0;
		for (Link link : links) {
			nodes[i] = link.node;
			distance += link.distance;
			goal = link.node.getBuilding();
			i++;
		}
	}

	public Node[] getNodes() {
		return nodes;
	}

	public double getDistance() {
		return distance;
	}

	public CoreBuilding getGoal() {
		return goal;
	}

}
