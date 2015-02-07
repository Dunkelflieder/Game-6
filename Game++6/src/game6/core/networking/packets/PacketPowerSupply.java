package game6.core.networking.packets;

import game6.core.events.PowerSupplyEvent;
import de.felk.NodeFile.NodeFile;

public class PacketPowerSupply extends Packet {

	public PowerSupplyEvent event;

	public PacketPowerSupply() {
		
	}
	
	public PacketPowerSupply(PowerSupplyEvent event) {
		this.event = event;
	}

	@Override
	public NodeFile toNode() {
		NodeFile node = new NodeFile();

		node.add('s', event.getSourceID());
		node.add('d', event.getDestID());
		node.add('a', event.getAmount());

		return node;
	}

	@Override
	public void loadNode(NodeFile node) {

		event = new PowerSupplyEvent(node.getInt('s'), node.getInt('d'), node.getInt('a'));

	}

}
