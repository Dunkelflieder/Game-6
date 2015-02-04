package game6.client.gui.components;

import game6.client.gui.components.listener.ButtonClickedListener;
import game6.client.gui.components.listener.MouseListener;

import java.util.ArrayList;
import java.util.List;

public class GButton extends GComponent implements MouseListener {

	private List<ButtonClickedListener> buttonClickedListener = new ArrayList<>();

	public GLabel text;
	private GImage buttonImage;

	public GButton(String text) {
		this.text.setText(text);
	}

	@Override
	public void init() {
		addMouseListener(this);
		text = new GLabel();
		// TODO make and use proper button texture
		buttonImage = new GImage("res/terrain/grass.png");
		// TODO add hover and click textures
	}

	@Override
	public void setSizeX(int x) {
		super.setSizeX(x);
		text.setSizeX(x);
		buttonImage.setSizeX(x);
	}

	@Override
	public void setSizeY(int y) {
		super.setSizeY(y);
		text.setSizeY(y);
		buttonImage.setSizeY(y);
	}

	@Override
	public void setSize(int x, int y) {
		setSizeX(x);
		setSizeY(y);
	}

	@Override
	public void render(int offsetX, int offsetY) {
		buttonImage.render(getPosX() + offsetX, getPosY() + offsetY);
		text.render(getPosX() + offsetX, getPosY() + offsetY);
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
