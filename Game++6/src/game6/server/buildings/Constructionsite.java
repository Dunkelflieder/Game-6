package game6.server.buildings;

import game6.core.buildings.CoreConstructionsite;
import game6.core.faction.Faction;
import game6.core.interfaces.ResourceContainer;
import game6.core.networking.packets.buildings.PacketBuildingFinishConstruction;
import game6.server.world.World;

/**
 * Don't add this to the building type list. It's a special building representing a construction site.
 * It also does not have the necessary long-only constructor.
 * @author Felk
 */
public class Constructionsite extends CoreConstructionsite<ServerBuilding> implements ServerBuilding, ServerBuildingInventory {

	private DefaultServerBuildingBehaviour defaultBehaviour = new DefaultServerBuildingBehaviour();

	public Constructionsite(ServerBuilding building, ResourceContainer constructionCost) {
		super(building, constructionCost);
	}

	@Override
	public void init() {
	}

	@Override
	public void update(float timeDelta) {
		super.update(timeDelta);
		if (isFinished()) {
			getWorld().finishConstructionsite(this);
			Faction.broadcastAll(new PacketBuildingFinishConstruction(getID()));
		}

		// TODO for debugging. remove later
		/*if (Math.random() < 0.2f) {
			removeResource(Resource.WOOD, 1);
			removeResource(Resource.METAL, 1);
		}*/
	}

	@Override
	public World getWorld() {
		return defaultBehaviour.getWorld();
	}

	@Override
	public void setWorld(World world) {
		defaultBehaviour.setWorld(world);
		getBuilding().setWorld(world);
	}

}
