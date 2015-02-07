package game6.client.gui;

import game6.client.buildings.BuildingFactory;
import game6.client.buildings.BuildingImageRenderer;
import game6.client.buildings.BuildingReactor;
import game6.client.buildings.BuildingResearch;
import game6.client.buildings.BuildingTower;

import java.awt.Color;

import de.nerogar.render.GameDisplay;

public class GuiStart extends Gui {

	public static GuiStart instance = new GuiStart();
	private GLabel title;
	private GButton buttonConnect, buttonBuilding;
	
	private GImage buildingFactory;
	private GImage buildingReactor;
	private GImage buildingResearch;
	private GImage buildingTower;

	@Override
	public void init() {
		title = new GLabel("Startgui");
		
		buttonConnect = new GButton("Connect to local server");
		buttonConnect.text.setColor(Color.BLACK);
		
		buttonBuilding = new GButton("Place random building");
		buttonBuilding.text.setColor(Color.BLACK);
		
		add(title);
		add(buttonConnect);
		add(buttonBuilding);
	}
	
	// TODO remove this method. It sucks.
	public void useThisDisplay(GameDisplay display) {
		buildingFactory = new GImage(BuildingImageRenderer.render(display, new BuildingFactory(-1)));
		buildingReactor = new GImage(BuildingImageRenderer.render(display, new BuildingReactor(-1)));
		buildingResearch = new GImage(BuildingImageRenderer.render(display, new BuildingResearch(-1)));
		buildingTower = new GImage(BuildingImageRenderer.render(display, new BuildingTower(-1)));
		add(buildingFactory);
		add(buildingReactor);
		add(buildingResearch);
		add(buildingTower);
	}
	
	public boolean addConnectionClickedListener(ButtonClickedListener listener) {
		return buttonConnect.addButtonClickedListener(listener);
	}
	
	public boolean removeConnectionClickedListener(ButtonClickedListener listener) {
		return buttonConnect.removeButtonClickedListener(listener);
	}
	
	public boolean addBuildingClickedListener(ButtonClickedListener listener) {
		return buttonBuilding.addButtonClickedListener(listener);
	}

	public boolean removeBuildingClickedListener(ButtonClickedListener listener) {
		return buttonBuilding.removeButtonClickedListener(listener);
	}

	@Override
	public void onSelect() {
	}

	@Override
	public void onDeselect() {
	}

	@Override
	public void onResize(int screenWidth, int screenHeight) {
		title.setSize(100, 30);
		title.setPos(20, screenHeight - 50);
		
		buttonConnect.setSize(310, 40);
		buttonConnect.setPos(20, 20);
		
		buttonBuilding.setSize(310, 40);
		buttonBuilding.setPos(20, 70);
		
		buildingFactory.setSize(128, 128);
		buildingFactory.setPos(350, 0);
		
		buildingReactor.setSize(128, 128);
		buildingReactor.setPos(450, 0);
		
		buildingResearch.setSize(128, 128);
		buildingResearch.setPos(550, 0);
		
		buildingTower.setSize(128, 128);
		buildingTower.setPos(650, 0);
	}

}
