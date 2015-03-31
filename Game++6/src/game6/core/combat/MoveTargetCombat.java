package game6.core.combat;

import game6.core.entities.MoveTarget;
import de.nerogar.util.Vector3f;

public class MoveTargetCombat implements MoveTarget {

	private ICombat attacker;
	private ICombatTarget target;

	public MoveTargetCombat(ICombat attacker, ICombatTarget target) {
		this.attacker = attacker;
		this.target = target;
	}

	@Override
	public boolean isReached() {
		return attacker.hasApproachDistance();
	}

	@Override
	public Vector3f getMovePosition() {
		return target.getPosition();
	}

}
