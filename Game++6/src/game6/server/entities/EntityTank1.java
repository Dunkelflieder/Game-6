package game6.server.entities;

import java.util.List;

import game6.core.combat.FightingObject;
import game6.core.entities.CoreEntityTank1;
import game6.core.events.EventAttackEffect;
import de.nerogar.engine.UpdateEvent;
import de.nerogar.render.Shader;
import de.nerogar.util.Vector3f;

public class EntityTank1 extends CoreEntityTank1 {

	public EntityTank1() {
		super(getNextID(), new Vector3f());

		getFightingObject().setAttackEvent(this::onAttack);
	}

	private void onAttack(List<UpdateEvent> events, FightingObject target) {
		if (tick % 10 == 0) {
			target.damage(10);
			events.add(new EventAttackEffect(getFightingObject(), target));
		}
	}

	@Override
	public void render(Shader shader) {
	}

}
