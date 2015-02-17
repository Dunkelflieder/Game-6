package game6.core.entities;

import game6.core.ai.pathfinding.Pathfinder.Position;
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
	private float rotation;

	private Map map;
	private Faction faction;

	private List<Vector3f> goals;
	private boolean moved = false, hasNewGoal = false;;

	// maximum movement speed in m/s
	private float speed;
	private boolean flying;

	public CoreEntity(long id, BoundingAABB<Vector3f> bounding, Vector3f position, float speed, boolean flying) {
		super(id, bounding, position);
		this.speed = speed;
		this.flying = flying;
		this.tick = 0;
		this.rotation = 0;
		goals = new ArrayList<Vector3f>();
	}

	public Vector3f getNextGoal() {
		if (goals.size() == 0) {
			return null;
		}
		return goals.get(0);
	}

	public void setGoal(Vector3f to) {
		goals.clear();
		if (to != null) {
			goals.add(to);
		}
		hasNewGoal = true;
	}

	public void move(Vector3f to) {
		goals.clear();
		if (to != null) {
			if (isFlying()) {
				// Just add the goal
				goals.add(to.added(new Vector3f(0.5f, 0f, 0.5f)));
			} else {
				List<Position> path = getMap().getPath(getPosition().getX(), getPosition().getZ(), to.getX(), to.getZ());
				if (path == null) {
					System.err.println("NO PATH FOUND!");
				} else {
					for (Position node : path) {
						goals.add(new Vector3f(node.x + 0.5f, 0, node.y + 0.5f));
					}
				}
			}
		}
		hasNewGoal = true;
	}

	public void updateRotation() {
		Vector3f goal = getNextGoal();
		if (goal == null) {
			return;
		}
		Vector3f dir = goal.subtracted(getPosition());

		rotation = (float) (-Math.atan(dir.getX() / (dir.getZ() - 0.01)));
		// fix unaligned due to arctan in 3rd and 4th (?) quadrant.
		if (dir.getZ() <= 0) {
			rotation += Math.PI;
		}
	}
	
	public void advanceOneGoal() {
		goals.remove(0);
		hasNewGoal = true;
		// test, if path got blocked
		int depth = 5;
		for (int i = 0; i < Math.min(goals.size(), depth); i++) {
			Vector3f pos = goals.get(i);
			if (getMap().getBuildingAt((int) Math.floor(pos.getX()), (int) Math.floor(pos.getZ())) != null) {
				// path is blocked! recalculate new one
				Vector3f last = goals.get(goals.size()-1);
				move(last);
			}
		}
	}

	@Override
	public void update(float timeDelta, List<UpdateEvent> events) {

		tick++;

		// check if path got invalid somewhen
		
		
		float remainingDistance = getSpeed() * timeDelta;
		while (!goals.isEmpty() && remainingDistance > 0) {
			Vector3f moveDelta = getNextGoal().subtracted(getPosition());
			float moveDistance = moveDelta.getValue();

			if (moveDistance > remainingDistance) {
				// goal is too far away to reach in this tick.
				// limit the travelled distance to maximum distance
				moveDelta.setValue(remainingDistance);
				remainingDistance = 0;
			} else {
				// goal is reached with this tick
				advanceOneGoal();
				remainingDistance -= moveDistance;
			}

			teleportRelative(moveDelta);
			moved = true;
		}

		if (hasNewGoal) {
			updateRotation();
			events.add(new EventEntityGoalChanged(this));
			moved = true;
			hasNewGoal = false;
		}

		if (moved && tick % 50 == 0) {
			events.add(new EventEntityMoved(this));
			// Force update somewhen
			hasNewGoal = true;
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
	 * @return Maximum movement speed in m/s
	 */
	public float getSpeed() {
		return speed;
	}

	public boolean isFlying() {
		return flying;
	}

	public void setFlying(boolean flying) {
		this.flying = flying;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

}
