package game6.client.entities;

import game6.core.entities.CoreEntityHelicopter;
import de.nerogar.render.*;
import de.nerogar.util.Vector3f;
import de.nerogar.util.Vectorf;

public class EntityHelicopter extends CoreEntityHelicopter {

	private RenderProperties renderPropertiesMain;
	private RenderProperties renderPropertiesRotorL;
	private RenderProperties renderPropertiesRotorH;

	private WavefrontMesh meshMain;
	private WavefrontMesh meshRotorL;
	private WavefrontMesh meshRotorH;

	public EntityHelicopter(Vectorf<?> position) {
		super(position);

		meshMain = WavefrontLoader.loadObject("res/entities/helicopter1/mesh.obj");
		meshRotorL = WavefrontLoader.loadObject("res/entities/helicopter1/meshRotorL.obj");
		meshRotorH = WavefrontLoader.loadObject("res/entities/helicopter1/meshRotorH.obj");

		renderPropertiesMain = new RenderProperties(getPosition(), new Vector3f(), null);
		renderPropertiesRotorL = new RenderProperties(getPosition(), new Vector3f(), null);
		renderPropertiesRotorH = new RenderProperties(getPosition(), new Vector3f(), null);
	}

	@Override
	public void update(float timeDelta) {

	}

	@Override
	public void render() {
		meshMain.render(renderPropertiesMain);
		meshRotorH.render(renderPropertiesRotorL);
		meshRotorL.render(renderPropertiesRotorH);
	}

}
