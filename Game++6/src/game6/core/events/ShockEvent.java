package game6.core.events;

import game6.core.buildings.CoreBuilding;
import game6.core.networking.ServerThread;
import game6.core.world.CoreMap;

import java.util.List;

public class ShockEvent implements Event {

	private CoreBuilding origin;
	private int power, radius;

	public ShockEvent(CoreBuilding origin, int power, int radius) {
		this.origin = origin;
		this.power = power;
		this.radius = radius;
	}

	public CoreBuilding getOrigin() {
		return origin;
	}

	public int getPower() {
		return power;
	}

	@Override
	public void doNetwork(List<Event> events, ServerThread serverThread) {
	}

	@Override
	public void doMap(List<Event> events, CoreMap map) {
		List<CoreBuilding> candidates = map.getBuildingsWithin(origin.getPosX(), origin.getPosY(), radius);
		// Share the available energy between the buildings
		int left = power;
		int part = (int) Math.ceil(left / (float) candidates.size());
		for (int i = 0; i < candidates.size(); i++) {
			CoreBuilding building = candidates.get(i);
			int given = part - building.addEnergy(part);
			left -= given;
			part = (int) Math.ceil(left / (float) (candidates.size() - i + 1));
			if (given > 0) {
				events.add(new PowerSupplyEvent(origin.getID(), building.getID(), given));
			}
		}
	}
}
