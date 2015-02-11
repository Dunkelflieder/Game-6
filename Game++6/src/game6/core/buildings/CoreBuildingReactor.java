package game6.core.buildings;

import game6.core.events.*;

import java.util.*;

public abstract class CoreBuildingReactor extends CoreBuilding {

	private int tick = 0;
	private int shockCooldown = 10;
	private int shockPower = 10;
	private int shockRadius = 10;

	public CoreBuildingReactor(int id) {
		super(id, 2, 2, 0);
	}

	@Override
	public void update(List<Event> events) {

		tick++;

		if (tick % shockCooldown == 0) {
			tick = 0;
			List<CoreBuilding> candidates = map.getBuildingsWithin(getPosX(), getPosY(), shockRadius);
			
			// Modify the candidates.
			// 1st.: remove self and buildings without energy need
			for (Iterator<CoreBuilding> iter = candidates.iterator(); iter.hasNext();) {
				CoreBuilding building = iter.next();
				if (building == this || building.getMaxEnergy() == 0 || building.getEnergy() == building.getMaxEnergy()) {
					iter.remove();
				}
			}
			// 2nd shuffle (TODO: sort after distance)
			Collections.shuffle(candidates);
			
			// prevent division through 0
			if (candidates.size() == 0) {
				return;
			}
			
			// Share the available energy between the buildings
			int left = shockPower;
			int part = (int) Math.ceil(left / (float) (candidates.size()));
			
			for (int i = 0; i < candidates.size(); i++) {
				CoreBuilding building = candidates.get(i);
				int given = part - building.addEnergy(part);
				left -= given;
				// calculate new energy per building
				part = (int) Math.ceil(left / (float) (candidates.size() - (i + 1)));
				if (given > 0) {
					events.add(new EventPowerSupply(faction, getID(), building.getID(), given));
					events.add(new EventBuildingUpdate(building));
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

	public String getName() {
		return "Reaktor";
	}

}
