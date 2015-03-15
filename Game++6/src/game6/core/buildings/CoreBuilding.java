package game6.core.buildings;

import game6.core.engine.IDList.UniqueID;
import game6.core.faction.Faction;
import game6.core.networking.packets.buildings.PacketBuilding;
import de.nerogar.util.Vector3f;

public interface CoreBuilding extends UniqueID {

	public void init();

	public int getSizeX();

	public int getSizeY();

	public int getPosX();

	public int getRange();

	public Vector3f getCenter();

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
	 * Basic update function. If used for network events, then should be overwritten in server class.
	 */
	public void update();

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
	 * Sets the faction this building belongs to.
	 * @param faction Faction-enum
	 */
	public void setFaction(Faction faction);

	public Faction getFaction();

	/**
	 * Returns whether the building can receive energy or not.
	 * @return true, if the building can receive energy and is not full. False otherwise.
	 */
	public boolean canReceiveEnergy();
	
	public void process(PacketBuilding packet);
	
}
