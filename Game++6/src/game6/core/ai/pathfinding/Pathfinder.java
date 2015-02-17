package game6.core.ai.pathfinding;

import game6.core.world.Map;

import java.util.ArrayList;
import java.util.List;

public class Pathfinder {

	private int maxDepth = 100000;
	private Node[] nodes;

	private int sizeX, sizeY;

	public static class Position {
		public int x;
		public int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public Pathfinder(Map map) {
		sizeX = map.getSizeX();
		sizeY = map.getSizeY();
		nodes = new Node[sizeX * sizeY];
		update(map, 0, 0, sizeX, sizeY);
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public void update(Map map, int fromX, int fromY, int toX, int toY) {
		System.out.println("Update from " + fromX + "/" + fromY + " to " + toX + "/" + toY);
		if (fromX < 0 || fromY < 0 || toX > sizeX || toY > sizeY || fromX > toX || fromY > toY) {
			throw new RuntimeException("Tried to update an invalid portion of the Pathfinder");
		}
		for (int x = fromX; x < toX; x++) {
			for (int y = fromY; y < toY; y++) {
				// The tile's walking cost
				float cost = map.getTile(x, y).getCost();
				// Make unwalkable if there is a building
				if (map.getBuildingAt(x, y) != null) {
					cost = -1;
				}
				nodes[x + y * sizeX] = new Node(cost, null, x, y);
			}
		}
	}

	private Node getNodeAt(int posX, int posY) {
		if (posX < 0 || posY < 0 || posX >= sizeX || posY >= sizeY) {
			return null;
		}
		return nodes[posX + sizeX * posY];
	}

	public void reset() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].setState(Node.STATE_INIT);
			nodes[i].setPointer(null);
			nodes[i].setDiagonal(false);
		}
	}

	public List<Position> getPath(int fromX, int fromY, int goalX, int goalY) {

		reset();

		ArrayList<Node> openList = new ArrayList<Node>();

		Node current = getNodeAt(fromX, fromY);
		current.setState(Node.STATE_OPEN);
		openList.add(current);

		// check if start and goal are valid
		Node to = getNodeAt(goalX, goalY);
		if (current == null || to == null) {
			return null;
		}
		if (current.cost < 0 || to.cost < 0) {
			return null;
		}

		// try finding a path, but abort if it's too expensive (thats the limit for)
		for (int i = 0; i < maxDepth; i++) {
			// no path possible
			if (openList.size() == 0)
				return null;

			// cheapest open node is always at 0
			current = openList.get(0);

			// goal reached
			if (current.posX == goalX && current.posY == goalY) {
				return nodeToArraylist(current);
			}

			Position[] newPos = new Position[8];
			newPos[0] = new Position(current.posX, current.posY - 1); // up
			newPos[1] = new Position(current.posX + 1, current.posY); // right
			newPos[2] = new Position(current.posX, current.posY + 1); // down
			newPos[3] = new Position(current.posX - 1, current.posY); // left
			newPos[4] = new Position(current.posX + 1, current.posY - 1); // up-right
			newPos[5] = new Position(current.posX + 1, current.posY + 1); // right-down
			newPos[6] = new Position(current.posX - 1, current.posY + 1); // down-left
			newPos[7] = new Position(current.posX - 1, current.posY - 1); // left-up

			// remember if the nodes up, right, down and left were walkable (initialized with false)
			boolean[] walkable = new boolean[4];
			for (int j = 0; j < newPos.length; j++) {

				if (j > 3) {
					// skip this diagonal nodes if the 2 corresponding straight nodes were not walkable
					if (!(walkable[j - 4] && walkable[j == 7 ? 0 : j - 3])) {
						// example: j = 4: up-right
						// j-4 = 0 => up
						// j-3 = 1 => right

						// example: j = 7: left-up
						// j-4 = 3 => left
						// j-3 = 4 => Out of Bounds => j==7: 0 => up
						//continue;
					}
				}

				Node node = getNodeAt(newPos[j].x, newPos[j].y);

				// no such node OR node already in open or closed list OR node not walkable
				if (node == null || node.getState() > Node.STATE_INIT || node.cost < 0) {
					continue;
				}

				node.setPointer(current);
				if (j > 3) {
					node.setDiagonal(true);
				}

				// add to openList at the correct place (openList stays sorted)
				addNodeSorted(openList, node, goalX, goalY);
				node.setState(Node.STATE_OPEN);

				if (j < 4) {
					walkable[j] = true;
				}
			}

			// remove processed node from open list and add to pseudo closed list
			current.setState(Node.STATE_CLOSED);
			openList.remove(0);

		}

		return null;
	}

	private static ArrayList<Position> nodeToArraylist(Node node) {
		ArrayList<Position> a = new ArrayList<Position>();
		a.add(new Position(node.posX, node.posY));
		while (node.getPointer() != null) {
			node = node.getPointer();
			a.add(0, new Position(node.posX, node.posY));
		}
		return a;
	}

	private static void addNodeSorted(ArrayList<Node> list, Node node, int goalX, int goalY) {
		int il = 0;
		int ir = list.size();
		int mid = 0;
		while (il < ir) {
			if (node.getTotalCost(goalX, goalY) < list.get(mid).getTotalCost(goalX, goalY))
				ir = mid;
			else
				il = mid + 1;
			mid = (il + ir) / 2;
		}
		list.add(il, node);
	}

}
