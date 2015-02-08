package game6.client.world;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import game6.core.buildings.CoreBuilding;
import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.render.RenderProperties;
import de.nerogar.render.Shader;
import de.nerogar.util.Vector3f;

public class MapEntity extends BaseEntity {

	private Map map;
	private MapMesh mesh;
	private RenderProperties renderProperties;

	private Shader shader;

	public MapEntity(Map map) {
		super(new BoundingAABB(new Vector3f(-99999), new Vector3f(99999, 0, 99999)), new Vector3f(0));
		this.map = map;
		mesh = new MapMesh(map);
		renderProperties = new RenderProperties();

		shader = new Shader("shaders/world.vert", "shaders/world.frag");
	}

	public void reloadMesh() {
		mesh.reload();
	}

	@Override
	public void update(float timeDelta) {
	}

	@Override
	public void render() {
		mesh.render(renderProperties);

		shader.activate();

		glUniform1i(glGetUniformLocation(shader.shaderHandle, "colorTex"), 0);
		glUniform1i(glGetUniformLocation(shader.shaderHandle, "factionTex"), 1);

		for (CoreBuilding building : map.getBuildings()) {
			building.render();
		}

		shader.deactivate();
	}

}
