package game6.client.gui.components;

import game6.client.gui.listener.KeyboardListener;
import game6.client.gui.listener.MouseListener;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

public abstract class GComponent {

	private int posX, posY, sizeX, sizeY;
	private boolean focused;

	private boolean wasHovered;

	private List<MouseListener> mouseListener = new ArrayList<>();
	private List<KeyboardListener> keyboardListener = new ArrayList<>();

	public GComponent() {
		init();
		focused = false;
		wasHovered = false;
	}

	public void init() {
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPosY() {
		return posY;
	}

	public void setPos(int posX, int posY) {
		setPosX(posX);
		setPosY(posY);
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSize(int sizeX, int sizeY) {
		setSizeX(sizeX);
		setSizeY(sizeY);
	}

	public void unfocus() {
		focused = false;
		onUnfocus();
	}

	public void focus() {
		focused = true;
		onFocus();
	}

	public boolean isFocused() {
		return focused;
	}

	// //////////

	public boolean isCurrentlyHovered() {
		return Mouse.getX() > getPosX() && Mouse.getX() < getPosX() + getSizeX() &&
				Mouse.getY() > getPosY() && Mouse.getY() < getPosY() + getSizeY();
	}

	public void update() {

		boolean isCurrentlyHovered = isCurrentlyHovered();
		if (isCurrentlyHovered && !wasHovered) {
			notifyMouseEnteredListener();
		} else if (!isCurrentlyHovered && wasHovered) {
			notifyMouseLeftListener();
		}
		wasHovered = isCurrentlyHovered;

	}

	public abstract void render(int offsetX, int offsetY);
	public abstract void onFocus();
	public abstract void onUnfocus();

	// //////////

	public boolean addMouseListener(MouseListener listener) {
		return mouseListener.add(listener);
	}

	public boolean removeMouseListener(MouseListener listener) {
		return mouseListener.remove(listener);
	}

	public boolean addKeyboardListener(KeyboardListener listener) {
		return keyboardListener.add(listener);
	}

	public boolean removeKeyboardListener(KeyboardListener listener) {
		return keyboardListener.remove(listener);
	}

	protected void notifyMouseEnteredListener() {
		for (MouseListener listener : mouseListener) {
			listener.mouseEntered(this);
		}
	}

	protected void notifyMouseLeftListener() {
		for (MouseListener listener : mouseListener) {
			listener.mouseLeft(this);
		}
	}

	protected boolean notifyMouseClickedListener(int button) {
		for (MouseListener listener : mouseListener) {
			if (listener.mouseClicked(this, button)) {
				return true;
			}
		}
		return false;
	}

	protected boolean notifyMouseReleasedListener(int button) {
		focus();
		for (MouseListener listener : mouseListener) {
			if (listener.mouseReleased(this, button)) {
				return true;
			}
		}
		return false;
	}

	protected boolean notifyMouseWheelListener(int delta) {
		for (MouseListener listener : mouseListener) {
			if (listener.mouseWheel(this, delta)) {
				return true;
			}
		}
		return false;
	}

	protected boolean notifyMouseMovedListener(int dx, int dy) {
		for (MouseListener listener : mouseListener) {
			if (listener.mouseMoved(this, dx, dy)) {
				return true;
			}
		}
		return false;
	}

	protected boolean notifyKeyPressedListener(int keyCode, char key) {
		for (KeyboardListener listener : keyboardListener) {
			if (listener.keyPressed(this, keyCode, key)) {
				return true;
			}
		}
		return false;
	}

	protected boolean notifyKeyReleasedListener(int keyCode, char key) {
		for (KeyboardListener listener : keyboardListener) {
			if (listener.keyReleased(this, keyCode, key)) {
				return true;
			}
		}
		return false;
	}

}
