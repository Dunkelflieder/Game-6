package game6.core.entities;

import game6.core.engine.*;
import game6.core.engine.IDList.UniqueID;
import game6.core.faction.Faction;
import game6.core.world.Removable;
import game6.core.world.Updateable;

public interface CoreEntity extends Movement, Positionable, Boundingable, UniqueID, Removable, Updateable {

	@Override
	default void update(float timeDelta) {
		updateMovement(timeDelta);
	}

	public String getName();
	
	public void setFaction(Faction faction);
	
	public Faction getFaction();
	
}
