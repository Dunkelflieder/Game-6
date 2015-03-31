package game6.core.entities;

import game6.core.combat.ICombat;
import game6.core.combat.ICombatTarget;
import game6.core.interfaces.BoundingAABB;
import de.nerogar.util.Vector3f;

public abstract class CoreEntityTank1 extends DefaultCoreEntity implements MovementGround, ICombat {

	private ICombatTarget combatTarget;
	
	public CoreEntityTank1(long id, Vector3f position) {
		super(id, position, new BoundingAABB(-0.3f, 0f, -0.3f, 0.3f, 0.3f, 0.3f), 2, 500);
	}
	
	@Override
	public String getName() {
		return "Panzer";
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
		return 16;
	}

	@Override
	public int getDamage() {
		return 50;
	}

}
