package game6.client.gui;

import game6.client.gui.components.GButton;
import game6.client.gui.components.GLabel;
import game6.client.gui.components.listener.ButtonClickedListener;

import java.awt.Color;

public class GuiStart extends Gui {

	public static GuiStart instance = new GuiStart();
	private GLabel title;
	private GButton buttonConnect, buttonBuilding;

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
	}

}
