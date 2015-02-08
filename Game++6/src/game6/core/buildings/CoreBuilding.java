package game6.core.buildings;

import game6.core.events.Event;

import java.util.List;

public abstract class CoreBuilding {

	private int id;
	private int sizeX, sizeY, posX, posY;

	private int energy;
	private int maxEnergy;

	public CoreBuilding(int id, int sizeX, int sizeY) {
		init();
		this.id = id;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.posX = 0;
		this.posY = 0;
	}
	
	public abstract void init();

	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public abstract void update(List<Event> events);
	public abstract void render();

	public int getEnergy() {
		return energy;
	}

	private int getEnergyOverflow() {
		if (this.energy > maxEnergy) {
			int overlap = maxEnergy - this.energy;
			this.energy = maxEnergy;
			return overlap;
		}
		return 0;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int addEnergy(int energy) {
		this.energy += energy;
		return getEnergyOverflow();
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

}
