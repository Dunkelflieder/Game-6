package game6.client.world;

import game6.client.buildings.BaseBuilding;
import game6.core.world.CoreMap;
import game6.core.world.Tile;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.render.RenderProperties;
import de.nerogar.util.Vector3f;

public class Map extends BaseEntity {

	private CoreMap core;
	private MapMesh terrainMesh;
	private RenderProperties renderProperties;
	private List<BaseBuilding> buildings;

	public Map(CoreMap core) {
		super(new BoundingAABB(new Vector3f(0, -10, 0), new Vector3f(core.getSizeX(), 0, core.getSizeY())), new Vector3f());
		this.core = core;
		this.terrainMesh = new MapMesh(core);
		buildings = new ArrayList<>();
		renderProperties = new RenderProperties();
	}

	public boolean canAddBuilding(int posX, int posY, BaseBuilding building) {
		return core.canAddBuilding(posX, posY, building.getCore());
	}

	public void addBuilding(int posX, int posY, BaseBuilding building) {
		building.setPosX(posX);
		building.setPosY(posY);
		buildings.add(building);
		core.addBuilding(posX, posY, building.getCore());
		terrainMesh.reload();
	}

	public int getSizeX() {
		return core.getSizeX();
	}

	public int getSizeY() {
		return core.getSizeY();
	}

	public Tile getTile(int x, int y) {
		return core.getTile(x, y);
	}

	public void setTile(int x, int y, Tile tile) {
		core.setTile(x, y, tile);
	}

	@Override
	public void update(float timeDelta) {
	}

	@Override
	public void render() {
		terrainMesh.render(renderProperties);
		for (BaseBuilding building : buildings) {
			building.render();
		}
	}

}
