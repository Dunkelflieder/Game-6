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
	private float speed;
	private List<Vector3f> movePath;
	private MoveTarget moveTarget;
	private float rotation;
	private boolean removalMarked = false;
	private Faction faction;
	private int health;
	private int maxHealth;

	public DefaultCoreEntity(long id, Vector3f position, BoundingAABB bounding, float speed, int maxHealth) {
		this.id = id;
		this.position = position;
		this.bounding = bounding;
		this.speed = speed;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.movePath = new ArrayList<>();
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
	public List<Vector3f> getMovementPath() {
		return movePath;
	}

	@Override
	public void setMoveTarget(MoveTarget target) {
		moveTarget = target;
	}
	
	@Override
	public MoveTarget getMoveTarget() {
		return moveTarget;
	}

	@Override
	public float getRotation() {
		return rotation;
	}

	@Override
	public void setRotation(float rotation) {
		this.rotation = rotation;
		rotationChanged();
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
	public float getSpeed() {
		return speed;
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
