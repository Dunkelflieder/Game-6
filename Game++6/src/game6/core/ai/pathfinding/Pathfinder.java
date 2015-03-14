package game6.core.ai.pathfinding;

import game6.core.buildings.CoreBuilding;
import game6.core.world.Map;

import java.util.*;

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

	public Pathfinder(Map<? extends CoreBuilding> map) {
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

	public void update(Map<? extends CoreBuilding> map, int fromX, int fromY, int toX, int toY) {
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
				nodes[x + y * sizeX] = new Node(cost, x, y);
			}
		}
	}

	private Node getNodeAt(int posX, int posY) {
		if (posX < 0 || posY < 0 || posX >= sizeX || posY >= sizeY) {
			return null;
		}
		return nodes[posX + sizeX * posY];
	}

	public List<Position> getPath(float fromX, float fromY, float goalX, float goalY) {
		return getPath((int) fromX, (int) fromY, (int) goalX, (int) goalY);
	}
	
	public List<Position> getPath(int fromX, int fromY, int goalX, int goalY) {
		List<Position> path = getRawPath(fromX, fromY, goalX, goalY);

		if (path == null) {
			return null;
		}

		List<Position> smoothedPath = new ArrayList<>();

		if (path.size() == 0) {
			return smoothedPath;
		}

		Position prev = path.get(0);
		smoothedPath.add(prev);

		int i = 1;
		while (i < path.size()) {
			while (i + 1 < path.size() && !intersectsBuilding(prev, path.get(i + 1))) {
				i++;
			}
			prev = path.get(i);
			smoothedPath.add(prev);
			i++;
		}

		return smoothedPath;
	}

	public List<Position> getRawPath(int fromX, int fromY, int goalX, int goalY) {

		ArrayList<Node> openList = new ArrayList<Node>();
		ArrayList<Node> closedList = new ArrayList<Node>();

		Node current = getNodeAt(fromX, fromY);

		// check if start and goal are valid
		Node to = getNodeAt(goalX, goalY);
		if (current == null || to == null) {
			return null;
		}
		if (!to.isWalkable()) {
			return null;
		}

		current.setState(Node.STATE_OPEN);
		openList.add(current);

		Position[] newPos = new Position[8];

		// try finding a path, but abort if it's too expensive (thats the limit for)
		for (int i = 0; i < maxDepth; i++) {

			// no path possible
			if (openList.size() == 0) {
				reset(openList, closedList);
				return null;
			}

			// cheapest open node is always at 0
			current = openList.get(0);

			// goal reached
			if (current.posX == goalX && current.posY == goalY) {
				ArrayList<Position> path = nodeToArraylist(current);
				reset(openList, closedList);
				return path;
			}

			newPos[Node.DIR_UP] = new Position(current.posX, current.posY - 1); // up
			newPos[Node.DIR_RIGHT] = new Position(current.posX + 1, current.posY); // right
			newPos[Node.DIR_DOWN] = new Position(current.posX, current.posY + 1); // down
			newPos[Node.DIR_LEFT] = new Position(current.posX - 1, current.posY); // left
			newPos[Node.DIR_UPRIGHT] = new Position(current.posX + 1, current.posY - 1); // up-right
			newPos[Node.DIR_RIGHTDOWN] = new Position(current.posX + 1, current.posY + 1); // right-down
			newPos[Node.DIR_DOWNLEFT] = new Position(current.posX - 1, current.posY + 1); // down-left
			newPos[Node.DIR_LEFTUP] = new Position(current.posX - 1, current.posY - 1); // left-up

			// remember if the nodes up, right, down and left were walkable (initialized with false)
			// boolean[] walkable = new boolean[4];
			for (byte j = 0; j < newPos.length; j++) {

				// if (j > 3) {
				// skip this diagonal nodes if the 2 corresponding straight nodes were not walkable
				// if (!(walkable[j - 4] && walkable[j == 7 ? 0 : j - 3])) {
				// example: j = 4: up-right
				// j-4 = 0 => up
				// j-3 = 1 => right

				// example: j = 7: left-up
				// j-4 = 3 => left
				// j-3 = 4 => Out of Bounds => j==7: 0 => up
				// continue;
				// }
				// }

				Node node = getNodeAt(newPos[j].x, newPos[j].y);

				// no such node OR node already in open or closed list OR node not walkable
				if (node == null || node.getState() > Node.STATE_INIT || !node.isWalkable()) {
					continue;
				}

				node.setPointer(current);
				node.setDir(j);

				// add to openList at the correct place (openList stays sorted)
				addNodeSorted(openList, node, goalX, goalY);
				node.setState(Node.STATE_OPEN);

				// if (j < 4) {
				// walkable[j] = true;
				// }
			}

			// remove processed node from open list and add to pseudo closed list
			openList.remove(current);
			current.setState(Node.STATE_CLOSED);
			closedList.add(current);

		}

		reset(openList, closedList);
		return null;
	}

	private void reset(List<Node> openList, List<Node> closedList) {
		for (Node node : closedList) {
			node.reset();
		}
		for (Node node : openList) {
			node.reset();
		}
	}

	private static ArrayList<Position> nodeToArraylist(Node node) {
		ArrayList<Position> a = new ArrayList<Position>();
		a.add(new Position(node.posX, node.posY));
		byte prevDir = -1;
		while (node.getPointer() != null) {
			node = node.getPointer();
			// Skip points on straight lines.
			if (node.getDir() == prevDir) {
				// continue;
			}
			a.add(0, new Position(node.posX, node.posY));
			prevDir = node.getDir();
		}
		a.remove(0);
		return a;
	}

	private static void addNodeSorted(ArrayList<Node> list, Node node, int goalX, int goalY) {
		int il = 0;
		int ir = list.size();
		int mid = 0;
		while (il < ir) {
			if (node.getTotalCost(goalX, goalY) < list.get(mid).getTotalCost(goalX, goalY)) {
				ir = mid;
			} else {
				il = mid + 1;
			}
			mid = (il + ir) / 2;
		}
		list.add(il, node);
	}

	/**
	 * Bresenham-based supercover line algorithm.
	 * Inspired by: http://lifc.univ-fcomte.fr/~dedu/projects/bresenham/index.html
	 * @param p1 Point 1 of line
	 * @param p2 Point 2 of line
	 * @return true, if the line intersects any building. false, if the line has no obstacles.
	 */
	public boolean intersectsBuilding(Position p1, Position p2) {

		int dx = p2.x - p1.x;
		int dy = p2.y - p1.y;

		int signumX = signum(dx);
		int signumY = signum(dy);

		if (dx < 0) {
			dx = -dx;
		}
		if (dy < 0) {
			dy = -dy;
		}

		int stepX, stepY;
		int errorSlow, errorFast;
		if (dx > dy) {
			stepX = signumX;
			stepY = 0;
			errorSlow = dx;
			errorFast = dy;
		} else {
			stepX = 0;
			stepY = signumY;
			errorSlow = dy;
			errorFast = dx;
		}

		int x = p1.x;
		int y = p1.y;

		int error = errorSlow / 2;
		int errorPrev = error;

		for (int i = 0; i < errorSlow; i++) {

			error -= errorFast;

			if (error < 0) {

				error += errorSlow;

				x += signumX;
				y += signumY;

				if (error + errorPrev < dx) {
					if (!isFree(x, y - signumY)) {
						return true;
					}
				} else if (error + errorPrev > dx) {
					if (!isFree(x - signumX, y)) {
						return true;
					}
				}

			} else {

				x += stepX;
				y += stepY;

			}

			if (!isFree(x, y)) {
				return true;
			}

			errorPrev = error;

		}

		return false;
	}

	private static int signum(int x) {
		return x > 0 ? 1 : (x < 0 ? -1 : 0);
	}

	private boolean isFree(int x, int y) {
		return getNodeAt(x, y).isWalkable();
	}

}
