package game6.client.buildings.guis;

import game6.client.gui.components.GPanel;
import game6.core.buildings.CoreBuilding;

public abstract class BuildingGui<T extends CoreBuilding> extends GPanel {

	protected T building;

	public BuildingGui(T building) {
		this.building = building;
		initComponents();
	}

	@Override
	public void update() {
		super.update();
		updateComponents();
	}

	protected abstract void initComponents();

	protected abstract void updateComponents();

}
