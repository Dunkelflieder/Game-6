package game6.client.gui;

import game6.client.Game;
import game6.client.buildings.ClientBuilding;
import game6.client.entities.ClientEntity;
import game6.client.gui.components.*;
import game6.client.gui.listener.KeyboardAdapter;
import game6.client.gui.listener.MouseAdapter;
import game6.client.world.ClientWorld;
import game6.core.buildings.BuildingType;
import game6.core.entities.EntityType;
import game6.core.faction.Faction;
import game6.core.interfaces.ResourceContainer;
import game6.core.world.IntersectionHelper;
import game6.core.world.RayIntersection;

import org.lwjgl.input.Keyboard;

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

	private GPanelBuildingSelection selectionPanel;
	private CameraMovementManager camManager;

	private GImage minimap;

	private Vector2f mapPosition;

	@Override
	public void initComponents() {

		buttonBuilding = new GButton(Game.langFile.getString("gui.ingame.button.spawnBuildingDebug"));
		buttonBuilding.addClickedListener(source -> {
			for (int i = 0; i < 100; i++) {
				controller.placeBuilding(BuildingType.getRandom(), (int) (Math.random() * (controller.getWorld().getMap().getSizeX() - 4)), (int) (Math.random() * (controller.getWorld().getMap().getSizeY() - 4)));
			}
		});

		buttonEntity = new GButton(Game.langFile.getString("gui.ingame.button.spawnEntityDebug"));
		buttonEntity.addClickedListener(source -> {
			// for (int i = 0; i < 100; i++) {
			// controller.requestEntity(EntityType.getRandom(), new Vector3f((int) (200 * Math.random()), 0, (int) (200 * Math.random())));
			// controller.requestEntity(EntityType.getRandom(), new Vector3f((float) (Math.random() * (controller.getWorld().getMap().getSizeX() - 2)), 1, (float) (Math.random() * (controller.getWorld().getMap().getSizeY() - 2))));
			// }
				controller.requestEntity(EntityType.getRandom(), new Vector3f(10, 0, 10));
				controller.requestEntity(EntityType.getRandom(), new Vector3f(10, 0, 10));
			});

		// Add manager for camera movement
		camManager = new CameraMovementManager(controller.getCamera());
		camManager.addCameraMovedListener(() -> updateCenterOfRendering());
		panel.addMouseListener(camManager);

		// clicks on map
		panel.addMouseListener(new MouseAdapter() {

			@Override
			public boolean mouseClicked(GComponent source, int button) {
				// TODO don't hardcode fov
				ClientWorld world = controller.getWorld();

				if (!world.isLoaded()) {
					return false;
				}

				controller.getInputHandler().updateMousePositions(controller.getCamera(), 90);
				Ray<Vector3f> mouseRay = controller.getInputHandler().getMouseRay();
				// RayIntersection<Vector3f> intersection = world.getPhysicsSpace().getIntersecting(mouseRay);
				RayIntersection intersection = IntersectionHelper.getIntersecting(world.getEntities(), mouseRay);
				Vector2f mapIntersection = controller.getWorld().getMap().getIntersection(mouseRay);

				ClientBuilding clickedBuilding = null;
				ClientEntity clickedEntity = null;

				if (mapIntersection != null) {
					clickedBuilding = controller.getWorld().getMap().getBuildingAt((int) mapIntersection.getX(), (int) mapIntersection.getY());
				}
				if (intersection != null) {
					clickedEntity = (ClientEntity) intersection.intersectingBody;
				}

				if (button == 0) {

					if (selectionPanel.getBuilding() != null) {
						// If a building is selected, place it on map click
						controller.placeBuilding(selectionPanel.getBuilding(), selectionPanel.getPreview().getPosX(), selectionPanel.getPreview().getPosY());
						return true;
					} else {
						// Else, try to select a building on the map

						if (clickedEntity == null) {
							// No intersection with mouse ray. Select building...

							world.selectBuilding(clickedBuilding);
							repositionBuildingGui();
						} else {
							world.selectEntity(clickedEntity);
							repositionEntityGui();
						}
					}

				} else if (button == 1) {
					// Rightclick deselects
					if (selectionPanel.getBuilding() != null) {
						selectionPanel.selectBuilding(null);
						return true;
					}

					ClientEntity selectedEntity = controller.getWorld().getSelectedEntity();
					if (selectedEntity != null) {
						if (clickedBuilding != null && clickedBuilding.getFaction() != Faction.own) {
							controller.setEntityAttack(selectedEntity, clickedBuilding);
						} else if (clickedBuilding != null && clickedBuilding instanceof ResourceContainer) {
							controller.setEntityDeliverJob(selectedEntity, clickedBuilding);
						} else if (clickedEntity != null && clickedEntity.getFaction() != Faction.own) {
							controller.setEntityAttack(selectedEntity, clickedEntity);
						} else {
							controller.moveEntity(selectedEntity, new Vector3f(mapPosition.getX(), selectedEntity.getPosition().getY(), mapPosition.getY()));
						}
					}
				}
				return false;
			}

			@Override
			public boolean mouseMoved(GComponent source, int dx, int dy) {

				ClientWorld world = controller.getWorld();

				if (!world.isLoaded()) {
					return false;
				}

				// TODO don't hardcode fov
				controller.getInputHandler().updateMousePositions(controller.getCamera(), 90);
				Ray<Vector3f> mouseRay = controller.getInputHandler().getMouseRay();

				mapPosition = world.getMap().getIntersection(mouseRay);

				if (selectionPanel.getBuilding() != null) {
					if (mapPosition != null) {
						selectionPanel.getPreview().setPosX(MathHelper.clamp((int) mapPosition.getX(), 0, world.getMap().getSizeX() - selectionPanel.getPreview().getSizeX()));
						selectionPanel.getPreview().setPosY(MathHelper.clamp((int) mapPosition.getY(), 0, world.getMap().getSizeY() - selectionPanel.getPreview().getSizeY()));
					}
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
				} else if (keyCode == Keyboard.KEY_DELETE) {
					if (controller.getWorld().getSelectedBuilding() != null) {
						// TODO maybe don't do the network stuff here?
						controller.removeBuilding(controller.getWorld().getSelectedBuilding());
					} else if (controller.getWorld().getSelectedEntity() != null) {
						// TODO maybe don't do the network stuff here?
						controller.removeEntity(controller.getWorld().getSelectedEntity());
					}
				}
				return false;
			}
		});

		selectionPanel = new GPanelBuildingSelection(controller);
		selectionPanel.addMouseListener(new MouseAdapter() {
			@Override
			public boolean mouseClicked(GComponent source, int button) {
				if (button < 2) {
					return true;
				}
				return false;
			}
		});

		minimap = new GMinimap(controller.getWorld().getMinimap());
		minimap.setPos(8, 8);
		minimap.setSize(256, 256);

		add(selectionPanel);
		add(buttonBuilding);
		add(buttonEntity);
		add(minimap);
	}

	public void reloadBuildingList() {
		selectionPanel.reloadBuildingList();
	}

	/**
	 * Calculates and sends the position the camera is looking.
	 * This position represents the center around which the map should be rendered
	 */
	public void updateCenterOfRendering() {

		if (!getController().getWorld().isLoaded()) {
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
		buttonBuilding.setSize(210, 40);
		buttonBuilding.setPos(700, 30);

		buttonEntity.setSize(210, 40);
		buttonEntity.setPos(700, 80);

		selectionPanel.setSize(screenWidth, 170);
		selectionPanel.setPos(0, 0);
		selectionPanel.resize();

		repositionBuildingGui();
		repositionEntityGui();
	}

	@Override
	public void render() {
		super.render();
		if (controller.getWorld().getSelectedBuilding() != null) {
			controller.getWorld().getSelectedBuilding().getGui().render();
		}
		if (controller.getWorld().getSelectedEntity() != null) {
			controller.getWorld().getSelectedEntity().getGui().render();
		}
	}

	@Override
	public void update() {
		super.update();
		if (controller.getWorld().getSelectedBuilding() != null) {
			controller.getWorld().getSelectedBuilding().getGui().update();
		}
		if (controller.getWorld().getSelectedEntity() != null) {
			controller.getWorld().getSelectedEntity().getGui().update();
		}
	}

	private void repositionBuildingGui() {
		ClientBuilding building = controller.getWorld().getSelectedBuilding();
		if (building != null) {
			building.getGui().setPos(panel.getSizeX() - building.getGui().getSizeX(), selectionPanel.getSizeY());
		}
	}

	private void repositionEntityGui() {
		ClientEntity entity = controller.getWorld().getSelectedEntity();
		if (entity != null) {
			entity.getGui().setPos(panel.getSizeX() - entity.getGui().getSizeX(), selectionPanel.getSizeY());
		}
	}

	public void reset() {
		selectionPanel.reset();
		if (controller.getWorld().isLoaded()) {
			camManager.setBounds(-30, -30, controller.getWorld().getMap().getSizeX() + 30, controller.getWorld().getMap().getSizeY() + 30);
		}
	}

}
