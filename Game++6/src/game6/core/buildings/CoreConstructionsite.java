package game6.core.buildings;

import game6.core.faction.Faction;
import game6.core.interfaces.DefaultResourceContainer;
import game6.core.interfaces.ResourceContainer;
import game6.core.util.Resource;

/**
 * Don't add this to the building type list. It's a special building representing a construction site.
 * It also does not have the necessary long-only constructor.
 * @author Felk
 */
public abstract class CoreConstructionsite<T extends CoreBuilding> extends DefaultCoreBuilding implements ResourceContainer {

	private T building;
	private ResourceContainer constructionCost;

	public CoreConstructionsite(T building, ResourceContainer constructionCost) {
		super(building.getID(), 1, 1, 0, 0, 100);
		this.building = building;
		this.constructionCost = new DefaultResourceContainer(constructionCost);
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

	public boolean isFinished() {
		return constructionCost.isEmpty();
	}

	public String getName() {
		return "Baustelle";
	}

	@Override
	public void setCapacity(int capacity) {
		constructionCost.setCapacity(capacity);
	}

	@Override
	public int getCapacity() {
		return constructionCost.getCapacity();
	}

	@Override
	public int getResource(Resource resource) {
		return constructionCost.getResource(resource);
	}

	@Override
	public void setResource(Resource resource, int amount) {
		constructionCost.setResource(resource, amount);
	}

}
