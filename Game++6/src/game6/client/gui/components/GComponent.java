package game6.client.gui.components;

import game6.client.gui.components.listener.MouseListener;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

public abstract class GComponent {

	private int posX, posY, sizeX, sizeY;
	private boolean consumesInput;

	private boolean isHovered;
	private boolean[] mouseButtonDown;

	private List<MouseListener> mouseListener = new ArrayList<>();

	public GComponent() {
		init();
		setPos(0, 0);
		setSize(0, 0);
		setConsumesInput(false);
		isHovered = false;
		mouseButtonDown = new boolean[3];
		mouseButtonDown[0] = false;
		mouseButtonDown[1] = false;
		mouseButtonDown[2] = false;
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

	public void setConsumesInput(boolean consumesInput) {
		this.consumesInput = consumesInput;
	}

	public boolean consumesInput() {
		return consumesInput;
	}

	// //////////

	private boolean isCurrentlyHovered() {
		return Mouse.getX() > getPosX() && Mouse.getX() < getPosX() + getSizeX() &&
				Mouse.getY() > getPosY() && Mouse.getY() < getPosY() + getSizeY();
	}

	public void update() {

		boolean isCurrentlyHovered = isCurrentlyHovered();
		if (isCurrentlyHovered && !isHovered) {
			notifyMouseEnteredListener();
		} else if (!isCurrentlyHovered && isHovered) {
			notifyMouseLeftListener();
		}
		isHovered = isCurrentlyHovered;

		for (int i = 0; i < mouseButtonDown.length; i++) {
			if (isHovered && Mouse.isButtonDown(i) && !mouseButtonDown[i]) {
				notifyMouseClickedListener(i);
			} else if (!Mouse.isButtonDown(i) && mouseButtonDown[i]) {
				notifyMouseReleasedListener(i);
			}
			mouseButtonDown[i] = Mouse.isButtonDown(i);
		}

	}
	
	public abstract void render(int offsetX, int offsetY);

	// //////////

	public boolean addMouseListener(MouseListener listener) {
		return mouseListener.add(listener);
	}

	public boolean removeMouseListener(MouseListener listener) {
		return mouseListener.remove(listener);
	}

	private void notifyMouseEnteredListener() {
		for (MouseListener listener : mouseListener) {
			listener.mouseEntered();
		}
	}

	private void notifyMouseLeftListener() {
		for (MouseListener listener : mouseListener) {
			listener.mouseLeft();
		}
	}

	private void notifyMouseClickedListener(int button) {
		for (MouseListener listener : mouseListener) {
			listener.mouseClicked(button);
		}
	}

	private void notifyMouseReleasedListener(int button) {
		for (MouseListener listener : mouseListener) {
			listener.mouseReleased(button);
		}
	}

}
