package game6.core.networking.packets;

import game6.core.events.EventPowerSupply;
import de.felk.NodeFile.NodeFile;

public class PacketPowerSupply extends Packet {

	public long source, destination;
	private int amount;

	public PacketPowerSupply() {
		
	}
	
	public PacketPowerSupply(EventPowerSupply event) {
		this.source = event.getSourceID();
		this.destination = event.getDestID();
		this.amount = event.getAmount();
	}

	@Override
	public NodeFile toNode() {
		NodeFile node = new NodeFile();

		node.add('s', source);
		node.add('d', destination);
		node.add('a', amount);

		return node;
	}

	@Override
	public void loadNode(NodeFile node) {

		source = node.getLong('s');
		destination = node.getLong('d');
		amount = node.getInt('a');

	}

}
