package game6.core.interfaces;

public interface ICombat extends IPosition {

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

	default public boolean reachesTarget() {
		if (!isFighting()) {
			return false;
		}
		return getPosition().subtracted(getCombatTarget().getPosition()).getSquaredValue() <= getReach() * getReach();
	}

}
