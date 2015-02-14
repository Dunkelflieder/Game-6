package game6.client.gui;

import game6.client.Controller;
import game6.client.gui.components.*;
import game6.client.gui.listener.KeyboardAdapter;
import game6.client.gui.listener.MouseAdapter;
import game6.client.world.World;
import game6.core.buildings.BuildingType;
import game6.core.buildings.CoreBuilding;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

import de.nerogar.render.Camera;
import de.nerogar.render.GameDisplay;
import de.nerogar.util.*;

public class GuiIngame extends Gui {

	public static GuiIngame instance = new GuiIngame();
	private GLabel title;
	private GButton buttonBuilding;
	private GColorfield highlight;
	private CoreBuilding preview;
	private GPanelBuilding buildingPanel;

	private List<GBuilding> buildings;
	private BuildingType selectedBuilding;

	private int mapX, mapY;

	// 0 = not grabbed, 1 = grabbed for movement, 2 = grabbed for rotation
	private int grabbed = 0;

	@Override
	public void initComponents() {

		title = new GLabel("Ingame-Gui");

		buttonBuilding = new GButton("Place random building");
		buttonBuilding.text.setColor(Color.BLACK);
		buttonBuilding.addClickedListener(source -> {
			controller.placeBuilding(BuildingType.getRandom(), (int) (Math.random() * (controller.getWorld().getMap().getSizeX() - 2)), (int) (Math.random() * (controller.getWorld().getMap().getSizeY() - 2)));
		});

		highlight = new GColorfield(new Color(0f, 1f, 0f, 0.5f));

		buildingPanel = new GPanelBuilding(controller);

		// clicks on map && camera movement
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public boolean mouseWheel(GComponent source, int delta) {
				// Zoom in or out
				controller.getCamera().setY(MathHelper.clamp(controller.getCamera().getY() - 0.01f * (delta), 2f, 42f));
				updateAt();
				return true;
			}

			@Override
			public boolean mouseReleased(GComponent source, int button) {
				if (button == 2) {
					grabbed = 0;
					return true;
				}
				return false;
			}

			@Override
			public boolean mouseClicked(GComponent source, int button) {
				if (button == 2) {
					if ((Mouse.getY() < Display.getHeight() * 0.3 && grabbed != 1) || grabbed == 2) {
						grabbed = 2;
						return true;
					} else if (grabbed != 2) {
						grabbed = 1;
						return true;
					}
				} else if (button == 1) {
					selectBuilding(null);
				} else if (button == 0) {
					if (selectedBuilding != null) {
						controller.placeBuilding(selectedBuilding, preview.getPosX(), preview.getPosY());
						return true;
					} else {
						CoreBuilding building = controller.getWorld().getMap().getBuildingAt(mapX, mapY);
						buildingPanel.setBuilding(building);
					}
				}
				return false;
			}

			@Override
			public boolean mouseMoved(GComponent source, int dx, int dy) {

				World world = controller.getWorld();
				if (!world.isReady()) {
					return false;
				}

				// TODO don't hardcode fov
				controller.getInputHandler().updateMousePositions(controller.getCamera(), 90);
				Ray mouseRay = controller.getInputHandler().getMouseRay();
				RayIntersection intersection = world.getPhysicsSpace().getIntersecting(mouseRay);

				if (intersection != null && intersection.intersectionPoint.getX() < world.getMap().getSizeX() && intersection.intersectionPoint.getZ() < world.getMap().getSizeY()) {
					mapX = (int) intersection.intersectionPoint.getX();
					mapY = (int) intersection.intersectionPoint.getZ();
					if (selectedBuilding != null) {
						preview.setPosX(MathHelper.clamp(mapX, 0, world.getMap().getSizeX() - preview.getSizeX()));
						preview.setPosY(MathHelper.clamp(mapY, 0, world.getMap().getSizeY() - preview.getSizeY()));
					}
				}

				if (grabbed == 1) {
					float slow = 0.01f * controller.getCamera().getY();
					float angle = (float) (controller.getCamera().getYaw() / (180f / Math.PI));

					controller.getCamera().setX(controller.getCamera().getX() - (float) (slow * dx * Math.cos(angle) + slow * dy * Math.sin(angle)));
					controller.getCamera().setZ(controller.getCamera().getZ() - (float) (slow * dx * Math.sin(angle) - slow * dy * Math.cos(angle)));

				} else if (grabbed == 2) {
					controller.getCamera().setYaw(controller.getCamera().getYaw() + dx * 0.5f);
				}

				updateAt();

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

		add(title);
		add(buttonBuilding);
		add(highlight);
		add(buildingPanel);
	}

	public void updateAt() {

		float pitch = controller.getCamera().getPitch();
		controller.getCamera().setPitch(pitch * 0.3f);

		Ray cameraRay = controller.getCamera().getCameraRay();
		Vector3f start = (Vector3f) cameraRay.getStart();
		Vector3f dir = (Vector3f) cameraRay.getDirection();
		dir.multiply(- start.getY() / dir.getY());
		start.add(dir);

		controller.getWorld().getMap().setAt((int) start.getX(), (int) start.getZ());
		controller.getCamera().setPitch(pitch);
	}

	public void reset() {
		selectBuilding(null);
	}

	private void selectBuilding(BuildingType building) {
		this.selectedBuilding = building;
		controller.getWorld().getMap().setGridActivated(building != null);
		if (building != null) {
			preview = building.getClientBuilding(0);
		} else {
			preview = null;
		}
		controller.getWorld().getMap().setBuildingPreview(preview);
		updateHighlight();
	}

	@Override
	public void init(GameDisplay display, Controller controller) {
		super.init(display, controller);
		buildings = new ArrayList<>();
		for (BuildingType type : BuildingType.values()) {
			GBuilding gBuilding = new GBuilding(display, type);
			buildings.add(gBuilding);
			add(gBuilding);
			gBuilding.addClickedListener(source -> {
				selectBuilding(((GBuilding) source).getBuildingType());
			});
		}
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
		title.setSize(100, 30);
		title.setPos(20, screenHeight - 50);

		buttonBuilding.setSize(310, 40);
		buttonBuilding.setPos(20, 20);

		buildingPanel.setSize(300, 500);
		buildingPanel.setPos(screenWidth - 300, 0);
		buildingPanel.resize();

		int offsetX = 350;
		for (GBuilding building : buildings) {
			building.setSize(128, 128);
			building.setPos(offsetX, 0);
			offsetX += 130;
		}
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

}
