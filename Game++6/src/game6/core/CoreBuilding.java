package game6.core;

public abstract class CoreBuilding {

	private int sizeX, sizeY;

	public CoreBuilding(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

}
