package game6.client.gui;

public interface MouseListener {

	public void mouseEntered(GComponent source);
	public void mouseLeft(GComponent source);
	public boolean mouseClicked(GComponent source, int button);
	public boolean mouseReleased(GComponent source, int button);
	public boolean mouseWheel(GComponent source, int delta);
	public boolean mouseMoved(GComponent source, int dx, int dy);
	
}
