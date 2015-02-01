package game6.networking.packets;

import de.felk.NodeFile.NodeFile;

public class PacketConnectionInfo extends Packet {

	public int version;

	public PacketConnectionInfo() {
	}
	
	public PacketConnectionInfo(int version) {
		this.version = version;
	}

	@Override
	public NodeFile toNode() {
		NodeFile node = new NodeFile();
		node.add('v', version);
		return node;
	}

	@Override
	public void loadNode(NodeFile node) {
		version = node.getInt('v');
	}

}
