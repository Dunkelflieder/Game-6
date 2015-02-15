package game6.core.entities;

import game6.core.faction.Faction;
import game6.core.world.Map;
import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.util.Vector3f;

public abstract class CoreEntity extends BaseEntity<Vector3f> {

	private Map map;
	private Faction faction;

	public CoreEntity(long id, BoundingAABB<Vector3f> bounding, Vector3f position) {
		super(id, bounding, position);
	}

	public void setMap(Map map) {
		this.map = map;
	}
	
	public Map getMap() {
		return map;
	}
	
	public void setFaction(Faction faction) {
		this.faction = faction;
	}

	public Faction getFaction() {
		return faction;
	}
	
}
