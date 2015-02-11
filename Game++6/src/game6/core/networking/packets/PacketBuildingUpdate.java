package game6.core.networking.packets;

import game6.core.buildings.CoreBuilding;
import de.felk.NodeFile.NodeFile;

public class PacketBuildingUpdate extends Packet {

	public int id, energy;

	public PacketBuildingUpdate() {
	}
	
	public PacketBuildingUpdate(CoreBuilding building) {
		this.id = building.getID();
		this.energy = building.getEnergy();
	}
	
	@Override
	public NodeFile toNode() {
		NodeFile node = new NodeFile();

		node.add('i', id);
		node.add('e', energy);

		return node;
	}

	@Override
	public void loadNode(NodeFile node) {

		id = node.getInt('i');
		energy = node.getInt('e');

	}

}
