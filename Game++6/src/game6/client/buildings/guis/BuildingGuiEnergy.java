package game6.client.buildings.guis;

import game6.client.buildings.IClientBuilding;
import game6.client.gui.Font;
import game6.client.gui.components.GColorfield;
import game6.client.gui.components.GLabel;

import java.awt.Color;

public class BuildingGuiEnergy extends BuildingGui<IClientBuilding> {

	private GLabel text;
	private GLabel energy;
	private GColorfield background;

	public BuildingGuiEnergy(IClientBuilding building) {
		super(building);
	}

	@Override
	protected void initComponents() {
		setSize(300, 100);

		text = new GLabel(building.getName() + " #" + building.getID());
		text.setPos(0, 50);
		text.setAlignment(Font.CENTER);
		text.setSize(300, 40);

		energy = new GLabel();
		energy.setPos(0, 20);
		energy.setAlignment(Font.CENTER);
		energy.setSize(300, 30);

		background = new GColorfield(new Color(0, 0, 0, 0.5f));
		background.setPos(0, 0);
		background.setSize(300, 100);

		add(background);
		add(text);
		add(energy);
	}

	@Override
	protected void updateComponents() {
		energy.setText("Energie: " + building.getEnergy() + "/" + building.getMaxEnergy());
	}

}
