package game6.core.entities;

import game6.core.ai.pathfinding.Pathfinder;
import game6.core.interfaces.IPosition;

import java.util.List;

import de.nerogar.util.Vector3f;

public interface Movement extends IPosition {

	List<Vector3f> getPathTo(Vector3f target);

	public Pathfinder getPathfinder();

	public List<Vector3f> getPath();

	void setPath(List<Vector3f> newPath);

	public void setTarget(Vector3f target, float stopDistanceSquared);

	default public void move(Vector3f target, float stopDistanceSquared) {
		setTarget(target, stopDistanceSquared);
		// TODO stop fight
	}

	default public void move(Vector3f target) {
		move(target, 0);
	}

	public float getStopDistanceSquared();

	default public boolean hasMovementGoal() {
		return !getPath().isEmpty();
	}

	default public Vector3f getNextGoal() {
		if (getPath().isEmpty()) {
			return null;
		}
		return getPath().get(0);
	}

	default public Vector3f getLastGoal() {
		if (getPath().isEmpty()) {
			return null;
		}
		return getPath().get(getPath().size() - 1);
	}

	public void stop();

	public float getSpeed();

	public float getRotation();

	public void setRotation(float rotation);

	default void updateRotation() {
		Vector3f goal = getNextGoal();
		if (goal == null) {
			return;
		}
		Vector3f dir = goal.subtracted(getPosition());

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

	default void advancePathNode() {
		if (hasMovementGoal()) {
			getPath().remove(0);
		}
	}

	default void updateMovement(float timeDelta) {

		float remainingMovement = getSpeed() * timeDelta;

		while (hasMovementGoal() && remainingMovement > 0 && getStopDistanceSquared() < getPosition().subtracted(getLastGoal()).getSquaredValue()) {

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
