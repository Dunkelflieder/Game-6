package game6.core.events;

import java.util.List;

import game6.core.faction.Faction;
import de.nerogar.engine.UpdateEvent;
import de.nerogar.engine.UpdateEventInterface;

public abstract class Event implements UpdateEvent {
	
	protected Faction faction;
	
	public Event(Faction faction) {
		this.faction = faction;
	}
	
	@Deprecated
	@Override
	public abstract void process(List<UpdateEventInterface> connections);

}
