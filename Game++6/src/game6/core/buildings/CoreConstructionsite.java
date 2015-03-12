package game6.core.buildings;

import game6.core.util.ResourceContainer;

/**
 * Don't add this to the building type list. It's a special building representing a construction site.
 * It also does not have the necessary long-only constructor.
 * @author Felk
 */
public abstract class CoreConstructionsite extends CoreBuilding {

	private CoreBuilding building;
	private ResourceContainer constructionCost;

	public CoreConstructionsite(CoreBuilding building, ResourceContainer constructionCost) {
		super(building.getID(), 1, 1, 0, 0);
		this.building = building;
		this.constructionCost = constructionCost.clone();
	}

	@Override
	public int getSizeX() {
		return building.getSizeX();
	}

	@Override
	public int getSizeY() {
		return building.getSizeY();
	}

	public CoreBuilding getBuildingType() {
		return building;
	}

	public ResourceContainer getCostRemaining() {
		return constructionCost;
	}

	public String getName() {
		return "Baustelle";
	}

}
