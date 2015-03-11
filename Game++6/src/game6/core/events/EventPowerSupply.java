package game6.core.events;

import game6.core.ai.goalfinding.Path;
import game6.core.buildings.CoreBuilding;
import game6.core.faction.Faction;
import game6.core.networking.packets.PacketPowerSupply;

import java.util.List;

public class EventPowerSupply extends Event {

	private CoreBuilding source;
	private Path path;
	private int amount;

	public EventPowerSupply(Faction faction, CoreBuilding source, Path path, int amount) {
		super(faction);
		this.source = source;
		this.path = path;
		this.amount = amount;
	}

	public CoreBuilding getSource() {
		return source;
	}

	public Path getPath() {
		return path;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "PowerSupplyEvent(source: " + source + ", path: " + path + ", amount: " + amount + ")";
	}

	@Override
	public void process(List<UpdateEventInterface> players) {
		Faction.broadcastAll(new PacketPowerSupply(this));
	}

}
