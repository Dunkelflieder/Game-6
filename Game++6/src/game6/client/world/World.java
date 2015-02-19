package game6.client.world;

import game6.core.buildings.CoreBuilding;
import game6.core.world.CoreWorld;
import game6.core.world.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import de.nerogar.render.*;
import de.nerogar.util.Color;

public class World extends CoreWorld {

	private MapMesh mesh;

	private CoreBuilding preview;

	private int renderCenterX, renderCenterY;
	private RenderProperties3f renderProperties;
	private Shader worldShader;

	public World() {
		super(null);
		renderProperties = new RenderProperties3f();
		worldShader = new Shader("shaders/world.vert", "shaders/world.frag");
	}

	@Override
	public void addBuilding(int posX, int posY, CoreBuilding building) {
		super.addBuilding(posX, posY, building);
		mesh.reload(posX, posY, building.getSizeX(), building.getSizeY());
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

	@Override
	public void setMap(Map map) {
		super.setMap(map);
		if (mesh != null) {
			mesh.cleanup();
		}
		if (map == null) {
			mesh = null;
		} else {
			mesh = new MapMesh(map);
		}
	}

	public void unloadMap() {
		setMap(null);
	}

	@Override
	public void render(Shader shader) {
		worldShader.activate();

		//TODO remove (debug)
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			worldShader.reloadFiles();
			worldShader.reCompile();
		}

		if (isLoaded()) {
			//render terrain

			worldShader.setUniform1bool("renderFactionObject", false);
			worldShader.setUniformMat4f("modelMatrix", renderProperties.getModelMatrix().asBuffer());
			mesh.render(renderCenterX, renderCenterY);

			worldShader.setUniform1bool("renderFactionObject", true);
			if (preview != null) {
				if (getMap().canAddBuilding(preview.getPosX(), preview.getPosY(), preview)) {
					worldShader.setUniform4f("factionColor", 0.0f, 1.0f, 0.0f, 1.0f);
					GL11.glColor4f(0, 1, 0, 1);
				} else {
					worldShader.setUniform4f("factionColor", 1.0f, 0.0f, 0.0f, 1.0f);
					GL11.glColor4f(1, 0, 0, 1);
				}
				preview.render(worldShader);
				GL11.glColor4f(1, 1, 1, 1);
			}

			// render buildings
			worldShader.setUniform1i("lightTex", 0);
			worldShader.setUniform1i("colorTex", 1);
			worldShader.setUniform1i("factionTex", 2);

			for (CoreBuilding building : getMap().getBuildingsWithin(renderCenterX, renderCenterY, 160)) {
				Color factionColor = new Color(0);
				if (building.getFaction() != null) {
					factionColor = building.getFaction().color;
				}
				worldShader.setUniform4f("factionColor", factionColor.getR(), factionColor.getG(), factionColor.getB(), factionColor.getA());
				building.render(worldShader);
			}

		}

		super.render(worldShader);

		worldShader.deactivate();
	}

	@Override
	public void load() {

	}

	@Override
	public void save() {

	}

	public boolean isLoaded() {
		return getMap() != null;
	}

	public void cleanup() {
		unloadMap();
		getBuildings().clear();
		getEntityList().clear();
	}

}
