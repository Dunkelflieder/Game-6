package game6.core.combat;

import java.util.List;

import de.nerogar.engine.UpdateEvent;

public interface AttackEvent {

	public void onAttack(List<UpdateEvent> events, FightingObject target);
	
}
