package game6.server.entities;

import java.util.List;

import de.nerogar.util.Vector3f;
import game6.core.entities.CoreEntity;
import game6.server.world.World;

public interface ServerEntity extends CoreEntity, ServerEntityBehaviour {

	@Override
	default public void setPath(List<Vector3f> newPath) {
		getPath().clear();
		getPath().addAll(newPath);
		// TODO network update path
	}

	public World getWorld();

	public void setWorld(World world);

}
