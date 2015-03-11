package game6.server.buildings;

import game6.client.buildings.BuildingGui;
import game6.core.ai.goalfinding.Path;
import game6.core.buildings.CoreBuildingReactor;
import game6.core.events.EventBuildingUpdate;
import game6.core.events.EventPowerSupply;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.render.Shader;

public class BuildingReactor extends CoreBuildingReactor {

	private int tick = 0;
	// Ticks between each energy pulse
	private int shockCooldown = 50;
	// Amount of energy emitted with each pulse
	private int shockPower = 100;

	public BuildingReactor() {
		super(getNextID());
	}

	@Override
	public void init() {
	}

	@Override
	public void render(Shader shader) {
	}

	@Override
	public void update() {
		tick++;

		// Each cooldown-period start emitting energy
		if (tick % shockCooldown == 0) {

			List<Path> candidatesPaths = world.getEnergyGoals(this);
			List<Path> candidates = new ArrayList<>();
			for (Path path : candidatesPaths) {
				if (path.getGoal().getFaction() == faction && path.getGoal().canReceiveEnergy()) {
					candidates.add(path);
				}
			}

			// sort by energy need
			candidates.sort((p1, p2) -> (p2.getGoal().getMaxEnergy() - p2.getGoal().getEnergy()) - (p1.getGoal().getMaxEnergy() - p1.getGoal().getEnergy()));

			int left = shockPower;
			for (Path path : candidates) {
				int returned = path.getGoal().addEnergy(left);
				if (returned < left) {
					events.add(new EventPowerSupply(faction, this, path, left - returned));
					events.add(new EventBuildingUpdate(path.getGoal()));
				}
				left = returned;
				if (left == 0) {
					break;
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

	@Override
	public BuildingGui getGui() {
		return null;
	}

}
