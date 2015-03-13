package game6.core.ai.goalfinding;

import game6.core.buildings.CoreBuildingTower;
import game6.core.buildings.ICoreBuilding;
import game6.core.world.CoreWorld;

import java.util.*;

public class Goalfinder {

	private CoreWorld<? extends ICoreBuilding> world;

	public Goalfinder(CoreWorld<? extends ICoreBuilding> world) {
		this.world = world;
	}

	private class Node {

		public ICoreBuilding building;
		public Node origin;

		public Node(ICoreBuilding building, Node origin) {
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

	public List<Path> search(ICoreBuilding startBuilding) {

		List<Path> paths = new ArrayList<>();

		HashSet<Node> scope = new HashSet<>();

		Node start = new Node(startBuilding, null);

		List<Node> openList = new ArrayList<>();
		openList.add(start);
		scope.add(start);

		while (openList.size() > 0) {

			Node check = openList.remove(0);
			List<? extends ICoreBuilding> candidates = world.getMap().getBuildingsWithin(check.building.getPosX(), check.building.getPosY(), check.building.getRange());
			for (ICoreBuilding building : candidates) {

				Node newNode = new Node(building, check);
				if (building.canReceiveEnergy()) {
					paths.add(nodeToPath(newNode));
				} else if (building instanceof CoreBuildingTower) {
					if (scope.add(newNode)) {
						openList.add(newNode);
					}
				}
			}
		}

		return paths;
	}

	private Path nodeToPath(Node node) {
		List<ICoreBuilding> path = new ArrayList<>();
		while (node != null) {
			path.add(0, node.building);
			node = node.origin;
		}
		return new Path(path);
	}

}