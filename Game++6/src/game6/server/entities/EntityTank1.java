package game6.server.entities;

import game6.core.combat.MoveTargetCombat;
import game6.core.entities.CoreEntityTank1;
import game6.core.faction.Faction;
import game6.core.networking.packets.PacketAttackAnimation;
import game6.server.world.World;
import de.nerogar.util.Vector3f;

public class EntityTank1 extends CoreEntityTank1 implements ServerEntity, ServerEntityCombat {

	private DefaultServerEntityBehaviour defaultBehaviour = new DefaultServerEntityBehaviour();

	private int shootCooldown = 0;
	private int maxShootCooldown = 16;

	public EntityTank1() {
		super(DefaultServerEntityBehaviour.getNextID(), new Vector3f());
	}

	@Override
	public World getWorld() {
		return defaultBehaviour.getWorld();
	}

	@Override
	public void setWorld(World world) {
		defaultBehaviour.setWorld(world);
	}
	
	@Override
	public void update(float timeDelta) {
		super.update(timeDelta);

		shootCooldown--;

		if (isFighting()) {

			if (!reachesTarget()) {
				move(new MoveTargetCombat(this, getCombatTarget()));
			} else if (shootCooldown <= 0) {
				Faction.broadcastAll(new PacketAttackAnimation(getPosition(), getCombatTarget().getPosition()));
				setRotation(getCombatTarget().getPosition().subtracted(getPosition()));
				doDamage();

				shootCooldown = maxShootCooldown;
			} else {
				shootCooldown--;
			}

		}

	}
}
