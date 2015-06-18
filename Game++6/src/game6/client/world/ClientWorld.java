package game6.client.world;

import game6.client.buildings.ClientBuilding;
import game6.client.effects.*;
import game6.client.entities.ClientEntity;
import game6.client.gui.GuiIngame;
import game6.core.buildings.CoreConstructionsite;
import game6.core.world.CoreWorld;
import game6.core.world.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import de.nerogar.render.*;
import de.nerogar.util.Color;

public class ClientWorld extends CoreWorld<ClientBuilding, ClientEntity> {

	private MapMesh mesh;

	private ClientBuilding preview;

	private int renderCenterX, renderCenterY;
	private RenderProperties3f renderProperties;
	private Shader worldShader;

	private EffectContainer effectContainer;
	private LightContainer lightContainer;

	private ClientBuilding selectedBuilding;
	private ClientEntity selectedEntity;

	private SelectionMarker selectionMarker;

	private Minimap minimap;

	// TODO testing
	private Texture2D skyTex;

	public ClientWorld(EffectContainer effectContainer) {
		super(null);
		this.effectContainer = effectContainer;
		selectionMarker = new SelectionMarker(null, 0, 0);
		renderProperties = new RenderProperties3f();
		worldShader = new Shader("shaders/world/world.vert", "shaders/world/world.frag");
		minimap = new Minimap();
		skyTex = Texture2DLoader.loadTexture("res/border.png");
	}

	public EffectContainer getEffectContainer() {
		return effectContainer;
	}

	public LightContainer getLightContainer() {
		return lightContainer;
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
			selectionMarker = new SelectionMarker(building.getCenterPosition().setY(0.1f), building.getSizeX(), building.getSizeY());
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
			GuiIngame.instance.reset();
		}
	}

	public void render(Shader shader) {

		if (!isLoaded()) { return; }

		worldShader.activate();

		worldShader.setUniform1bool("renderFactionObject", false);

		skyTex.bind();
		GL11.glColor4f(1f, 1f, 1f, 1f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(-300, -0.1f, -300);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(getMap().getSizeX() + 300, -0.1f, -300);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(getMap().getSizeX() + 300, -0.1f, getMap().getSizeY() + 300);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(-300, -0.1f, getMap().getSizeY() + 300);
		GL11.glEnd();

		// set texture positions
		worldShader.setUniform1i("colorTex", 0);
		worldShader.setUniform1i("ambientTex", 1);
		worldShader.setUniform1i("factionTex", 2);

		// TODO remove (debug)
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			worldShader.reloadFiles();
			worldShader.reCompile();
		}

		// render terrain

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

	@Override
	public void removeBuilding(ClientBuilding building) {
		super.removeBuilding(building);
		mesh.reload(building.getPosX(), building.getPosY(), building.getSizeX(), building.getSizeY());
	}

}
