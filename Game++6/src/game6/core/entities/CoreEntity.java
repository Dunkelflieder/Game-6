package game6.core.entities;

import game6.core.events.EventEntityGoalChanged;
import game6.core.events.EventEntityMoved;
import game6.core.faction.Faction;
import game6.core.world.Map;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.engine.UpdateEvent;
import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.util.Vector3f;

public abstract class CoreEntity extends BaseEntity<Vector3f> {

	protected int tick;

	private Map map;
	private Faction faction;

	private List<Vector3f> goals;
	private boolean moved = false, hasNewGoal = false;;

	// maximum movement speed in mm/s
	private int speed;
	private boolean flying;

	public CoreEntity(long id, BoundingAABB<Vector3f> bounding, Vector3f position, int speed, boolean flying) {
		super(id, bounding, position);
		this.speed = speed;
		this.flying = flying;
		this.tick = 0;
		goals = new ArrayList<Vector3f>();
	}

	public Vector3f getNextGoal() {
		if (goals.size() == 0) {
			return null;
		}
		return goals.get(0);
	}

	public void move(Vector3f to) {
		if (isFlying()) {
			// Just add the goal
			goals.add(to);
		} else {
			// TODO calculate path and add nodes to goal list
			// for now, act as flying
			goals.add(to);
		}
		hasNewGoal = true;
	}

	public void setGoal(Vector3f goal) {
		goals.clear();
		if (goal != null) {
			goals.add(goal);
		}
		hasNewGoal = true;
	}

	@Override
	public void update(float timeDelta, List<UpdateEvent> events) {

		tick++;

		if (hasNewGoal) {
			events.add(new EventEntityGoalChanged(this));
			hasNewGoal = false;
		}

		if (goals.size() > 0) {
			// Move!
			float maxDistance = 0.001f * getSpeed() * timeDelta;
			Vector3f moveDelta = getNextGoal().subtracted(getPosition());

			if (moveDelta.getValue() > maxDistance) {
				// goal is too far away to reach in this tick.
				// limit the travelled distance to maximum distance
				moveDelta.setValue(maxDistance);
			} else {
				// goal is reached with this tick
				goals.remove(0);
				events.add(new EventEntityGoalChanged(this));
			}

			teleportRelative(moveDelta);
			moved = true;
		}

		if (moved && tick % 5 == 0) {
			events.add(new EventEntityMoved(this));
			moved = false;
		}
	}

	public boolean isMoving() {
		return !goals.isEmpty();
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Map getMap() {
		return map;
	}

	public void setFaction(Faction faction) {
		this.faction = faction;
	}

	public Faction getFaction() {
		return faction;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return Maximum movement speed in mm/s
	 */
	public int getSpeed() {
		return speed;
	}

	public boolean isFlying() {
		return flying;
	}

	public void setFlying(boolean flying) {
		this.flying = flying;
	}

}
