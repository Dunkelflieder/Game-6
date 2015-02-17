package game6.client.gui;

import game6.client.Controller;
import game6.client.gui.components.*;
import game6.client.gui.listener.KeyboardAdapter;
import game6.client.gui.listener.MouseAdapter;
import game6.client.world.World;
import game6.core.buildings.BuildingType;
import game6.core.buildings.CoreBuilding;
import game6.core.entities.EntityType;

import org.lwjgl.input.Keyboard;

import de.nerogar.render.GameDisplay;
import de.nerogar.util.*;

/**
 * Gui, that is used ingame.
 * Currently contains another panel for building selection and one for building information.
 * @author Felk
 *
 */
public class GuiIngame extends Gui {

	public static GuiIngame instance = new GuiIngame();
	private GButton buttonBuilding;
	private GButton buttonEntity;

	private GPanelBuildingInfo buildingPanel;
	private GPanelBuildingSelection selectionPanel;

	@Override
	public void initComponents() {

		buttonBuilding = new GButton("Place random building");
		buttonBuilding.addClickedListener(source -> {
			for (int i = 0; i < 100; i++) {
				controller.placeBuilding(BuildingType.getRandom(), (int) (Math.random() * (controller.getWorld().getMap().getSizeX() - 2)), (int) (Math.random() * (controller.getWorld().getMap().getSizeY() - 2)));
			}
		});

		buttonEntity = new GButton("Place random entity");
		buttonEntity.addClickedListener(source -> {
			for (int i = 0; i < 100; i++) {
				controller.requestEntity(EntityType.TANK1, new Vector3f((int) (50 * Math.random()), 0, (int) (50 * Math.random())));
			// controller.requestEntity(EntityType.getRandom(), new Vector3f((float) (Math.random() * (controller.getWorld().getMap().getSizeX() - 2)), 1, (float) (Math.random() * (controller.getWorld().getMap().getSizeY() - 2))));
			}
			});

		// Panel, that represents the Info-Panel for a selected building. Very basic and generic for all buildings for now
		buildingPanel = new GPanelBuildingInfo(controller);

		// Add manager for camera movement
		CameraMovementManager camManager = new CameraMovementManager(controller.getCamera());
		camManager.addCameraMovedListener(() -> updateCenterOfRendering());
		panel.addMouseListener(camManager);

		// clicks on map
		panel.addMouseListener(new MouseAdapter() {

			@Override
			public boolean mouseClicked(GComponent source, int button) {
				if (button == 0) {

					if (selectionPanel.getBuilding() != null) {
						// If a building is selected, place it on map click
						controller.placeBuilding(selectionPanel.getBuilding(), selectionPanel.getPreview().getPosX(), selectionPanel.getPreview().getPosY());
						return true;
					} else {
						// Else, try to select a building on the map

						World world = controller.getWorld();
						if (!world.isLoaded()) {
							return false;
						}

						// TODO don't hardcode fov
						controller.getInputHandler().updateMousePositions(controller.getCamera(), 90);
						Ray<Vector3f> mouseRay = controller.getInputHandler().getMouseRay();
						RayIntersection<Vector3f> intersection = world.getPhysicsSpace().getIntersecting(mouseRay);

						if (intersection == null) {

							// No intersection with mouse ray. Trying to select building...
							Vector2f mapIntersection = controller.getWorld().getMap().getIntersection(mouseRay);
							if (mapIntersection != null) {
								// If there is a building, select it
								CoreBuilding building = controller.getWorld().getMap().getBuildingAt((int) mapIntersection.getX(), (int) mapIntersection.getY());
								buildingPanel.setBuilding(building);
							}

						} else {

							// TODO A unit was selected. Do stuff.

						}
					}

				} else if (button == 1) {
					// Rightclick deselects
					selectionPanel.selectBuilding(null);
				}
				return false;
			}

			@Override
			public boolean mouseMoved(GComponent source, int dx, int dy) {

				if (selectionPanel.getBuilding() == null) {
					return false;
				}

				World world = controller.getWorld();
				if (!world.isLoaded()) {
					return false;
				}

				// TODO don't hardcode fov
				controller.getInputHandler().updateMousePositions(controller.getCamera(), 90);
				Ray<Vector3f> mouseRay = controller.getInputHandler().getMouseRay();

				Vector2f intersect = world.getMap().getIntersection(mouseRay);

				if (intersect != null) {
					selectionPanel.getPreview().setPosX(MathHelper.clamp((int) intersect.getX(), 0, world.getMap().getSizeX() - selectionPanel.getPreview().getSizeX()));
					selectionPanel.getPreview().setPosY(MathHelper.clamp((int) intersect.getY(), 0, world.getMap().getSizeY() - selectionPanel.getPreview().getSizeY()));
				}

				return true;
			}

		});

		// pause menu
		panel.addKeyboardListener(new KeyboardAdapter() {
			@Override
			public boolean keyPressed(GComponent source, int keyCode, char key) {
				if (keyCode == Keyboard.KEY_ESCAPE) {
					Guis.select(Guis.PAUSE);
				}
				return false;
			}
		});

		add(buttonBuilding);
		add(buttonEntity);
		add(buildingPanel);
	}

	/**
	 * Calculates and sends the position the camera is looking.
	 * This position represents the center around which the map should be rendered
	 */
	public void updateCenterOfRendering() {

		if (!controller.getWorld().isLoaded()) {
			return;
		}

		// Decrease the pitch angle for the calculation to get a center point further away.
		// Because of perspective, the actual center point is not a good center point for rendering.
		float pitch = controller.getCamera().getPitch();
		controller.getCamera().setPitch(pitch * 0.3f);

		// Get the world point of the camera center
		Ray<Vector3f> cameraRay = controller.getCamera().getCameraRay();

		Vector2f intersect = controller.getWorld().getMap().getIntersection(cameraRay);
		if (intersect != null) {
			controller.getWorld().setCenterOfRendering((int) intersect.getX(), (int) intersect.getY());
		}

		// Revert to the original pitch
		controller.getCamera().setPitch(pitch);
	}

	@Override
	public void init(GameDisplay display, Controller controller) {
		super.init(display, controller);
		selectionPanel = new GPanelBuildingSelection(display, controller);
		add(selectionPanel);
	}

	@Override
	public void onSelect() {
		panel.removeAllKeyboardListeners();
		panel.removeAllMouseListeners();
		initComponents();
	}

	@Override
	public void onDeselect() {
	}

	@Override
	public void onResize(int screenWidth, int screenHeight) {
		buttonBuilding.setSize(310, 40);
		buttonBuilding.setPos(20, 20);

		buttonEntity.setSize(310, 40);
		buttonEntity.setPos(20, 70);

		buildingPanel.setSize(300, 500);
		buildingPanel.setPos(screenWidth - 300, 0);
		buildingPanel.resize();

		selectionPanel.setSize(screenWidth, 128);
		selectionPanel.setPos(310, 0);
		selectionPanel.resize();
	}

	public void reset() {
		selectionPanel.reset();
	}

}
