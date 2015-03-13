package game6.server.entities;

import game6.client.entities.guis.EntityGui;
import game6.core.entities.CoreEntity;
import game6.core.entities.CoreEntityHelicopter1;
import de.nerogar.render.Shader;
import de.nerogar.util.Vector3f;

public class EntityHelicopter1 extends CoreEntityHelicopter1 {

	public EntityHelicopter1() {
		super(getNextID(), new Vector3f());
	}

	@Override
	public void render(Shader shader) {
	}

	@Override
	public EntityGui<CoreEntity> getGui() {
		return null;
	}

}
