package game6.server.buildings;

import game6.core.ai.goalfinding.Path;
import game6.core.buildings.CoreBuildingReactor;
import game6.core.faction.Faction;
import game6.core.networking.packets.PacketPowerSupply;
import game6.core.networking.packets.buildings.PacketBuildingUpdate;
import game6.server.world.World;

import java.util.ArrayList;
import java.util.List;

public class BuildingReactor extends CoreBuildingReactor implements ServerBuilding {

	private DefaultServerBuildingBehaviour defaultBehaviour = new DefaultServerBuildingBehaviour();

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
	public void update(float timeDelta) {
		super.update(timeDelta);
		tick++;

		// Each cooldown-period start emitting energy
		if (tick % shockCooldown == 0) {

			List<Path> candidatesPaths = getWorld().getEnergyGoals(this);
			List<Path> candidates = new ArrayList<>();
			for (Path path : candidatesPaths) {
				if (path.getGoal().getFaction() == getFaction() && path.getGoal().canReceiveEnergy()) {
					candidates.add(path);
				}
			}

			// sort by energy need
			candidates.sort((p1, p2) -> (p2.getGoal().getMaxEnergy() - p2.getGoal().getEnergy()) - (p1.getGoal().getMaxEnergy() - p1.getGoal().getEnergy()));

			int left = shockPower;
			for (Path path : candidates) {
				int returned = path.getGoal().addEnergy(left);
				if (returned < left) {
					// visible lightnings
					Faction.broadcastAll(new PacketPowerSupply(this, path, left - returned));
					// update energy values and stuff
					getFaction().broadcast(new PacketBuildingUpdate(path.getGoal()));
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
	public World getWorld() {
		return defaultBehaviour.getWorld();
	}

	@Override
	public void setWorld(World world) {
		defaultBehaviour.setWorld(world);
	}

}
