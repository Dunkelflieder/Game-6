package game6.core.world;

import game6.core.ai.goalfinding.Goalfinder;
import game6.core.ai.goalfinding.Path;
import game6.core.buildings.CoreConstructionsite;
import game6.core.buildings.CoreBuilding;
import game6.core.entities.CoreEntity;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.engine.BaseWorld;
import de.nerogar.util.Vector3f;

public abstract class CoreWorld<T extends CoreBuilding> extends BaseWorld<Vector3f> {

	private List<T> buildings;
	private Map<T> map;
	private Goalfinder goalfinder;

	public CoreWorld(Map<T> map) {
		super(new Vector3f());
		isStatic = true;
		setMap(map);
		this.buildings = new ArrayList<>();
		this.goalfinder = new Goalfinder(this);
	}

	@Override
	public void update(float timeDelta) {
		super.update(timeDelta);

		for (T building : buildings) {
			building.update();
		}
	}

	public Map<T> getMap() {
		return map;
	}

	public void setMap(Map<T> map) {
		this.map = map;
	}

	public void spawnEntity(CoreEntity entity, Vector3f position) {
		entity.setMap(getMap());
		super.spawnEntity(entity, position);
	}

	public void addBuilding(int posX, int posY, T building) {
		building.setPosX(posX);
		building.setPosY(posY);
		this.buildings.add(building);
		this.buildings.sort((a, b) -> (int) (a.getID() - b.getID()));
		map.addBuilding(building);
	}

	public void finishConstructionsite(CoreConstructionsite<T> constructionsite) {
		map.finishConstructionsize(constructionsite);
		buildings.set(getBuildingArrayPosition(constructionsite.getID()), constructionsite.getBuilding());
	}

	public T getBuilding(long id) {

		int pos = getBuildingArrayPosition(id);
		if (pos < 0) {
			return null;
		}

		return buildings.get(pos);

	}

	private int getBuildingArrayPosition(long id) {

		int l = 0;
		int r = buildings.size() - 1;
		int p;
		while (l <= r) {
			p = (l + r) / 2;

			if (buildings.get(p).getID() == id) {
				return p;
			}
			if (buildings.get(p).getID() < id) {
				l = p + 1;
			} else {
				r = p - 1;
			}
		}

		return -1;

	}

	public List<T> getBuildings() {
		return buildings;
	}

	public List<Path> getEnergyGoals(T start) {
		return goalfinder.search(start);
	}

	@Override
	public void load() {
	}

	@Override
	public void save() {
	}

}
