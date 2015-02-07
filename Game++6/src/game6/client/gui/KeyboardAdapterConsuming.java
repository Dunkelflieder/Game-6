package game6.client.gui;

public class KeyboardAdapterConsuming implements KeyboardListener {

	@Override
	public boolean keyPressed(GComponent source, int keyCode, char key) {
		return true;
	}

	@Override
	public boolean keyReleased(GComponent source, int keyCode, char key) {
		return true;
	}

}
