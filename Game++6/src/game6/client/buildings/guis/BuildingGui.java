package game6.client.buildings.guis;

import game6.client.buildings.IClientBuilding;
import game6.client.gui.components.GPanel;

public abstract class BuildingGui<T extends IClientBuilding> extends GPanel {

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
