package game6.core.networking.packets;

import game6.core.buildings.BuildingType;
import de.felk.NodeFile.NodeFile;

public class PacketPlaceBuilding extends Packet {

	public BuildingType building;
	public int posX, posY;

	public PacketPlaceBuilding() {
	}
	
	public PacketPlaceBuilding(BuildingType building, int posX, int posY) {
		this.building = building;
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public NodeFile toNode() {
		NodeFile node = new NodeFile();

		node.add('x', posX);
		node.add('y', posY);
		node.add('i', building.getID());

		return node;
	}

	@Override
	public void loadNode(NodeFile node) {
		
		posX = node.getInt('x');
		posY = node.getInt('y');
		building = BuildingType.byID(node.getInt('i'));
		
	}

}
