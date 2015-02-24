package game6.core.ai.pathfinding;

class Node {

	public final float cost;

	private Node pointer;
	public int posX, posY;
	private float costCalc;

	private byte state;
	public static byte STATE_INIT = 0;
	public static byte STATE_OPEN = 1;
	public static byte STATE_CLOSED = 2;

	private byte dir;
	public static byte DIR_UP = 0;
	public static byte DIR_RIGHT = 1;
	public static byte DIR_DOWN = 2;
	public static byte DIR_LEFT = 3;
	public static byte DIR_UPRIGHT = 4;
	public static byte DIR_RIGHTDOWN = 5;
	public static byte DIR_DOWNLEFT = 6;
	public static byte DIR_LEFTUP = 7;

	public Node(float cost, int posX, int posY) {
		this.cost = cost;
		this.posX = posX;
		this.posY = posY;
		reset();
	}

	public void reset() {
		pointer = null;
		state = STATE_INIT;
		dir = DIR_UP;
	}

	public float getTotalCost(int goalX, int goalY) {
		if (costCalc < 0) {
			int diffX = Math.abs(goalX - posX);
			int diffY = Math.abs(goalY - posY);
			float pointerCost = 0;
			float dirChangePenality = 0;
			if (pointer != null) {
				pointerCost = pointer.getTotalCost(goalX, goalY);
				if (pointer.getDir() != dir) {
					dirChangePenality = 0.01f;
				}
			}
			costCalc = pointerCost + dirChangePenality + (diffX * diffX + diffY * diffY) + cost * (isDiagonal() ? 1.41421356f : 1);
		}
		return costCalc;
	}

	public void setPointer(Node node) {
		costCalc = -1;
		pointer = node;
	}

	public Node getPointer() {
		return pointer;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public boolean isDiagonal() {
		return dir > DIR_LEFT;
	}

	public byte getDir() {
		return dir;
	}

	public void setDir(byte dir) {
		this.dir = dir;
		costCalc = -1;
	}
	
	public boolean isWalkable() {
		return cost >= 0;
	}

}
