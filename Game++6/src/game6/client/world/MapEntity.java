package game6.client.world;

import game6.core.buildings.CoreBuilding;

import org.lwjgl.opengl.GL11;

import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.render.RenderProperties;
import de.nerogar.render.Shader;
import de.nerogar.util.Color;
import de.nerogar.util.Vector3f;

public class MapEntity extends BaseEntity<Vector3f> {

	private Map map;
	private MapMesh mesh;
	private RenderProperties renderProperties;
	private CoreBuilding preview;

	private int renderCenterX, renderCenterY;

	private Shader shader;

	public MapEntity(Map map) {
		super(new BoundingAABB<Vector3f>(new Vector3f(-99999), new Vector3f(99999, 0, 99999)), new Vector3f(0));
		this.map = map;
		mesh = new MapMesh(map);
		renderProperties = new RenderProperties();

		shader = new Shader("shaders/world.vert", "shaders/world.frag");
	}

	public void setCenterOfRendering(int x, int z) {
		this.renderCenterX = x;
		this.renderCenterY = z;
	}

	public boolean isGridActivated() {
		return mesh.isGridActivated();
	}

	public void setGridActivated(boolean is) {
		mesh.setGridActivated(is);
	}

	public void setBuildingPreview(CoreBuilding preview) {
		this.preview = preview;
	}

	public void reloadMesh(int posX, int posY, int sizeX, int sizeY) {
		mesh.reload(posX, posY, sizeX, sizeY);
	}

	@Override
	public void update(float timeDelta) {
	}

	@Override
	public void render() {
		mesh.render(renderProperties, renderCenterX, renderCenterY);

		if (preview != null) {
			if (map.canAddBuilding(preview.getPosX(), preview.getPosY(), preview)) {
				GL11.glColor4f(0, 1, 0, 1);
			} else {
				GL11.glColor4f(1, 0, 0, 1);
			}
			preview.render();
			GL11.glColor4f(1, 1, 1, 1);
		}

		// render buildings
		shader.activate();

		shader.setUniform1i("lightTex", 0);
		shader.setUniform1i("colorTex", 1);
		shader.setUniform1i("factionTex", 2);

		for (CoreBuilding building: map.getBuildingsWithin(renderCenterX, renderCenterY, 150)) {
			Color factionColor = new Color(0);
			if (building.getFaction() != null) {
				factionColor = building.getFaction().color;
			}
			shader.setUniform4f("factionColor", factionColor.getR(), factionColor.getG(), factionColor.getB(), factionColor.getA());
			building.render();
		}
		
		shader.deactivate();
	}

}
