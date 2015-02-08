package game6.client.gui.listener;

import game6.client.gui.components.GComponent;

public abstract class KeyboardAdapter implements KeyboardListener {

	@Override
	public boolean keyPressed(GComponent source, int keyCode, char key) {
		return false;
	}

	@Override
	public boolean keyReleased(GComponent source, int keyCode, char key) {
		return false;
	}

}
