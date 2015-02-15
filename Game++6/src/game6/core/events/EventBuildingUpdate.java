package game6.core.events;

import game6.core.buildings.CoreBuilding;
import game6.core.networking.packets.PacketBuildingUpdate;
import game6.server.world.Player;

import java.util.List;

import de.nerogar.engine.UpdateEventInterface;

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
		for (UpdateEventInterface i : players) {
			Player p = (Player) i;
			if (p.getFaction() == faction) {
				p.getConnection().send(new PacketBuildingUpdate(building));
			}
		}
	}

}
