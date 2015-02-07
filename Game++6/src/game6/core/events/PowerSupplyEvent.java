package game6.core.events;

import java.util.List;

import game6.core.networking.ServerThread;
import game6.core.networking.packets.PacketPowerSupply;
import game6.core.world.CoreMap;

public class PowerSupplyEvent implements Event {

	private int sourceID, destID, amount;

	public PowerSupplyEvent(int sourceID, int destID, int amount) {
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
	public void doNetwork(List<Event> events, ServerThread serverThread) {
		serverThread.broadcast(new PacketPowerSupply(this));
	}

	@Override
	public void doMap(List<Event> events, CoreMap map) {
	}

	@Override
	public String toString() {
		return "PowerSupplyEvent(source: " + sourceID + ", dest: " + destID + ", amount: " + amount + ")";
	}

}
