package game6.core.events;

import game6.core.faction.Faction;
import game6.core.networking.packets.PacketPowerSupply;
import game6.server.world.Player;

import java.util.List;

import de.nerogar.engine.UpdateEventInterface;

public class EventPowerSupply extends Event {

	private long sourceID, destID;
	private int amount;

	public EventPowerSupply(Faction faction, long sourceID, long destID, int amount) {
		super(faction);
		this.sourceID = sourceID;
		this.destID = destID;
		this.amount = amount;
	}

	public long getSourceID() {
		return sourceID;
	}

	public long getDestID() {
		return destID;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "PowerSupplyEvent(source: " + sourceID + ", dest: " + destID + ", amount: " + amount + ")";
	}

	@Override
	public void process(List<UpdateEventInterface> players) {
		for (UpdateEventInterface i : players) {
			Player p = (Player) i;
			p.getConnection().send(new PacketPowerSupply(this));
		}
	}

}
