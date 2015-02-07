package game6.client.gui.listener;

import game6.client.gui.components.GComponent;

public class MouseAdapter implements MouseListener {

	@Override
	public void mouseEntered(GComponent source) {
	}

	@Override
	public void mouseLeft(GComponent source) {
	}

	@Override
	public boolean mouseClicked(GComponent source, int button) {
		return false;
	}

	@Override
	public boolean mouseReleased(GComponent source, int button) {
		return false;
	}

	@Override
	public boolean mouseWheel(GComponent source, int delta) {
		return false;
	}

	@Override
	public boolean mouseMoved(GComponent source, int dx, int dy) {
		return false;
	}

}
