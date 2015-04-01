package game6.core.entities;

import game6.core.combat.ICombat;
import game6.core.combat.ICombatTarget;
import game6.core.interfaces.*;
import de.nerogar.util.Vector3f;

public abstract class CoreEntityHelicopter1 extends DefaultCoreEntity implements MovementAir, ICombat {

	private ICombatTarget combatTarget;

	public CoreEntityHelicopter1(long id, Vector3f position) {
		super(id, position, new BoundingAABB(-0.3f, 0f, -0.3f, 0.3f, 0.3f, 0.3f), 5, 100);
	}

	@Override
	public String getName() {
		return "Helicopter";
	}

	@Override
	public ICombatTarget getCombatTarget() {
		return combatTarget;
	}

	@Override
	public void setCombatTarget(ICombatTarget target) {
		this.combatTarget = target;
	}

	@Override
	public float getReach() {
		return 10;
	}

	@Override
	public int getDamage() {
		return 10;
	}

}
