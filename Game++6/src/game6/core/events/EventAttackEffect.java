package game6.core.events;

import de.nerogar.engine.UpdateEvent;
import game6.core.combat.FightingObject;
import game6.core.faction.Faction;
import game6.core.networking.packets.PacketAttackEffect;

import java.util.List;

import de.nerogar.engine.UpdateEventInterface;

public class EventAttackEffect implements UpdateEvent {

	private FightingObject sourceObject;
	private FightingObject targetObject;

	public EventAttackEffect(FightingObject sourceObject, FightingObject targetObject) {
		this.sourceObject = sourceObject;
		this.targetObject = targetObject;

	}

	@Override
	public void process(List<UpdateEventInterface> players) {
		Faction.broadcastAll(new PacketAttackEffect(sourceObject.getPosition(), targetObject.getPosition()));
	}

}
