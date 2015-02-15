package game6.client.gui;

import game6.client.Controller;
import game6.client.gui.components.*;
import game6.client.gui.listener.MouseAdapter;
import game6.core.buildings.BuildingType;
import game6.core.buildings.CoreBuilding;

import java.awt.Color;
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

	private GColorfield highlight;
	private List<GBuilding> buildings;
	private BuildingType selectedBuilding;
	private CoreBuilding buildingPreview;

	public GPanelBuildingSelection(GameDisplay display, Controller controller) {

		this.controller = controller;

		highlight = new GColorfield(new Color(0f, 1f, 0f, 0.5f));

		buildings = new ArrayList<>();

		for (BuildingType type : BuildingType.values()) {
			GBuilding gBuilding = new GBuilding(display, type);
			buildings.add(gBuilding);
			add(gBuilding);
			gBuilding.addClickedListener(source -> {
				selectBuilding(((GBuilding) source).getBuildingType());
			});
		}

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

		add(highlight);
	}

	public BuildingType getBuilding() {
		return selectedBuilding;
	}

	public CoreBuilding getPreview() {
		return buildingPreview;
	}

	void selectBuilding(BuildingType building) {
		this.selectedBuilding = building;
		controller.getWorld().getMap().setGridActivated(building != null);
		if (building != null) {
			buildingPreview = building.getClientBuilding(0);
		} else {
			buildingPreview = null;
		}
		controller.getWorld().getMap().setBuildingPreview(buildingPreview);
		updateHighlight();
	}

	private void updateHighlight() {
		if (selectedBuilding == null) {
			highlight.setPos(-9999, -9999);
		} else {
			for (GBuilding building : buildings) {
				if (building.getBuildingType() == selectedBuilding) {
					highlight.setPos(building.getPosX(), building.getPosY());
					highlight.setSize(building.getSizeX(), building.getSizeY());
					return;
				}
			}
		}
	}

	/**
	 * Resizes the panel's components to match it's bounds.
	 */
	public void resize() {
		int offsetX = 0;
		for (GBuilding building : buildings) {
			building.setSize(128, 128);
			building.setPos(offsetX, 0);
			offsetX += 128;
		}
		updateHighlight();
	}

	public void reset() {
		selectBuilding(null);
	}
}
