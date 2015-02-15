package game6.client.gui.components;

import game6.client.gui.listener.KeyboardListener;
import game6.client.gui.listener.MouseListener;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

/**
 * Abstract Gui-Component class.
 * All components inherit from this class.
 * Offers mouse and keyboard listeners.
 * @author Felk
 *
 */
public abstract class GComponent {

	private int posX, posY, sizeX, sizeY, offsetX, offsetY;
	private boolean focused;

	private boolean wasHovered;

	private List<MouseListener> mouseListeners = new ArrayList<>();
	private List<KeyboardListener> keyboardListeners = new ArrayList<>();

	public GComponent() {
		init();
		focused = false;
		wasHovered = false;
		offsetX = 0;
		offsetY = 0;
	}

	public void init() {
	}

	public void setPos(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setSize(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setOffset(int x, int y) {
		this.offsetX = x;
		this.offsetY = y;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	/**
	 * Tells the component, that it is no longer focused. Should not be called manually.
	 */
	public void unfocus() {
		focused = false;
		onUnfocus();
	}

	/**
	 * Tells the component, that it is focused now. Should not be called manually.
	 */
	public void focus() {
		focused = true;
		onFocus();
	}

	/**
	 * Returns whether the component is currently focused or not.
	 * @return true, if it is focused. False otherwise.
	 */
	public boolean isFocused() {
		return focused;
	}

	// //////////

	/**
	 * Returns whether the component is currently hovered my the Mouse or not
	 * @return true, if the mouse is currently within the components bounds. False otherwise
	 */
	public boolean isHovered() {
		return Mouse.getX() > getPosX() + getOffsetX() && Mouse.getX() < getPosX() + getSizeX() + getOffsetX() &&
				Mouse.getY() > getPosY() + getOffsetY() && Mouse.getY() < getPosY() + getSizeY() + getOffsetY();
	}

	public void update() {

		// These are the only listeners, which are notified by the component itself (for now)
		// Determines, if the mouse left or entered the component's bounds.
		boolean isCurrentlyHovered = isHovered();
		if (isCurrentlyHovered && !wasHovered) {
			notifyMouseEnteredListener();
		} else if (!isCurrentlyHovered && wasHovered) {
			notifyMouseLeftListener();
		}
		wasHovered = isCurrentlyHovered;

	}

	public abstract void render();

	public abstract void onFocus();

	public abstract void onUnfocus();

	// //////////

	public boolean addMouseListener(MouseListener listener) {
		return mouseListeners.add(listener);
	}

	public void removeAllMouseListeners() {
		mouseListeners.clear();
	}

	public boolean addKeyboardListener(KeyboardListener listener) {
		return keyboardListeners.add(listener);
	}

	public void removeAllKeyboardListeners() {
		keyboardListeners.clear();
	}

	protected void notifyMouseEnteredListener() {
		for (MouseListener listener : mouseListeners) {
			listener.mouseEntered(this);
		}
	}

	protected void notifyMouseLeftListener() {
		for (MouseListener listener : mouseListeners) {
			listener.mouseLeft(this);
		}
	}

	protected boolean notifyMouseClickedListener(int button) {
		for (MouseListener listener : mouseListeners) {
			if (listener.mouseClicked(this, button)) {
				return true;
			}
		}
		return false;
	}

	protected boolean notifyMouseReleasedListener(int button) {
		focus();
		for (MouseListener listener : mouseListeners) {
			if (listener.mouseReleased(this, button)) {
				return true;
			}
		}
		return false;
	}

	protected boolean notifyMouseWheelListener(int delta) {
		for (MouseListener listener : mouseListeners) {
			if (listener.mouseWheel(this, delta)) {
				return true;
			}
		}
		return false;
	}

	protected boolean notifyMouseMovedListener(int dx, int dy) {
		for (MouseListener listener : mouseListeners) {
			if (listener.mouseMoved(this, dx, dy)) {
				return true;
			}
		}
		return false;
	}

	protected boolean notifyKeyPressedListener(int keyCode, char key) {
		for (KeyboardListener listener : keyboardListeners) {
			if (listener.keyPressed(this, keyCode, key)) {
				return true;
			}
		}
		return false;
	}

	protected boolean notifyKeyReleasedListener(int keyCode, char key) {
		for (KeyboardListener listener : keyboardListeners) {
			if (listener.keyReleased(this, keyCode, key)) {
				return true;
			}
		}
		return false;
	}

}
