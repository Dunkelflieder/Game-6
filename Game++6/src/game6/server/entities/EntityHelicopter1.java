package game6.server.entities;

import game6.core.combat.MoveTargetCombat;
import game6.core.entities.CoreEntityHelicopter1;
import game6.core.faction.Faction;
import game6.core.networking.packets.PacketAttackAnimation;
import game6.core.util.Resource;
import game6.server.world.World;
import de.nerogar.util.Vector3f;

public class EntityHelicopter1 extends CoreEntityHelicopter1 implements ServerEntity, ServerEntityInventory, ServerEntityCombat {

	private int shootCooldown = 0;
	private int maxShootCooldown = 2;
	private int volleyCounter = 0;
	private int volleySize = 3;
	private int volleyCooldown = 0;
	private int maxVolleyCooldown = 8;

	private DefaultServerEntityBehaviour defaultBehaviour = new DefaultServerEntityBehaviour();

	public EntityHelicopter1() {
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
			} else if (volleyCooldown <= 0 && volleyCounter > 0 && shootCooldown <= 0) {
				Faction.broadcastAll(new PacketAttackAnimation(getPosition(), getCombatTarget().getPosition()));
				setRotation(getCombatTarget().getPosition().subtracted(getPosition()));
				doDamage();

				shootCooldown = maxShootCooldown;
				volleyCounter--;

			} else if (volleyCooldown <= 0 && volleyCounter > 0) {
				shootCooldown--;
			} else if (volleyCooldown <= 0 && volleyCounter <= 0) {
				volleyCounter = volleySize;
				volleyCooldown = maxVolleyCooldown;
			} else {
				volleyCooldown--;
			}

		}

		addResource(Resource.getRandom(), 1);

	}

}
