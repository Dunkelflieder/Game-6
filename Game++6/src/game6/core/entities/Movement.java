package game6.core.entities;

import game6.core.ai.pathfinding.Pathfinder;
import game6.core.interfaces.IPosition;

import java.util.List;

import de.nerogar.util.Vector3f;

public interface Movement extends IPosition {

	List<Vector3f> getPathTo(Vector3f target);

	public Pathfinder getPathfinder();

	public List<Vector3f> getMovementPath();

	void setMovementPath(List<Vector3f> newPath);

	void setMoveTarget(MoveTarget target);

	MoveTarget getMoveTarget();

	default public void updatePath() {
		if (!hasMovementTarget()) {
			stopMovement();
			return;
		}
		List<Vector3f> path = getPathTo(getMoveTarget().getMovePosition());
		if (path == null) {
			stopMovement();
		} else {
			setMovementPath(path);
		}
	}

	default public void move(MoveTarget target) {
		/*if (this instanceof ICombat) {
			((ICombat) this).setCombatTarget(null);
		}*/
		setMoveTarget(target);
		updatePath();
	}

	default public boolean hasMovementTarget() {
		return getMoveTarget() != null;
	}

	default public boolean hasMovementPath() {
		return !getMovementPath().isEmpty();
	}

	default public Vector3f getNextGoal() {
		if (!hasMovementPath()) {
			return null;
		}
		return getMovementPath().get(0);
	}

	default public Vector3f getLastGoal() {
		if (!hasMovementPath()) {
			return null;
		}
		return getMovementPath().get(getMovementPath().size() - 1);
	}

	default public void stopMovement() {
		setMoveTarget(null);
		getMovementPath().clear();
	}

	public float getSpeed();

	public float getRotation();

	public void setRotation(float rotation);

	default void rotationChanged() {
	}
	
	default void setRotation(Vector3f dir) {
		// If moving on x-axis, set direction manually
		float rotation;
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
		setRotation(rotation);
	}

	default void updateRotation() {
		Vector3f goal = getNextGoal();
		if (goal == null) {
			return;
		}
		setRotation(goal.subtracted(getPosition()));
	}

	default void advancePathNode() {
		if (hasMovementPath()) {
			getMovementPath().remove(0);
		} else {
			if (hasMovementTarget()) {
				updatePath();
			} else {
				stopMovement();
			}
		}
	}

	default void updateMovement(float timeDelta) {

		// TODO don't check this every tick
		if (isPathBlocked()) {
			updatePath();
		}

		float remainingMovement = getSpeed() * timeDelta;

		while (hasMovementPath() && remainingMovement > 0) {

			if (hasMovementTarget() && getMoveTarget().isReached()) {
				stopMovement();
				break;
			}

			Vector3f moveDelta = getNextGoal().subtracted(getPosition());

			float moveDistance = moveDelta.getValue();

			if (moveDistance > remainingMovement) {

				moveDelta.setValue(remainingMovement);
				remainingMovement = 0;

			} else {

				advancePathNode();
				remainingMovement -= moveDistance;

			}

			teleportRelative(moveDelta);

		}

		updateRotation();

	}

	boolean isPathBlocked();

}
