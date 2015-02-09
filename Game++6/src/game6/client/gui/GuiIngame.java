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

import de.nerogar.render.GameDisplay;
import de.nerogar.util.*;

public class GuiIngame extends Gui {

	public static GuiIngame instance = new GuiIngame();
	private GLabel title;
	private GButton buttonBuilding;
	private GColorfield highlight;
	private CoreBuilding preview;

	private List<GBuilding> buildings;
	private BuildingType selectedBuilding;

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

		initCameraMovementListener();

		// clicks on map
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public boolean mouseClicked(GComponent source, int button) {
				if (button == 0 && selectedBuilding != null) {
					controller.placeBuilding(selectedBuilding, preview.getPosX(), preview.getPosY());
					return true;
				}
				return false;
			}

			@Override
			public boolean mouseMoved(GComponent source, int dx, int dy) {
				if (selectedBuilding != null) {
					// TODO don't hardcode fov

					World world = controller.getWorld();

					controller.getInputHandler().updateMousePositions(controller.getCamera(), 90);
					Ray mouseRay = controller.getInputHandler().getMouseRay();
					RayIntersection intersection = world.getPhysicsSpace().getIntersecting(mouseRay);

					if (intersection != null && intersection.intersectionPoint.getX() < world.getMap().getSizeX() && intersection.intersectionPoint.getZ() < world.getMap().getSizeY()) {
						int clickX = (int) intersection.intersectionPoint.getX();
						int clickY = (int) intersection.intersectionPoint.getZ();
						preview.setPosX(MathHelper.clamp(clickX, 0, world.getMap().getSizeX() - preview.getSizeX()));
						preview.setPosY(MathHelper.clamp(clickY, 0, world.getMap().getSizeY() - preview.getSizeY()));
					}
					return true;
				}
				return false;
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

	private void initCameraMovementListener() {
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public boolean mouseWheel(GComponent source, int delta) {
				// Zoom in or out
				controller.getCamera().y = MathHelper.clamp(controller.getCamera().y - 0.01f * (delta), 2f, 15f);
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
			public boolean mouseMoved(GComponent source, int dx, int dy) {
				if (grabbed == 1) {
					float slow = 0.01f * controller.getCamera().y;
					float angle = (float) (controller.getCamera().yaw / (180f / Math.PI));

					controller.getCamera().x -= slow * dx * Math.cos(angle) + slow * dy * Math.sin(angle);
					controller.getCamera().z -= slow * dx * Math.sin(angle) - slow * dy * Math.cos(angle);
					return true;
				} else if (grabbed == 2) {
					controller.getCamera().yaw += dx * 0.5f;
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
				}
				return false;
			}
		});
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
