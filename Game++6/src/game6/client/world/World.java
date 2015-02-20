package game6.client.world;

import game6.core.buildings.CoreBuilding;
import game6.core.entities.CoreEntity;
import game6.core.world.CoreWorld;
import game6.core.world.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import de.nerogar.render.RenderProperties3f;
import de.nerogar.render.Shader;
import de.nerogar.util.Color;

public class World extends CoreWorld {

	private MapMesh mesh;

	private CoreBuilding preview;

	private int renderCenterX, renderCenterY;
	private RenderProperties3f renderProperties;
	private Shader worldShader;
	
	private CoreBuilding selectedBuilding;
	private CoreEntity selectedEntity;

	public World() {
		super(null);
		renderProperties = new RenderProperties3f();
		worldShader = new Shader("shaders/world.vert", "shaders/world.frag");
	}
	
	public CoreBuilding getSelectedBuilding() {
		return selectedBuilding;
	}
	
	public void selectBuilding(CoreBuilding building) {
		selectedBuilding = building;
		selectedEntity = null;
	}

	public CoreEntity getSelectedEntity() {
		return selectedEntity;
	}
	
	public void selectEntity(CoreEntity entity) {
		selectedEntity = entity;
		selectedBuilding = null;
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

		//set texture positions
		worldShader.setUniform1i("colorTex", 0);
		worldShader.setUniform1i("ambientTex", 1);
		worldShader.setUniform1i("factionTex", 2);

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

			// TODO highlight selectedBuilding somehow
			for (CoreBuilding building : getMap().getBuildingsWithin(renderCenterX, renderCenterY, 160)) {
				Color factionColor = new Color(0);
				if (building.getFaction() != null) {
					factionColor = building.getFaction().color;
				}
				worldShader.setUniform4f("factionColor", factionColor.getR(), factionColor.getG(), factionColor.getB(), factionColor.getA());
				building.render(worldShader);
			}

		}
		
		// TODO highlight selectedEntity somehow

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
