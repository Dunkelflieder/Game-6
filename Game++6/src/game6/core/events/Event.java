package game6.core.events;

import game6.core.faction.Faction;

import java.util.List;

public abstract class Event implements UpdateEvent {
	
	protected Faction faction;
	
	public Event(Faction faction) {
		this.faction = faction;
	}
	
	@Deprecated
	@Override
	public abstract void process(List<UpdateEventInterface> connections);

}
