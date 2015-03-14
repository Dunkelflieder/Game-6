package game6.client.entities;

import game6.core.entities.CoreEntity;

import java.util.List;

import de.nerogar.util.Vector3f;

public interface ClientEntity extends CoreEntity, ClientEntityBehaviour {

	default public void setPath(List<Vector3f> newPath) {
		getPath().clear();
		getPath().addAll(newPath);
	}

}
