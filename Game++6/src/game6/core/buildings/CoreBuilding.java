package game6.core.buildings;

import game6.core.faction.Faction;
import game6.core.world.Map;

import java.util.List;

import de.nerogar.engine.UpdateEvent;
import de.nerogar.render.Shader;

public abstract class CoreBuilding {

	private static long MAX_ID;
	private long id;
	private int sizeX, sizeY, posX, posY;

	private int energy;
	private int maxEnergy;

	protected Map map;
	protected Faction faction;

	public CoreBuilding(long id, int sizeX, int sizeY, int maxEnergy) {
		init();
		if (id > MAX_ID)
			MAX_ID = id;
		this.id = id;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.posX = 0;
		this.posY = 0;
		this.maxEnergy = maxEnergy;
	}

	public abstract void init();

	/**
	 * @return Unique Building-ID of this instance
	 */
	public long getID() {
		return id;
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

	/**
	 * Sets the x-position of this building on the map.
	 * Is or should be used while this instance is added to the map.
	 * @param posX x-Position on the map
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	/**
	 * Sets the y-position of this building on the map.
	 * Is or should be used while this instance is added to the map.
	 * @param posY y-Position on the map
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	/**
	 * Updates the logic and can cause (network) events, that are added to the supplied list
	 * @param events list of events, where new events can be appended
	 */
	public abstract void update(List<UpdateEvent> events);

	/**
	 * Should be implemented by client subclasses
	 * @param shader the currently active shader
	 */
	public abstract void render(Shader shader);

	/**
	 * Returns a human-readable name for this building.
	 * @return String with human readable name
	 */
	public abstract String getName();

	/**
	 * Returns the current energy this building has.
	 * @return energy
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * Fixes energy overflowing over the max. Caps energy at max.
	 * @return The amount of energy that was overflowing or 0 if none was overflowing;
	 */
	private int getEnergyOverflow() {
		if (this.energy > maxEnergy) {
			int overflow = maxEnergy - this.energy;
			this.energy = maxEnergy;
			return overflow;
		}
		return 0;
	}

	/**
	 * Fixes energy underflowing below 0
	 * @return The amount of energy that was underflowing or 0 if none was underflowing;
	 */
	private int getEnergyUnderflow() {
		if (this.energy < 0) {
			int underflow = -this.energy;
			this.energy = 0;
			return underflow;
		}
		return 0;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	/**
	 * Adds energy to the building
	 * @param energy added energy
	 * @return amount of energy that was not added due to overflowing
	 */
	public int addEnergy(int energy) {
		this.energy += energy;
		return getEnergyOverflow();
	}

	/**
	 * Subtracts energy from the building
	 * @param energy subtracted energy
	 * @return amount of energy that was not subtracted due to underflowing
	 */
	public int subtractEnergy(int energy) {
		this.energy -= energy;
		return getEnergyUnderflow();
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	/**
	 * Sets the map-instance this building is added to.
	 * Is or should be used while this instance is added to the map.
	 * @param map
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * Sets the faction this building belongs to.
	 * @param faction Faction-enum
	 */
	public void setFaction(Faction faction) {
		this.faction = faction;
	}

	public Faction getFaction() {
		return faction;
	}

	/**
	 * Returns the next unique-Instance-ID.
	 * @return unique building-instance-ID
	 */
	protected static long getNextID() {
		MAX_ID++;
		return MAX_ID;
	}

	/**
	 * Returns whether the building can receive energy or not.
	 * @return true, if the building can receive energy and is not full. False otherwise.
	 */
	public boolean canReceiveEnergy() {
		return energy < maxEnergy;
	}

}
