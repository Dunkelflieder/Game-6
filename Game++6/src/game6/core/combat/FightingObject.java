package game6.core.combat;

import game6.core.interfaces.ICombat;
import game6.core.interfaces.IHealth;
import de.nerogar.util.Vector3f;

public abstract class FightingObject implements IHealth, ICombat {

	private Vector3f position;
	private FightingObject target;

	private AttackEvent attackEvent;

	public FightingObject(int maxHealth, Vector3f position) {
		this.position = position;
	}

	public void update() {
		int reach = 0; // get rid of compile error
		if (attackEvent != null && target != null && target.getPosition().subtracted(getPosition()).getSquaredValue() <= reach * reach) {
			if (target.isDead()) target = null;
			attackEvent.onAttack(target);
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setTarget(FightingObject target) {
		this.target = target;
	}

	public void setAttackEvent(AttackEvent attackEvent) {
		this.attackEvent = attackEvent;
	}

	public AttackEvent getAttackEvent() {
		return attackEvent;
	}

}
