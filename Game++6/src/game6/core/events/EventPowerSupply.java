package game6.core.events;

import java.util.List;

import game6.core.Faction;
import game6.core.networking.packets.PacketPowerSupply;
import game6.server.world.Player;

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
	public void process(List<Player> players) {
		for (Player p : players) {
			p.getConnection().send(new PacketPowerSupply(this));
		}
	}

}
