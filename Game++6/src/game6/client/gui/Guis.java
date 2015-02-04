package game6.client.gui;

public enum Guis {

	START(GuiStart.instance);

	private Gui gui;
	private static Guis selectedGui = START;

	Guis(Gui gui) {
		this.gui = gui;
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
