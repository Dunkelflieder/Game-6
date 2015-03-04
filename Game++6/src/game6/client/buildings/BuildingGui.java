package game6.client.buildings;

import game6.client.gui.components.GPanel;
import game6.core.buildings.CoreBuilding;

public abstract class BuildingGui extends GPanel {

	protected CoreBuilding building;

	public BuildingGui(CoreBuilding building) {
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
