package game6.core.buildings;

import game6.core.faction.Faction;
import game6.core.util.ResourceContainer;

/**
 * Don't add this to the building type list. It's a special building representing a construction site.
 * It also does not have the necessary long-only constructor.
 * @author Felk
 */
public abstract class CoreConstructionsite<T extends CoreBuilding> extends DefaultCoreBuilding {

	private T building;
	private ResourceContainer constructionCost;

	public CoreConstructionsite(T building, ResourceContainer constructionCost) {
		super(building.getID(), 1, 1, 0, 0);
		this.building = building;
		this.constructionCost = constructionCost.clone();
		setFaction(building.getFaction());
	}

	@Override
	public int getSizeX() {
		return building.getSizeX();
	}

	@Override
	public int getSizeY() {
		return building.getSizeY();
	}

	@Override
	public void setPosX(int posX) {
		super.setPosX(posX);
		building.setPosX(posX);
	}

	@Override
	public void setPosY(int posY) {
		super.setPosY(posY);
		building.setPosY(posY);
	}

	@Override
	public void setFaction(Faction faction) {
		super.setFaction(faction);
		building.setFaction(faction);
	}

	public T getBuilding() {
		return building;
	}

	public ResourceContainer getCostRemaining() {
		return constructionCost;
	}

	public boolean isFinished() {
		return constructionCost.isEmpty();
	}

	public String getName() {
		return "Baustelle";
	}

}
