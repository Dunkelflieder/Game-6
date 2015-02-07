package game6.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GuiTitlescreen extends Gui {

	public static GuiTitlescreen instance = new GuiTitlescreen();

	private GColorfield background;
	private GLabel title, label;
	private GInput host;
	private GInputNumber port;
	private GButton connectButton;

	private List<ConnectListener> connectListeners = new ArrayList<>();

	public static interface ConnectListener {
		public void connect(String host, int port);
	}

	public boolean addConnectListener(ConnectListener listener) {
		return connectListeners.add(listener);
	}

	public boolean removeConnectListener(ConnectListener listener) {
		return connectListeners.remove(listener);
	}

	private void notifyConnectListeners(String host, int port) {
		for (ConnectListener listener : connectListeners) {
			listener.connect(host, port);
		}
	}

	@Override
	public void init() {
		background = new GColorfield(new Color(0, 0, 160));

		title = new GLabel("Dies ist der Titelbildschirm");
		title.setAlignment(Font.CENTER);

		label = new GLabel("Host und Port:");
		label.setAlignment(Font.RIGHT);

		host = new GInput("localhost");
		port = new GInputNumber(4200);

		connectButton = new GButton("Verbinden");
		connectButton.addButtonClickedListener(() -> notifyConnectListeners(host.getText(), port.getNumber()));

		add(background);
		add(title);
		add(label);
		add(host);
		add(port);
		add(connectButton);
	}

	@Override
	public void onSelect() {
	}

	@Override
	public void onDeselect() {
	}

	@Override
	public void onResize(int screenWidth, int screenHeight) {

		background.setPos(0, 0);
		background.setSize(screenWidth, screenWidth);

		title.setPos(0, screenHeight - 100);
		title.setSize(screenWidth, 80);

		label.setSize(0, 60);
		label.setPos(screenWidth / 2, screenHeight / 2);

		host.setSize(220, 60);
		host.setPos(screenWidth / 2, screenHeight / 2);

		port.setSize(120, 60);
		port.setPos(screenWidth / 2 + 225, screenHeight / 2);

		connectButton.setSize(220, 60);
		connectButton.setPos(screenWidth / 2, screenHeight / 2 - 65);

	}

}
