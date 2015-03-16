package game6.core.entities;

import game6.core.faction.Faction;
import game6.core.interfaces.BoundingAABB;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.util.Vector3f;

public abstract class DefaultCoreEntity implements CoreEntity {

	private BoundingAABB bounding;
	private Vector3f position;
	private long id;
	private List<Vector3f> path;
	private float rotation;
	private float stopDistanceSquared;
	private boolean removalMarked = false;
	private Faction faction;
	private int health;
	private int maxHealth;

	public DefaultCoreEntity(long id, Vector3f position, BoundingAABB bounding, int maxHealth) {
		this.id = id;
		this.position = position;
		this.bounding = bounding;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		path = new ArrayList<>();
	}

	@Override
	public Vector3f getPosition() {
		return position;
	}

	@Override
	public BoundingAABB getBounding() {
		return bounding;
	}

	@Override
	public long getID() {
		return id;
	}

	@Override
	public List<Vector3f> getPath() {
		return path;
	}

	@Override
	public void setTarget(Vector3f target, float stopDistanceSquared) {
		setPath(getPathTo(target));
		this.stopDistanceSquared = stopDistanceSquared;
	}

	@Override
	public float getStopDistanceSquared() {
		return stopDistanceSquared;
	}

	@Override
	public void stop() {
		path.clear();
	}

	@Override
	public float getRotation() {
		return rotation;
	}

	@Override
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	@Override
	public void remove() {
		removalMarked = true;
	}

	@Override
	public boolean isRemovalMarked() {
		return removalMarked;
	}

	@Override
	public void setFaction(Faction faction) {
		this.faction = faction;
	}

	@Override
	public Faction getFaction() {
		return faction;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public int getMaxHealth() {
		return maxHealth;
	}

}
