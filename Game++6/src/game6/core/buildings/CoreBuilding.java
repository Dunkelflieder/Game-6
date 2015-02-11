package game6.core.buildings;

import game6.core.Faction;
import game6.core.events.Event;
import game6.core.world.CoreMap;

import java.util.List;

public abstract class CoreBuilding {

	private int id;
	private int sizeX, sizeY, posX, posY;

	private int energy;
	private int maxEnergy;
	
	protected CoreMap map;
	protected Faction faction;

	public CoreBuilding(int id, int sizeX, int sizeY, int maxEnergy) {
		init();
		this.id = id;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.posX = 0;
		this.posY = 0;
		this.faction = Faction.YELLOW;
		this.maxEnergy = maxEnergy;
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
	public abstract String getName();

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

	public void setMap(CoreMap map) {
		this.map = map;
	}
	
	public void setFaction(Faction faction){
		this.faction = faction;
	}
	
	public Faction getFaction(){
		return faction;
	}

}
