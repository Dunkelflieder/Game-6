package game6.core.ai.pathfinding;

class Node {

	public final float cost;

	private Node pointer;
	public int posX, posY;
	private byte state;
	private float costCalc;
	private boolean diagonal;

	public static byte STATE_INIT = 0;
	public static byte STATE_OPEN = 1;
	public static byte STATE_CLOSED = 2;

	public Node(float cost, Node pointer, int posX, int posY) {
		this.cost = cost;
		this.pointer = pointer;
		this.posX = posX;
		this.posY = posY;
	}

	public float getTotalCost(int goalX, int goalY) {
		if (costCalc < 0) {
			int diffX = Math.abs(goalX - posX);
			int diffY = Math.abs(goalY - posY);
			float pointerCost = (pointer == null) ? 0 : pointer.getTotalCost(goalX, goalY);
			// recursion base ~distance cost path varying sqrt(2)
			costCalc = pointerCost + (diffX + diffY + cost) * (isDiagonal() ? 1.41421356f : 1);
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
		return diagonal;
	}

	public void setDiagonal(boolean diagonal) {
		this.diagonal = diagonal;
		costCalc = -1;
	}

}
