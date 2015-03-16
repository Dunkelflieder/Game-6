package game6.core.interfaces;

import game6.core.faction.Faction;

public interface IFaction {

	/**
	 * Sets the faction this building belongs to.
	 * @param faction Faction-enum
	 */
	public void setFaction(Faction faction);

	public Faction getFaction();

}
