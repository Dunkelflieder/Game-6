package game6.core.interfaces;

import de.nerogar.util.Vector3f;

public interface IPosition {

	public Vector3f getPosition();

	default public void teleport(Vector3f newPos) {
		getPosition().set(newPos);
	}

	default public void teleportRelative(Vector3f newPos) {
		getPosition().add(newPos);
	}

}
