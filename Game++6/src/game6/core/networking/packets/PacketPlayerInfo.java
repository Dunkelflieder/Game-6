package game6.core.networking.packets;

import game6.core.faction.Faction;
import de.felk.NodeFile.NodeFile;

public class PacketPlayerInfo extends Packet {

	public Faction faction;

	public PacketPlayerInfo() {
	}
	
	public PacketPlayerInfo(Faction faction) {
		this.faction = faction;
	}

	@Override
	public NodeFile toNode() {
		NodeFile node = new NodeFile();
		node.add('f', faction.getID());
		return node;
	}

	@Override
	public void loadNode(NodeFile node) {
		faction = Faction.byID(node.getInt('f'));
	}

}
