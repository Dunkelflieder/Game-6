package game6.core.entities;

import game6.core.faction.Faction;
import game6.core.interfaces.*;
import game6.core.networking.packets.entities.PacketEntity;
import game6.core.world.Removable;
import game6.core.world.Updateable;
import game6.core.world.IDList.UniqueID;

public interface CoreEntity extends Movement, IPosition, IBounding, UniqueID, Removable, Updateable, IHealth {

	@Override
	default void update(float timeDelta) {
		updateMovement(timeDelta);
	}

	public String getName();
	
	public void setFaction(Faction faction);
	
	public Faction getFaction();
	
	public void process(PacketEntity packet);
	
}
