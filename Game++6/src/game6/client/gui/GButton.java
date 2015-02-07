package game6.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GButton extends GComponent implements MouseListener {

	private List<ButtonClickedListener> buttonClickedListener = new ArrayList<>();

	public GLabel text;
	private GMultiimage buttonImage;

	public GButton(String text) {
		this.text.setText(text);
	}

	@Override
	public void init() {
		addMouseListener(this);
		text = new GLabel();
		text.setColor(Color.BLACK);
		buttonImage = new GMultiimage("res/buttons.png", 1, 4);
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
		text.render(getPosX() + offsetX + 10, getPosY() + offsetY);
	}

	public boolean addButtonClickedListener(ButtonClickedListener listener) {
		return buttonClickedListener.add(listener);
	}

	public boolean removeButtonClickedListener(ButtonClickedListener listener) {
		return buttonClickedListener.remove(listener);
	}

	private void notifyButtonClickedListener() {
		for (ButtonClickedListener listener : buttonClickedListener) {
			listener.buttonClicked();
		}
	}

	@Override
	public void mouseEntered(GComponent source) {
		buttonImage.setIndex(1);
	}

	@Override
	public void mouseLeft(GComponent source) {
		buttonImage.setIndex(0);
	}

	@Override
	public boolean mouseClicked(GComponent source, int button) {
		if (button == 0) {
			buttonImage.setIndex(2);
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseReleased(GComponent source, int button) {
		if (button == 0) {
			if (isCurrentlyHovered()) {
				notifyButtonClickedListener();
			}
			buttonImage.setIndex(0);
			return true;
		}
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

	@Override
	public void onFocus() {
	}

	@Override
	public void onUnfocus() {
	}

}
