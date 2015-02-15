package game6.client.gui.components;

import game6.client.gui.listener.ClickedListener;
import game6.client.gui.listener.MouseListener;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Gui-Component, which represents a button.
 * Offers a listener for click events.
 * @author Felk
 *
 */
public class GButton extends GComponent implements MouseListener {

	private List<ClickedListener> clickedListener = new ArrayList<>();

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
	public void setSize(int x, int y) {
		super.setSize(x, y);
		text.setSize(x, y);
		buttonImage.setSize(x, y);
	}

	@Override
	public void setOffset(int x, int y) {
		super.setOffset(x, y);
		text.setOffset(x, y);
		buttonImage.setOffset(x, y);
	}

	@Override
	public void setPos(int x, int y) {
		super.setPos(x, y);
		text.setPos(x + 10, y);
		buttonImage.setPos(x, y);
	}

	@Override
	public void render() {
		buttonImage.render();
		text.render();
	}

	public boolean addClickedListener(ClickedListener listener) {
		return clickedListener.add(listener);
	}

	public boolean removeClickedListener(ClickedListener listener) {
		return clickedListener.remove(listener);
	}

	private void notifyClickedListener() {
		for (ClickedListener listener : clickedListener) {
			listener.clicked(this);
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
			if (isHovered()) {
				notifyClickedListener();
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
