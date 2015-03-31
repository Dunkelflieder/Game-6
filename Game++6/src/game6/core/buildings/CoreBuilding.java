package game6.core.buildings;

import game6.core.combat.ICombatTarget;
import game6.core.interfaces.*;
import game6.core.util.Position;
import game6.core.world.IDList.UniqueID;
import game6.core.world.*;
import de.nerogar.util.Vector3f;

public interface CoreBuilding extends UniqueID, IHealth, ICombatTarget, Removable, Updateable, IProcessPackets, IFaction {

	public void init();

	public int getSizeX();

	public int getSizeY();

	public int getPosX();

	public int getRange();

	public Vector3f getCenterPosition();

	/**
	 * Sets the x-position of this building on the map.
	 * Is or should be used while this instance is added to the map.
	 * @param posX x-Position on the map
	 */
	public void setPosX(int posX);

	public int getPosY();

	/**
	 * Sets the y-position of this building on the map.
	 * Is or should be used while this instance is added to the map.
	 * @param posY y-Position on the map
	 */
	public void setPosY(int posY);

	/**
	 * Returns a human-readable name for this building. Not for actual use, mostly debugging.
	 * @return String with human readable name
	 */
	public String getName();

	/**
	 * Returns the current energy this building has.
	 * @return energy
	 */
	public int getEnergy();

	public void setEnergy(int energy);

	/**
	 * Adds energy to the building
	 * @param energy added energy
	 * @return amount of energy that was not added due to overflowing
	 */
	public int addEnergy(int energy);

	/**
	 * Subtracts energy from the building
	 * @param energy subtracted energy
	 * @return amount of energy that was not subtracted due to underflowing
	 */
	public int subtractEnergy(int energy);

	public int getMaxEnergy();

	public void setMaxEnergy(int maxEnergy);

	/**
	 * Returns whether the building can receive energy or not.
	 * @return true, if the building can receive energy and is not full. False otherwise.
	 */
	public boolean canReceiveEnergy();

	public Map<? extends CoreBuilding> getMap();

	default public Position getFreeNeighbourPosition() {
		for (int y = getPosY() - 1; y <= getPosY() + getSizeY(); y += getSizeY() + 1) {
			for (int x = getPosX() - 1; x <= getPosX() + getSizeX(); x++) {
				if (getMap().getBuildingAt(x, y) == null) {
					if (x >= 0 || y >= 0 || x < getMap().getSizeX() || y < getMap().getSizeY()) {
						return new Position(x, y);
					}
				}
			}
		}
		for (int x = getPosX() - 1; x <= getPosX() + getSizeX(); x += getSizeX() + 1) {
			for (int y = getPosY(); y < getPosY() + getSizeY(); y++) {
				if (getMap().getBuildingAt(x, y) == null) {
					if (x >= 0 || y >= 0 || x < getMap().getSizeX() || y < getMap().getSizeY()) {
						return new Position(x, y);
					}
				}
			}
		}
		return null;
	}

	@Override
	default void update(float timeDelta) {
	}
	
	@Override
	default Vector3f getPosition() {
		return getCenterPosition();
	}

}
