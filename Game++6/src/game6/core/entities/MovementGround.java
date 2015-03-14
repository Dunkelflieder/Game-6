package game6.core.entities;

import game6.core.ai.pathfinding.Pathfinder.Position;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.util.Vector3f;

public interface MovementGround extends Movement {

	default List<Vector3f> getPathTo(Vector3f target) {
		List<Position> path = getPathfinder().getPath(getPosition().getX(), getPosition().getZ(), target.getX(), target.getZ());
		if (path == null) {
			System.err.println("NO PATH FOUND to: " + target + ", from: " + getPosition());
		} else {
			List<Vector3f> newPath = new ArrayList<>();
			for (Position node : path) {
				newPath.add(new Vector3f(node.x + 0.5f, 0, node.y + 0.5f));
			}
			if (newPath.size() > 0) {
				newPath.remove(newPath.size() - 1);
			}
			newPath.add(target.clone());
			return newPath;
		}
		return null;
	}

	default boolean isPathBlocked() {
		if (!hasMovementGoal()) {
			return false;
		}
		Position currentPosition = new Position((int) Math.floor(getPosition().getX()), (int) Math.floor(getPosition().getZ()));
		Position nextPosition = new Position((int) Math.floor(getNextGoal().getX()), (int) Math.floor(getNextGoal().getZ()));
		return getPathfinder().intersectsBuilding(currentPosition, nextPosition);
	}

}
