package game6.client.gui.listener;

import game6.client.gui.components.GComponent;

/**
 * An abstract class, that implements a Keyboard listener and consumes all inputs by default.
 * Can be useful if you want to define an non-interactive portion in a gui.
 * @author Felk
 *
 */
public abstract class KeyboardAdapterConsuming implements KeyboardListener {

	@Override
	public boolean keyPressed(GComponent source, int keyCode, char key) {
		return true;
	}

	@Override
	public boolean keyReleased(GComponent source, int keyCode, char key) {
		return true;
	}

}
