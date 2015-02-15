package game6.client.gui;

import game6.client.Controller;
import game6.client.gui.components.*;
import game6.core.buildings.CoreBuilding;

import java.awt.Color;

/**
 * Panel, that contains information about a selected building.
 * Is included into the Ingame-GUI, but outsourced for readability.
 * @author Felk
 *
 */
public class GPanelBuildingInfo extends GPanel {

	private Controller controller;
	private GColorfield background;
	private CoreBuilding selectedBuilding;
	private GLabel title;
	private GLabel energy;

	public GPanelBuildingInfo(Controller controller) {

		this.controller = controller;

		background = new GColorfield(new Color(0, 0, 0, 0.5f));
		background.setSize(99999, 99999);
		background.setPos(0, 0);

		title = new GLabel();
		title.setAlignment(Font.CENTER);

		energy = new GLabel("Energie: ");

		add(background);
		add(title);
		add(energy);
	}

	@Override
	public void update() {
		super.update();
		if (selectedBuilding != null) {
			if (selectedBuilding.getFaction() == controller.getFaction()) {
				energy.setText("Energie: " + selectedBuilding.getEnergy() + " / " + selectedBuilding.getMaxEnergy());
			} else {
				energy.setText("owned by " + selectedBuilding.getFaction());
			}
		}
	}

	@Override
	public void render() {
		if (selectedBuilding != null) {
			super.render();
		}
	}

	/**
	 * Sets, which building is now selected
	 * @param building Building-Instance of the selected building
	 */
	public void setBuilding(CoreBuilding building) {
		this.selectedBuilding = building;
		if (building != null) {
			title.setText(building.getName() + " #" + building.getID());
		}
	}

	/**
	 * Resizes the panel's components to match it's bounds.
	 */
	public void resize() {
		title.setSize(getSizeX(), 40);
		title.setPos(0, getSizeY() - 50);

		energy.setSize(0, 40);
		energy.setPos(20, getSizeY() - 100);
	}
}
