package game6.core.entities;

import game6.core.buildings.CoreBuilding;
import game6.core.interfaces.IPosition;
import game6.core.util.Position;
import de.nerogar.util.Vector3f;

public class MoveTargetBuilding implements MoveTarget {

	private static final float MAX_INTERACTION_DISTANCE = 1f;

	private IPosition mover;
	private CoreBuilding building;

	public MoveTargetBuilding(IPosition mover, CoreBuilding building) {
		this.mover = mover;
		this.building = building;
	}

	@Override
	public boolean isReached() {
		if (building.getPosX() > mover.getPosition().getX() + MAX_INTERACTION_DISTANCE ||
				building.getPosX() + building.getSizeX() < mover.getPosition().getX() - MAX_INTERACTION_DISTANCE ||
				building.getPosY() > mover.getPosition().getY() + MAX_INTERACTION_DISTANCE ||
				building.getPosY() + building.getSizeY() < mover.getPosition().getY() - MAX_INTERACTION_DISTANCE) {
			return false;
		}
		return true;
	}

	@Override
	public Vector3f getMovePosition() {
		Position freeNeighbour = building.getFreeNeighbourPosition();
		if (freeNeighbour == null) {
			return null;
		}
		return new Vector3f(freeNeighbour.x + 0.5f, 0, freeNeighbour.y + 0.5f);
	}

}
