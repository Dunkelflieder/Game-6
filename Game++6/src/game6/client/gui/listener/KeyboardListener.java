package game6.client.gui.listener;

import game6.client.gui.components.GComponent;

public interface KeyboardListener {

	public boolean keyPressed(GComponent source, int keyCode, char key);
	public boolean keyReleased(GComponent source, int keyCode, char key);
	
}
