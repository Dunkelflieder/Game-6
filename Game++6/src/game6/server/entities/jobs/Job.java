package game6.server.entities.jobs;

import game6.core.world.Updateable;

public abstract class Job implements Updateable {

	private boolean cancelled = false;

	public void cancel() {
		cancelled = true;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public abstract void update(float timeDelta);

}
