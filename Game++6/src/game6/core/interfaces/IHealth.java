package game6.core.interfaces;

import game6.core.world.IDList.UniqueID;
import game6.core.world.Removable;

public interface IHealth extends UniqueID, Removable {

	public int getHealth();

	public void setHealth(int health);

	public int getMaxHealth();

	public void kill();

	default public void damage(int damage) {
		int health = getHealth() - damage;
		if (health < 0) {
			health = 0;
		}
		setHealth(health);
		if (isDead()) {
			kill();
		}
	}

	default public void heal(int heal) {
		int health = getHealth() + heal;
		if (health > getMaxHealth()) {
			health = getMaxHealth();
		}
		setHealth(health);
	}

	default public boolean isDead() {
		return getHealth() <= 0 || isRemovalMarked();
	}

}
