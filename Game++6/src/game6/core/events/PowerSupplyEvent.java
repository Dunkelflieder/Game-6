package game6.core.events;

import java.util.List;

import game6.core.Faction;
import game6.core.networking.packets.PacketPowerSupply;
import game6.server.world.Player;

public class PowerSupplyEvent extends Event {

	private int sourceID, destID, amount;

	public PowerSupplyEvent(Faction faction, int sourceID, int destID, int amount) {
		super(faction);
		this.sourceID = sourceID;
		this.destID = destID;
		this.amount = amount;
	}

	public int getSourceID() {
		return sourceID;
	}

	public int getDestID() {
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
