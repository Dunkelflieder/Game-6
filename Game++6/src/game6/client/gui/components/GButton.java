package game6.client.gui.components;

import game6.client.gui.components.listener.ButtonClickedListener;
import game6.client.gui.components.listener.MouseListener;

import java.util.ArrayList;
import java.util.List;

public class GButton extends GComponent implements MouseListener {

	private List<ButtonClickedListener> buttonClickedListener = new ArrayList<>();

	public final GLabel text;
	private GImage buttonImage;

	public GButton() {
		addMouseListener(this);
		text = new GLabel();
		// TODO make and use proper button texture
		buttonImage = new GImage("res/terrain/grass.png");
		// TODO add hover and click textures
	}

	public GButton(String text) {
		this();
		this.text.setText(text);
	}

	@Override
	public void render(int offsetX, int offsetY) {
		buttonImage.render(offsetX, offsetY);
		text.render(offsetX, offsetY);
	}

	public boolean addButtonClickedListener(ButtonClickedListener listener) {
		return buttonClickedListener.add(listener);
	}

	public boolean removeButtonClickedListener(ButtonClickedListener listener) {
		return buttonClickedListener.remove(listener);
	}

	private void notifyButtonClickedListener(int button) {
		for (ButtonClickedListener listener : buttonClickedListener) {
			listener.buttonClicked(button);
		}
	}

	@Override
	public void mouseEntered() {
	}

	@Override
	public void mouseLeft() {
	}

	@Override
	public void mouseClicked(int button) {
		notifyButtonClickedListener(button);
	}

	@Override
	public void mouseReleased(int button) {
	}

}
