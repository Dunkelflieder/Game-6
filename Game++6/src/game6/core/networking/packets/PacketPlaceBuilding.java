package game6.core.networking.packets;

import game6.core.Faction;
import game6.core.buildings.BuildingType;
import de.felk.NodeFile.NodeFile;

public class PacketPlaceBuilding extends Packet {

	public BuildingType building;
	public Faction faction;
	public int id, posX, posY;

	public PacketPlaceBuilding() {
	}

	public PacketPlaceBuilding(BuildingType building, Faction faction, int id, int posX, int posY) {
		this.building = building;
		this.faction = faction;
		this.id = id;
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public NodeFile toNode() {
		NodeFile node = new NodeFile();

		node.add('f', faction.getID());
		node.add('i', id);
		node.add('x', posX);
		node.add('y', posY);
		node.add('t', building.getID());

		return node;
	}

	@Override
	public void loadNode(NodeFile node) {

		faction = Faction.byID(node.getInt('f'));
		id = node.getInt('i');
		posX = node.getInt('x');
		posY = node.getInt('y');
		building = BuildingType.byID(node.getInt('t'));

	}

}
