package game6.core.entities;

import game6.core.combat.ICombatTarget;
import game6.core.interfaces.*;
import game6.core.world.IDList.UniqueID;
import game6.core.world.*;
import game6.server.entities.jobs.Job;

public interface CoreEntity extends Movement, IPosition, IBounding, UniqueID, Removable, Updateable, IHealth, ICombatTarget, IProcessPackets, IFaction {

	@Override
	default void update(float timeDelta) {
		updateMovement(timeDelta);
		if (hasJob()) {
			if (getJob().isCancelled()) {
				setJob(null);
			} else {
				getJob().update(timeDelta);
			}
		}
	}

	public String getName();

	default public boolean hasJob() {
		return getJob() != null;
	}

	default public void stopJob() {
		if (hasJob()) getJob().cancel();
	}

	public Job getJob();

	public void setJob(Job job);

}
