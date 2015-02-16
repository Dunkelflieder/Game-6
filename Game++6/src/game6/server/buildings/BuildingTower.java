package game6.server.buildings;

import java.util.*;

import de.nerogar.engine.UpdateEvent;
import game6.core.buildings.CoreBuilding;
import game6.core.buildings.CoreBuildingTower;
import game6.core.events.EventBuildingUpdate;
import game6.core.events.EventPowerSupply;

public class BuildingTower extends CoreBuildingTower {

	private int tick = 0;
	// Ticks between each energy pulse
	private int shockCooldown = 10;
	// (quadratic) radius in which the energy can be emitted.
	private int shockRadius = 10;
	
	public BuildingTower() {
		super(getNextID());
	}

	@Override
	public void init() {
	}

	@Override
	public void render() {
	}

	@Override
	public void update(List<UpdateEvent> events) {
		tick++;

		// Each cooldown-period, start emitting energy
		if (tick % shockCooldown == 0) {

			// Get all buildings that can get energy
			List<CoreBuilding> candidates = map.getBuildingsWithin(getPosX(), getPosY(), shockRadius);

			// Modify the candidates.
			// 1st.: remove self, other factions and buildings without energy need
			for (Iterator<CoreBuilding> iter = candidates.iterator(); iter.hasNext();) {
				CoreBuilding building = iter.next();
				if (building == this || building.getFaction() != faction || !building.canReceiveEnergy()) {
					iter.remove();
				}
			}

			// 2nd shuffle (TODO: sort after distance)
			Collections.shuffle(candidates);

			// If no one could receive energy, stop the pulse emission.
			if (candidates.size() == 0) {
				return;
			}

			// Share the available energy between the buildings

			// The amount of energy that is left and needs to be emitted to somewhere
			int left = getEnergy();
			// The amount of energy each building receives
			int part = (int) Math.ceil(left / (float) (candidates.size()));

			for (int i = 0; i < candidates.size(); i++) {
				CoreBuilding building = candidates.get(i);

				// amount of energy the building actually received
				int given = part - building.addEnergy(part);

				// Remove the given energy from remaining pool
				left -= given;

				// calculate new energy per building
				part = (int) Math.ceil(left / (float) (candidates.size() - (i + 1)));

				// If the building received energy, trigger the corresponding events
				if (given > 0) {
					events.add(new EventPowerSupply(faction, getID(), building.getID(), given));
					events.add(new EventBuildingUpdate(building));
				}
			}
			
			if (left != getEnergy()) {
				events.add(new EventBuildingUpdate(this));
				setEnergy(left);
			}
		}
	}

}
