package game6.client.gui.listener;

import game6.client.gui.components.GComponent;

/**
 * A listener for mouse events.
 * @author Felk
 *
 */
public interface MouseListener {

	public void mouseEntered(GComponent source);

	public void mouseLeft(GComponent source);

	public boolean mouseClicked(GComponent source, int button);

	public boolean mouseReleased(GComponent source, int button);

	public boolean mouseWheel(GComponent source, int delta);

	public boolean mouseMoved(GComponent source, int dx, int dy);

}
