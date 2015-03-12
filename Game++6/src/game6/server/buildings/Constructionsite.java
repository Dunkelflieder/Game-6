package game6.server.buildings;

import game6.client.buildings.guis.BuildingGui;
import game6.core.buildings.CoreBuilding;
import game6.core.buildings.CoreConstructionsite;
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
	}

	@Override
	public void init() {
	}

	@Override
	public void render(Shader shader) {
	}

	@Override
	public void update() {
	}

	@Override
	public BuildingGui<CoreBuilding> getGui() {
		return null;
	}

}
