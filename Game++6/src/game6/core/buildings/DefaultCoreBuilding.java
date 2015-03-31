package game6.core.buildings;

import game6.core.faction.Faction;
import de.nerogar.util.Vector3f;

public abstract class DefaultCoreBuilding implements CoreBuilding {

	private static long MAX_ID;
	private long id;
	private int sizeX, sizeY, posX, posY;

	private int energy;
	private int maxEnergy;
	private int range;

	private Faction faction;
	private boolean removalMarked;

	private int health;
	private int maxHealth;

	public DefaultCoreBuilding(long id, int sizeX, int sizeY, int maxEnergy, int range, int maxHealth) {
		init();
		if (id > MAX_ID) {
			MAX_ID = id;
		}
		this.id = id;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.posX = 0;
		this.posY = 0;
		this.maxEnergy = maxEnergy;
		this.range = range;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}

	public abstract void init();

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

	public int getRange() {
		return range;
	}

	public Vector3f getCenterPosition() {
		return new Vector3f(getPosX() + 0.5f * getSizeX(), 0.5f, getPosY() + 0.5f * getSizeY());
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

	public abstract String getName();

	public int getEnergy() {
		return energy;
	}

	private int getEnergyOverflow() {
		if (this.energy > maxEnergy) {
			int overflow = this.energy - maxEnergy;
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

	public int addEnergy(int energy) {
		this.energy += energy;
		return getEnergyOverflow();
	}

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

	public void setFaction(Faction faction) {
		this.faction = faction;
	}

	public Faction getFaction() {
		return faction;
	}

	protected static long getNextID() {
		MAX_ID++;
		return MAX_ID;
	}

	public boolean canReceiveEnergy() {
		return energy < maxEnergy;
	}

	@Override
	public void remove() {
		removalMarked = true;
	}

	@Override
	public boolean isRemovalMarked() {
		return removalMarked;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public int getMaxHealth() {
		return maxHealth;
	}

}
