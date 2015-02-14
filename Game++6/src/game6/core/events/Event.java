package game6.core.events;

import game6.core.faction.Faction;
import game6.server.world.Player;

import java.util.List;

public abstract class Event {
	
	protected Faction faction;
	
	public Event(Faction faction) {
		this.faction = faction;
	}
	
	public abstract void process(List<Player> players);
	
}
