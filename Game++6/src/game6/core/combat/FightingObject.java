package game6.core.combat;

import de.nerogar.util.Vector3f;

public class FightingObject {

	private int maxHealth;
	private int health;
	private float reach;

	private Vector3f position;
	private FightingObject target;

	private DieEvent dieEvent;

	public FightingObject(int maxHealth, Vector3f position, DieEvent dieEvent) {
		this.health = maxHealth;
		this.position = position;
		this.dieEvent = dieEvent;
	}

	public void heal(int healAmount) {
		health += healAmount;

		if (health > maxHealth) health = maxHealth;
	}

	public void damage(int damage) {
		health -= damage;

		if (health <= 0) {
			dieEvent.onDie();
		}
	}

	public int getMaxhealth() {
		return maxHealth;
	}

	public int getHealth() {
		return health;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setTarget(FightingObject target) {
		this.target = target;
	}

	public FightingObject getTarget() {
		return target;
	}

	public void setReach(float reach){
		this.reach = reach;
	}
	
	public float getReach() {
		return reach;
	}

}
