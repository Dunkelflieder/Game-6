package game6.core.entities;

import game6.core.combat.ICombatTarget;
import game6.core.interfaces.*;
import game6.core.world.IDList.UniqueID;
import game6.core.world.*;

public interface CoreEntity extends Movement, IPosition, IBounding, UniqueID, Removable, Updateable, IHealth, ICombatTarget, IProcessPackets, IFaction {

	@Override
	default void update(float timeDelta) {
		updateMovement(timeDelta);
	}

	public String getName();

}
