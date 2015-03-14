package game6.core.world;

import game6.core.ai.goalfinding.Goalfinder;
import game6.core.ai.goalfinding.Path;
import game6.core.buildings.CoreBuilding;
import game6.core.buildings.CoreConstructionsite;
import game6.core.engine.IDList;
import game6.core.entities.CoreEntity;

import java.util.Iterator;
import java.util.List;

public abstract class CoreWorld<B extends CoreBuilding, E extends CoreEntity> implements Updateable {

	private Map<B> map;
	private Goalfinder goalfinder;

	private IDList<E> entities;
	private IDList<B> buildings;
	
	public CoreWorld(Map<B> map) {
		setMap(map);
		this.goalfinder = new Goalfinder(this);
		
		buildings = new IDList<>();
		entities = new IDList<>();
	}

	@Override
	public void update(float timeDelta) {
		
		for (Iterator<E> iter = entities.iterator(); iter.hasNext();) {
			E entity = iter.next();
			if (entity.isRemovalMarked()) {
				iter.remove();
			} else {
				entity.update(timeDelta);
			}
		}
		
		for (B building : buildings) {
			building.update();
		}
	}
	
	public void addEntity(E entity) {
		entities.add(entity);
	}
	
	public Map<B> getMap() {
		return map;
	}

	public void setMap(Map<B> map) {
		this.map = map;
	}

	public void addBuilding(int posX, int posY, B building) {
		building.setPosX(posX);
		building.setPosY(posY);
		buildings.add(building);
		map.addBuilding(building);
	}

	public void finishConstructionsite(CoreConstructionsite<B> constructionsite) {
		map.finishConstructionsize(constructionsite);
		buildings.replace(constructionsite.getID(), constructionsite.getBuilding());
	}

	public B getBuilding(long id) {
		return buildings.get(id);
	}

	public IDList<B> getBuildings() {
		return buildings;
	}
	
	public E getEntity(long id) {
		return entities.get(id);
	}
	
	public IDList<E> getEntities() {
		return entities;
	}

	public List<Path> getEnergyGoals(B start) {
		return goalfinder.search(start);
	}

	public void unloadMap() {
		setMap(null);
	}
	
	public void cleanup() {
		unloadMap();
		entities.clear();
		buildings.clear();
	}
	
}
