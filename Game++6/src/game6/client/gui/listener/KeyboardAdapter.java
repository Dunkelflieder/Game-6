package game6.client.gui.listener;

import game6.client.gui.components.GComponent;

/**
 * An abstract class, that implements a Keyboard listener and consumes nothing by default.
 * Can be useful if you only need to overwrite 1 method.
 * @author Felk
 *
 */
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
