package game6.client.buildings.guis;

import java.awt.Color;

import game6.client.gui.Font;
import game6.client.gui.components.GColorfield;
import game6.client.gui.components.GLabel;
import game6.core.buildings.CoreBuilding;

public class BuildingGuiDefault extends BuildingGui<CoreBuilding> {

	private GLabel text;
	private GColorfield background;

	public BuildingGuiDefault(CoreBuilding building) {
		super(building);
	}

	@Override
	protected void initComponents() {
		setSize(300, 60);
		
		text = new GLabel(building.getName() + " #" + building.getID());
		text.setPos(0, 10);
		text.setAlignment(Font.CENTER);
		text.setSize(300, 40);

		background = new GColorfield(new Color(0, 0, 0, 0.5f));
		background.setPos(0, 0);
		background.setSize(getSizeX(), getSizeY());


		add(background);
		add(text);
	}

	@Override
	protected void updateComponents() {
	}

}
