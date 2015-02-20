package game6.client.gui;

import game6.client.Controller;
import game6.client.gui.components.*;
import game6.core.entities.CoreEntity;

import java.awt.Color;

/**
 * Panel, that contains information about a selected building.
 * Is included into the Ingame-GUI, but outsourced for readability.
 * @author Felk
 *
 */
public class GPanelEntityInfo extends GPanel {

	private Controller controller;
	private GColorfield background;
	private GLabel title;

	public GPanelEntityInfo(Controller controller) {

		this.controller = controller;

		background = new GColorfield(new Color(0, 0, 0, 0.5f));
		background.setSize(99999, 99999);
		background.setPos(0, 0);

		title = new GLabel();
		title.setAlignment(Font.CENTER);

		add(background);
		add(title);
	}

	@Override
	public void update() {
		super.update();
		CoreEntity selectedEntity = controller.getWorld().getSelectedEntity();
		if (selectedEntity != null) {
			if (selectedEntity.getFaction() == controller.getFaction()) {
				title.setText(selectedEntity.getName() + " #" + selectedEntity.getID());
			} else {
				title.setText("Fremde Einheit");
			}
		}
	}

	@Override
	public void render() {
		if (controller.getWorld().getSelectedEntity() != null) {
			super.render();
		}
	}

	/**
	 * Resizes the panel's components to match it's bounds.
	 */
	public void resize() {
		title.setSize(getSizeX(), 40);
		title.setPos(0, getSizeY() - 50);
	}
}
