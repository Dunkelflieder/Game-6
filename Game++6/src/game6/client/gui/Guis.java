package game6.client.gui;

public enum Guis {

	TITLESCREEN(GuiTitlescreen.instance),
	INGAME(GuiIngame.instance);

	private Gui gui;
	private static Guis selectedGui = TITLESCREEN;

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
