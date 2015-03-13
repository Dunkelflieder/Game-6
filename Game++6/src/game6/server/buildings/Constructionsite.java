package game6.server.buildings;

import game6.client.buildings.guis.BuildingGui;
import game6.core.buildings.CoreBuilding;
import game6.core.buildings.CoreConstructionsite;
import game6.core.faction.Faction;
import game6.core.networking.packets.PacketFinishConstruction;
import game6.core.networking.packets.PacketUpdateConstructionsite;
import game6.core.util.Resource;
import game6.core.util.ResourceContainer;
import de.nerogar.render.Shader;

/**
 * Don't add this to the building type list. It's a special building representing a construction site.
 * It also does not have the necessary long-only constructor.
 * @author Felk
 */
public class Constructionsite extends CoreConstructionsite {

	public Constructionsite(CoreBuilding building, ResourceContainer constructionCost) {
		super(building, constructionCost);
		getCostRemaining().setChangeCallback(this::remainingCostChanged);
	}

	private void remainingCostChanged() {
		faction.broadcast(new PacketUpdateConstructionsite(getID(), getCostRemaining()));
	}

	@Override
	public void init() {
	}

	@Override
	public void render(Shader shader) {
	}

	@Override
	public void update() {
		if (isFinished()) {
			world.finishConstructionsite(this);
			Faction.broadcastAll(new PacketFinishConstruction(getID()));
		}

		// TODO for debugging. remove later
		if (Math.random() < 0.2f) {
			getCostRemaining().removeResource(Resource.WOOD, 1);
		}
	}

	@Override
	public BuildingGui<CoreBuilding> getGui() {
		return null;
	}

}
