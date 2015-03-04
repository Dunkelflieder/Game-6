package game6.client.gui;

import game6.client.Controller;
import game6.client.gui.components.*;
import game6.client.gui.listener.MouseAdapter;
import game6.core.buildings.BuildingType;
import game6.core.buildings.CoreBuilding;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.render.GameDisplay;

/**
 * Panel, that contains a set of building which can be selected
 * @author Felk
 *
 */
public class GPanelBuildingSelection extends GPanel {

	private Controller controller;

	private List<GBuildingIcon> buildings;
	private BuildingType selectedBuilding;
	private CoreBuilding buildingPreview;

	private GImage buildingBackground;
	private GImage buildingHighlight;

	public GPanelBuildingSelection(GameDisplay display, Controller controller) {

		this.controller = controller;

		buildings = new ArrayList<>();

		buildingBackground = new GImage("res/gui/ingameBuildings.png");
		buildingBackground.setPos(0, 0);
		buildingBackground.setSize(1920, 175);

		buildingHighlight = new GImage("res/gui/ingameBuildingsHighlight.png");
		buildingHighlight.setSize(73, 73);
		buildingHighlight.setPos(-100, 0);

		addMouseListener(new MouseAdapter() {

			@Override
			public boolean mouseClicked(GComponent source, int button) {
				if (button == 0) {
					if (selectedBuilding != null) {
						controller.placeBuilding(selectedBuilding, buildingPreview.getPosX(), buildingPreview.getPosY());
						return true;
					}
				}
				return false;
			}

		});

		add(buildingBackground);
		add(buildingHighlight);

		for (BuildingType type : BuildingType.values()) {
			GBuildingIcon gBuilding = new GBuildingIcon(display, type);
			buildings.add(gBuilding);
			add(gBuilding);
			gBuilding.addClickedListener(source -> {
				selectBuilding(((GBuildingIcon) source).getBuildingType());
			});
		}
	}

	public BuildingType getBuilding() {
		return selectedBuilding;
	}

	public CoreBuilding getPreview() {
		return buildingPreview;
	}

	void selectBuilding(BuildingType building) {
		this.selectedBuilding = building;
		controller.getWorld().setGridActivated(building != null);
		if (building != null) {
			buildingPreview = building.getClientBuilding(0);
		} else {
			buildingPreview = null;
		}
		controller.getWorld().setBuildingPreview(buildingPreview);
		updateHighlight();
	}

	private void updateHighlight() {
		if (selectedBuilding == null) {
			buildingHighlight.setPos(-9999, -9999);
		} else {
			for (GBuildingIcon building : buildings) {
				if (building.getBuildingType() == selectedBuilding) {
					buildingHighlight.setPos(building.getPosX() - 5, building.getPosY() - 5);
					return;
				}
			}
		}
	}

	private int getPositionXForIndex(int index) {
		int x = 256 + 18;
		x += 69 * (index % 5);
		return x;
	}

	private int getPositionYForIndex(int index) {
		int y = 78;
		if (index > 5) {
			y += 80;
		}
		return y;
	}

	/**
	 * Resizes the panel's components to match it's bounds.
	 */
	public void resize() {
		for (int index = 0; index < buildings.size(); index++) {
			GBuildingIcon building = buildings.get(index);
			building.setSize(64, 64);
			building.setPos(getPositionXForIndex(index), getPositionYForIndex(index));
		}
		updateHighlight();
	}

	public void reset() {
		selectBuilding(null);
	}
}
