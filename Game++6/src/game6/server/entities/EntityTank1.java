package game6.server.entities;

import game6.client.entities.guis.EntityGui;
import game6.core.combat.FightingObject;
import game6.core.entities.CoreEntity;
import game6.core.entities.CoreEntityTank1;
import game6.core.faction.Faction;
import game6.core.networking.packets.PacketAttackEffect;
import de.nerogar.render.Shader;
import de.nerogar.util.Vector3f;

public class EntityTank1 extends CoreEntityTank1 {

	public EntityTank1() {
		super(getNextID(), new Vector3f());

		getFightingObject().setAttackEvent(this::onAttack);
	}

	private void onAttack(FightingObject target) {
		if (tick % 10 == 0) {
			target.damage(8);
			Faction.broadcastAll(new PacketAttackEffect(getFightingObject().getPosition(), target.getPosition()));
		}
	}

	@Override
	public void render(Shader shader) {
	}

	@Override
	public EntityGui<CoreEntity> getGui() {
		return null;
	}

}
