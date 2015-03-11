package game6.core.entities;

import game6.core.ai.pathfinding.Pathfinder.Position;
import game6.core.combat.FightingObject;
import game6.core.faction.Faction;
import game6.core.networking.packets.PacketEntityGoalChanged;
import game6.core.networking.packets.PacketEntityMoved;
import game6.core.world.Map;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.util.MathHelper;
import de.nerogar.util.Vector3f;

public abstract class CoreEntity extends BaseEntity<Vector3f> {

	protected int tick;
	private float rotation;
	private float visibleRotation;
	private static final float rotationSpeed = 6;

	private Map map;
	private Faction faction;

	private List<Vector3f> goals;
	private float stopDistanceSquared;
	private boolean moved = false;
	private boolean hasNewGoal = false;

	private FightingObject fightingObject;

	// maximum movement speed in m/s
	private float speed;
	private boolean flying;

	public CoreEntity(long id, BoundingAABB<Vector3f> bounding, Vector3f position, float speed, boolean flying, int maxHealth) {
		super(id, bounding, position);
		this.speed = speed;
		this.flying = flying;

		this.tick = 0;
		this.rotation = 0;
		goals = new ArrayList<Vector3f>();

		setFightingObject(new FightingObject(maxHealth, getPosition(), () -> {
			playDeathAnimation();
			removeFromWorld();
		}));
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
		getFightingObject().setTarget(null);
		setTarget(to, 0);
	}

	private void setTarget(Vector3f targetPos, float stopDistance) {
		this.stopDistanceSquared = stopDistance * stopDistance;

		goals.clear();

		if (targetPos != null) {
			if (isFlying()) {
				// Just add the goal

				goals.add(targetPos.added(new Vector3f(0.5f, 0f, 0.5f)));

				goals.add(targetPos);
			} else {
				List<Position> path = getMap().getPath(getPosition().getX(), getPosition().getZ(), targetPos.getX(), targetPos.getZ());
				if (path == null) {

					System.err.println("NO PATH FOUND to: " + targetPos + ", from: " + getPosition());
				} else {
					for (Position node : path) {
						goals.add(new Vector3f(node.x + 0.5f, 0, node.y + 0.5f));
					}
					if (goals.size() > 0) {
						goals.remove(goals.size() - 1);
					}
					goals.add(targetPos.clone());
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

		// If moving on x-axis, set direction manually
		if (Math.abs(dir.getZ()) < 0.001f) {
			if (dir.getX() > 0) {
				rotation = (float) (3 * Math.PI / 2);
			} else {
				rotation = (float) (Math.PI / 2);
			}
		} else {
			rotation = (float) (-Math.atan(dir.getX() / (dir.getZ())));
			// fix unaligned due to arctan in 3rd and 4th (?) quadrant.
			if (dir.getZ() <= 0) {
				rotation += Math.PI;
			}
		}
	}

	public void advanceOneGoal() {
		goals.remove(0);
		hasNewGoal = true;
		checkPathBlocked();
		// int depth = 5;
		// for (int i = 0; i < Math.min(goals.size(), depth); i++) {
		// Vector3f pos = goals.get(i);
		// if (getMap().getBuildingAt((int) Math.floor(pos.getX()), (int) Math.floor(pos.getZ())) != null) {
		// path is blocked! recalculate new one
		// Vector3f last = goals.get(goals.size() - 1);
		// move(last);
		// }
		// }
	}

	private void checkPathBlocked() {
		if (!isFlying() && getNextGoal() != null) {
			Position currentPosition = new Position((int) Math.floor(getPosition().getX()), (int) Math.floor(getPosition().getZ()));
			Position nextPosition = new Position((int) Math.floor(getNextGoal().getX()), (int) Math.floor(getNextGoal().getZ()));
			if (getMap().getPathfinder().intersectsBuilding(currentPosition, nextPosition)) {
				Vector3f last = goals.get(goals.size() - 1);
				move(last);
			}
		}
	}

	@Override
	public void update(float timeDelta) {

		tick++;

		if (!goals.isEmpty()) {
			float squaredDistanceToTarget = goals.get(goals.size() - 1).subtracted(getPosition()).getSquaredValue();
			if (squaredDistanceToTarget > stopDistanceSquared) {

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
			} else if (squaredDistanceToTarget <= stopDistanceSquared) {
				setTarget(null, 0);
			}
		}

		if (hasNewGoal) {
			updateRotation();
			Faction.broadcastAll(new PacketEntityGoalChanged(this));
			moved = true;
			hasNewGoal = false;
		}

		if (moved && tick % 50 == 0) {
			// update position: prevents major desyncs
			Faction.broadcastAll(new PacketEntityMoved(this));
			// Force update somewhen
			hasNewGoal = true;
			moved = false;
		}

		// TODO Don't check this every update maybe.
		checkPathBlocked();

		// determine the delta this entity needs to turn.
		float turngoal = rotation - visibleRotation;
		// fix, if turning > 180°. Reverse the direction
		if (turngoal > Math.PI) {
			turngoal -= 2 * Math.PI;
		}
		// fix, if turning < -180°. Reverse the direction
		if (turngoal < -Math.PI) {
			turngoal += 2 * Math.PI;
		}
		float delta = MathHelper.clamp(turngoal, -rotationSpeed * timeDelta, rotationSpeed * timeDelta);
		visibleRotation = (float) ((visibleRotation + delta) % (2 * Math.PI));

		if (getFightingObject().getTarget() != null) {
			if (tick % 10 == 0)
				setTarget(getFightingObject().getTarget().getPosition(), getFightingObject().getReach());
		}

		getFightingObject().update();

	}

	private void playDeathAnimation() {
		// TODO implement
	}

	protected float getVisibleRotation() {
		return visibleRotation;
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

	public FightingObject getFightingObject() {
		return fightingObject;
	}

	public void setFightingObject(FightingObject fightingObject) {
		this.fightingObject = fightingObject;
	}

	public void attack(FightingObject target) {
		getFightingObject().setTarget(target);

		setTarget(target.getPosition(), getFightingObject().getReach());
	}

	public abstract String getName();

}
