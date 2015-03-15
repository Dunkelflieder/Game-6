package game6.client.world;

import game6.client.buildings.ClientBuilding;
import game6.client.effects.EffectContainer;
import game6.client.effects.SelectionMarker;
import game6.client.entities.ClientEntity;
import game6.core.buildings.CoreConstructionsite;
import game6.core.world.CoreWorld;
import game6.core.world.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import de.nerogar.render.RenderProperties3f;
import de.nerogar.render.Shader;
import de.nerogar.util.Color;

public class World extends CoreWorld<ClientBuilding, ClientEntity> {

	private MapMesh mesh;

	private ClientBuilding preview;

	private int renderCenterX, renderCenterY;
	private RenderProperties3f renderProperties;
	private Shader worldShader;

	private EffectContainer effectContainer;

	private ClientBuilding selectedBuilding;
	private ClientEntity selectedEntity;

	private SelectionMarker selectionMarker;

	private Minimap minimap;
	
	public World(EffectContainer effectContainer) {
		super(null);
		this.effectContainer = effectContainer;
		selectionMarker = new SelectionMarker(null, 0, 0);
		renderProperties = new RenderProperties3f();
		worldShader = new Shader("shaders/world/world.vert", "shaders/world/world.frag");
		minimap = new Minimap();
	}

	public EffectContainer getEffectContainer() {
		return effectContainer;
	}
	
	public ClientBuilding getSelectedBuilding() {
		return selectedBuilding;
	}

	public Minimap getMinimap() {
		return minimap;
	}

	public void selectBuilding(ClientBuilding building) {
		selectionMarker.kill();
		if (building != null) {
			selectionMarker = new SelectionMarker(building.getCenter().setY(0.1f), building.getSizeX(), building.getSizeY());
			effectContainer.addEffect(selectionMarker);
		}

		selectedBuilding = building;
		selectedEntity = null;
	}

	public ClientEntity getSelectedEntity() {
		return selectedEntity;
	}

	public void selectEntity(ClientEntity entity) {
		selectionMarker.kill();
		if (entity != null) {
			selectionMarker = new SelectionMarker(entity.getPosition(), 1.0f, 1.0f);
			effectContainer.addEffect(selectionMarker);
		}

		selectedEntity = entity;
		selectedBuilding = null;
	}

	@Override
	public void addBuilding(int posX, int posY, ClientBuilding building) {
		building.setWorld(this);
		super.addBuilding(posX, posY, building);
		mesh.reload(posX, posY, building.getSizeX(), building.getSizeY());
		minimap.update(posX, posY, building.getSizeX(), building.getSizeY());
	}

	@Override
	public void finishConstructionsite(CoreConstructionsite<ClientBuilding> constructionsite) {
		super.finishConstructionsite(constructionsite);
		if (selectedBuilding == constructionsite) {
			selectBuilding(null);
		}
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

	public void setBuildingPreview(ClientBuilding preview) {
		this.preview = preview;
	}

	@Override
	public void setMap(Map<ClientBuilding> map) {
		super.setMap(map);
		if (minimap != null) {
			minimap.setMap(map);
		}
		if (mesh != null) {
			mesh.cleanup();
		}
		if (map == null) {
			mesh = null;
		} else {
			mesh = new MapMesh(map);
		}
	}

	public void render(Shader shader) {
		worldShader.activate();

		// set texture positions
		worldShader.setUniform1i("colorTex", 0);
		worldShader.setUniform1i("ambientTex", 1);
		worldShader.setUniform1i("factionTex", 2);

		// TODO remove (debug)
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			worldShader.reloadFiles();
			worldShader.reCompile();
		}

		if (isLoaded()) {
			// render terrain

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
			Color factionColor = new Color(0);
			for (ClientBuilding building : getMap().getBuildingsWithin(renderCenterX, renderCenterY, 160)) {

				if (building.getFaction() != null) {
					factionColor = building.getFaction().color;
				}
				worldShader.setUniform4f("factionColor", factionColor.getR(), factionColor.getG(), factionColor.getB(), factionColor.getA());
				building.render(worldShader);
			}

			for (ClientEntity entity : getEntities()) {
				if (entity.getFaction() != null) {
					factionColor = entity.getFaction().color;
				}
				worldShader.setUniform4f("factionColor", factionColor.getR(), factionColor.getG(), factionColor.getB(), factionColor.getA());
				entity.render(worldShader);
			}

		}

		worldShader.deactivate();
	}

	public boolean isLoaded() {
		return getMap() != null;
	}
	
	@Override
	public void addEntity(ClientEntity entity) {
		entity.setWorld(this);
		super.addEntity(entity);
	}

}
