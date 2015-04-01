package game6.core.combat;

import game6.core.entities.Movement;
import game6.core.interfaces.IPosition;

public interface ICombat extends IPosition {

	public float getReach();

	public int getDamage();

	public ICombatTarget getCombatTarget();

	void setCombatTarget(ICombatTarget target);

	default public void attack(ICombatTarget target) {
		setCombatTarget(target);
	}
	
	default public void stopCombat() {
		setCombatTarget(null);
	}
	
	default public boolean isFighting() {
		return getCombatTarget() != null;
	}

	default public void doDamage() {
		if (!isFighting()) {
			return;
		}
		getCombatTarget().damage(getDamage());
		notifyDamage();
		if (getCombatTarget().isDead()) {
			stopCombat();
			if (this instanceof Movement) {
				// TODO refactor and dont do this here
				((Movement) this).stopMovement();
			}
		}
	}
	
	default public void notifyDamage() {
	}

	default public boolean reachesTarget() {
		if (!isFighting()) {
			return false;
		}
		return getPosition().subtracted(getCombatTarget().getPosition()).getSquaredValue() <= getReach() * getReach();
	}
	
	default public boolean hasApproachDistance() {
		if (!isFighting()) {
			return false;
		}
		return getPosition().subtracted(getCombatTarget().getPosition()).getSquaredValue() * 1.5 <= getReach() * getReach();
	}

}
