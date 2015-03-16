package game6.core.interfaces;

public interface ICombat {

	public float getReach();

	public int getDamage();

	public ICombatTarget getCombatTarget();

	public void setCombatTarget(ICombatTarget target);

	default public boolean isFighting() {
		return getCombatTarget() != null;
	}

	default public void doDamage() {
		if (isFighting()) {
			getCombatTarget().damage(getDamage());
			if (getCombatTarget().isDead()) {
				setCombatTarget(null);
			}
		}
	}

}
