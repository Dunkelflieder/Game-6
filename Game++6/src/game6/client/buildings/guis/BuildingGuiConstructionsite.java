package game6.client.buildings.guis;

import game6.client.buildings.Constructionsite;
import game6.client.gui.Font;
import game6.client.gui.components.GColorfield;
import game6.client.gui.components.GLabel;
import game6.core.util.Resource;

import java.awt.Color;

public class BuildingGuiConstructionsite extends BuildingGui<Constructionsite> {

	private GLabel text;
	private GLabel stuff;
	private GColorfield background;

	public BuildingGuiConstructionsite(Constructionsite building) {
		super(building);
	}

	@Override
	protected void initComponents() {
		setSize(300, 100);

		text = new GLabel(building.getName() + " #" + building.getID());
		text.setPos(0, 50);
		text.setAlignment(Font.CENTER);
		text.setSize(300, 40);

		stuff = new GLabel();
		stuff.setPos(0, 20);
		stuff.setAlignment(Font.CENTER);
		stuff.setSize(300, 30);

		background = new GColorfield(new Color(0, 0, 0, 0.5f));
		background.setPos(0, 0);
		background.setSize(300, 100);

		add(background);
		add(text);
		add(stuff);
	}

	@Override
	protected void updateComponents() {
		stuff.setText(Resource.WOOD.getName() + " needed: " + building.getCostRemaining().getAmount(Resource.WOOD));
	}

}
