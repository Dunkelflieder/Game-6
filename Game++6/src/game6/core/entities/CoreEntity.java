package game6.core.entities;

import game6.core.faction.Faction;
import game6.core.interfaces.*;
import game6.core.world.IDList.UniqueID;
import game6.core.world.*;

public interface CoreEntity extends Movement, IPosition, IBounding, UniqueID, Removable, Updateable, IHealth, IProcessPackets {

	@Override
	default void update(float timeDelta) {
		updateMovement(timeDelta);
	}

	public String getName();

	public void setFaction(Faction faction);

	public Faction getFaction();

}
