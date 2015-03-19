package game6.core.entities;

import game6.core.interfaces.IPosition;
import de.nerogar.util.Vector3f;

public class MoveTargetPosition implements MoveTarget {

	private IPosition mover;
	private Vector3f position;

	public MoveTargetPosition(IPosition mover, Vector3f position) {
		this.mover = mover;
		this.position = position;
	}

	@Override
	public boolean isReached() {
		return mover.getPosition().subtracted(position).getSquaredValue() <= 0.01f;
	}

	@Override
	public Vector3f getMovePosition() {
		return position;
	}

}
