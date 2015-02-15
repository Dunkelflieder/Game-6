package game6.client.gui;

import game6.client.Controller;
import de.nerogar.render.GameDisplay;

/**
 * Basically the static Gui-Manager class. Contains all GUIs and offers method to select another GUI
 * @author Felk
 *
 */
public enum Guis {

	TITLESCREEN(GuiTitlescreen.instance),
	INGAME(GuiIngame.instance),
	PAUSE(GuiPause.instance);

	private Gui gui;
	private static Guis selectedGui = TITLESCREEN;

	Guis(Gui gui) {
		this.gui = gui;
	}

	public static void init(GameDisplay display, Controller controller) {
		for (Guis gui : values()) {
			gui.getGui().init(display, controller);
		}
	}

	public Gui getGui() {
		return gui;
	}

	public static void render() {
		selectedGui.getGui().render();
	}

	public static void update() {
		selectedGui.getGui().update();
	}

	public static void select(Guis gui) {
		selectedGui.getGui().deselect();
		selectedGui = gui;
		selectedGui.getGui().select();
	}

	public static void resize(int screenWidth, int screenHeight) {
		for (Guis gui : values()) {
			gui.getGui().resize(screenWidth, screenHeight);
		}
	}
}
