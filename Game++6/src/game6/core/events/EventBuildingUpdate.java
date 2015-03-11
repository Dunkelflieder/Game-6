package game6.core.events;

import game6.core.buildings.CoreBuilding;
import game6.core.networking.packets.PacketBuildingUpdate;

import java.util.List;

public class EventBuildingUpdate extends Event {

	private CoreBuilding building;

	public EventBuildingUpdate(CoreBuilding building) {
		super(building.getFaction());
		this.building = building;
	}

	public CoreBuilding getBuilding() {
		return building;
	}

	@Override
	public void process(List<UpdateEventInterface> players) {
		faction.broadcast(new PacketBuildingUpdate(building));
	}

}
