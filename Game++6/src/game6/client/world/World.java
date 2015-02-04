package game6.client.world;

import de.nerogar.engine.BaseWorld;
import de.nerogar.util.Vector3f;

public class World extends BaseWorld {

	private Map map;

	public World() {
		super(new Vector3f());
		isStatic = true;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		if (this.map != null) {
			this.map.removeFromWorld();
		}
		this.map = map;
		spawnEntity(map, new Vector3f());
	}

	@Override
	public void load() {

	}

	@Override
	public void save() {

	}

	public boolean isReady() {
		return map != null;
	}

}
