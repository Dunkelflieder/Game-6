package game6.client.world;

import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.opengl.GL11;

import game6.core.buildings.CoreBuilding;
import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.render.RenderProperties;
import de.nerogar.render.Shader;
import de.nerogar.util.Vector3f;

public class MapEntity extends BaseEntity {

	private Map map;
	private MapMesh mesh;
	private MapGridMesh gridMesh;
	private RenderProperties renderProperties;
	private boolean gridActivated = false;
	private CoreBuilding preview;

	private Shader shader;

	public MapEntity(Map map) {
		super(new BoundingAABB(new Vector3f(-99999), new Vector3f(99999, 0, 99999)), new Vector3f(0));
		this.map = map;
		mesh = new MapMesh(map);
		gridMesh = new MapGridMesh(map);
		renderProperties = new RenderProperties();

		shader = new Shader("shaders/world.vert", "shaders/world.frag");
	}

	public boolean isGridActivated() {
		return gridActivated;
	}

	public void setGridActivated(boolean is) {
		gridActivated = is;
	}

	public void setBuildingPreview(CoreBuilding preview) {
		this.preview = preview;
	}

	public void reloadMesh() {
		mesh.reload();
		gridMesh.reload();
	}

	@Override
	public void update(float timeDelta) {
	}

	@Override
	public void render() {
		mesh.render(renderProperties);
		if (gridActivated) {
			// TODO Don't hardcode OpenGL here. This is to ensure that the grid overlays the terrain.
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			gridMesh.render(renderProperties);
		}

		if (preview != null) {
			if (map.canAddBuilding(preview.getPosX(), preview.getPosY(), preview)) {
				GL11.glColor4f(0, 1, 0, 1);
			} else {
				GL11.glColor4f(1, 0, 0, 1);
			}
			preview.render();
			GL11.glColor4f(1, 1, 1, 1);
		}

		shader.activate();

		glUniform1i(glGetUniformLocation(shader.shaderHandle, "colorTex"), 0);
		glUniform1i(glGetUniformLocation(shader.shaderHandle, "factionTex"), 1);

		for (CoreBuilding building : map.getBuildings()) {
			building.render();
		}

		shader.deactivate();
	}

}
