package game6.core.events;

import game6.core.faction.Faction;
import de.nerogar.engine.UpdateEvent;

public abstract class Event implements UpdateEvent {
	
	protected Faction faction;
	
	public Event(Faction faction) {
		this.faction = faction;
	}

}
