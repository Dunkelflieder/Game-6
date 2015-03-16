package game6.core.interfaces;

public interface IHealth {

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
		if (health == 0) {
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
		return getHealth() <= 0;
	}

}
