package game6.core.world;

import game6.core.buildings.CoreBuilding;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.engine.BaseWorld;
import de.nerogar.engine.UpdateEvent;
import de.nerogar.util.Vector3f;

public class CoreWorld extends BaseWorld<Vector3f> {

	private List<CoreBuilding> buildings;
	private Map map;

	public CoreWorld(Map map) {
		super(new Vector3f());
		isStatic = true;
		setMap(map);
		this.buildings = new ArrayList<>();
	}

	@Override
	public List<UpdateEvent> update(float timeDelta) {
		List<UpdateEvent> events = super.update(timeDelta);
		for (CoreBuilding building : buildings) {
			building.update(events);
		}
		return events;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void addBuilding(int posX, int posY, CoreBuilding building) {
		building.setPosX(posX);
		building.setPosY(posY);
		building.setMap(map);
		this.buildings.add(building);
		this.buildings.sort((a, b) -> (int) (a.getID() - b.getID()));
		map.addBuilding(building);
	}

	public CoreBuilding getBuilding(long id) {

		int l = 0;
		int r = buildings.size() - 1;
		int p;
		while (l <= r) {
			p = (l + r) / 2;

			if (buildings.get(p).getID() == id)
				return buildings.get(p);
			if (buildings.get(p).getID() < id) {
				l = p + 1;
			} else {
				r = p - 1;
			}
		}

		return null;

	}

	public List<CoreBuilding> getBuildings() {
		return buildings;
	}

	@Override
	public void load() {
	}

	@Override
	public void save() {
	}

}
