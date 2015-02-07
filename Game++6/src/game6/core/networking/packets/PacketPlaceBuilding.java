package game6.core.networking.packets;

import game6.core.buildings.BuildingType;
import de.felk.NodeFile.NodeFile;

public class PacketPlaceBuilding extends Packet {

	public BuildingType building;
	public int id, posX, posY;

	public PacketPlaceBuilding() {
	}
	
	public PacketPlaceBuilding(BuildingType building, int id, int posX, int posY) {
		this.building = building;
		this.id = id;
		this.posX = posX;
		this.posY = posY;
	}
	
	@Override
	public NodeFile toNode() {
		NodeFile node = new NodeFile();

		node.add('i', id);
		node.add('x', posX);
		node.add('y', posY);
		node.add('t', building.getID());

		return node;
	}

	@Override
	public void loadNode(NodeFile node) {
		
		id = node.getInt('i');
		posX = node.getInt('x');
		posY = node.getInt('y');
		building = BuildingType.byID(node.getInt('t'));
		
	}

}
