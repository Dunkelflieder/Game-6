package game6.server.entities;

import game6.core.entities.CoreEntityTank1;
import game6.server.world.World;
import de.nerogar.util.Vector3f;

public class EntityTank1 extends CoreEntityTank1 implements ServerEntity {

	private DefaultServerEntityBehaviour defaultBehaviour = new DefaultServerEntityBehaviour();

	public EntityTank1() {
		super(DefaultServerEntityBehaviour.getNextID(), new Vector3f());

		// TODO enable fighting again
		// getFightingObject().setAttackEvent(this::onAttack);
	}

	/*private void onAttack(FightingObject target) {
		if (tick % 10 == 0) {
			target.damage(8);
			Faction.broadcastAll(new PacketAttackEffect(getFightingObject().getPosition(), target.getPosition()));
		}
	}*/

	@Override
	public World getWorld() {
		return defaultBehaviour.getWorld();
	}

	@Override
	public void setWorld(World world) {
		defaultBehaviour.setWorld(world);
	}
}
