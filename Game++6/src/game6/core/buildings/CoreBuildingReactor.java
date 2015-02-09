package game6.core.buildings;

import game6.core.events.*;

import java.util.List;

public abstract class CoreBuildingReactor extends CoreBuilding {

	private int tick = 0;
	private int shockCooldown = 30;
	private int shockPower = 10;
	private int shockRadius = 10;
	
	public CoreBuildingReactor(int id) {
		super(id, 2, 2);
	}

	@Override
	public void update(List<Event> events) {
		
		tick++;
		
		if (tick % shockCooldown == 0) {
			tick = 0;
			List<CoreBuilding> candidates = map.getBuildingsWithin(getPosX(), getPosY(), shockRadius);
			// Share the available energy between the buildings
			int left = shockPower;
			int part = (int) Math.ceil(left / (float) candidates.size());
			for (int i = 0; i < candidates.size(); i++) {
				CoreBuilding building = candidates.get(i);
				int given = part - building.addEnergy(part);
				left -= given;
				part = (int) Math.ceil(left / (float) (candidates.size() - i + 1));
				if (given > 0) {
					events.add(new PowerSupplyEvent(faction, getID(), building.getID(), given));
				}
			}
		}
		
	}
	
	public int getShockCooldown() {
		return shockCooldown;
	}
	
	public int getShockPower() {
		return shockPower;
	}
	
}
