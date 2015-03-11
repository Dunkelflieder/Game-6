package game6.core.events;

import game6.core.buildings.BuildingType;
import game6.core.faction.Faction;
import game6.core.networking.packets.PacketEnabledBuildingsList;

import java.util.HashSet;
import java.util.List;

import de.nerogar.engine.UpdateEventInterface;

public class EventEnabledBuildingsChanged extends Event {

	private HashSet<BuildingType> buildings;

	public EventEnabledBuildingsChanged(Faction faction, HashSet<BuildingType> buildings) {
		super(faction);
		this.buildings = buildings;
	}

	@Override
	public void process(List<UpdateEventInterface> players) {
		faction.broadcast(new PacketEnabledBuildingsList(buildings));
	}

}
