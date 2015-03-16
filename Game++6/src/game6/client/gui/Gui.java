package game6.client.gui;

import game6.client.Controller;
import game6.client.gui.components.GComponent;
import game6.client.gui.components.GPanel;
import game6.client.gui.listener.GuiListener;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public abstract class Gui {

	protected Controller controller;
	protected GPanel panel;
	private List<GuiListener> guiListeners = new ArrayList<>();

	public Gui() {
		panel = new GPanel();
	}

	public Controller getController() {
		return controller;
	}

	public abstract void initComponents();

	public void init(Controller controller) {
		this.controller = controller;
		initComponents();
	}

	public boolean add(GComponent component) {
		return panel.add(component);
	}

	public boolean remove(GComponent component) {
		return panel.remove(component);
	}

	public void removeAll() {
		panel.removeAll();
	}

	public boolean addGuiListener(GuiListener listener) {
		return guiListeners.add(listener);
	}

	public boolean removeGuiListener(GuiListener listener) {
		return guiListeners.remove(listener);
	}

	public void removeAllGuiListeners() {
		guiListeners.clear();
	}

	private void notifyGuiSelectedListeners() {
		for (GuiListener listener : guiListeners) {
			listener.guiSelected();
		}
	}

	private void notifyGuiDeselectedListeners() {
		for (GuiListener listener : guiListeners) {
			listener.guiDeselected();
		}
	}

	public void render() {
		panel.render();
	}

	public void update() {

		panel.update();

		while (Mouse.next()) {
			if (Mouse.getEventButton() >= 0) {
				if (panel.isHovered()) {
					if (Mouse.getEventButtonState()) {
						panel.notifyMouseClickedListener(Mouse.getEventButton());
					} else {
						panel.notifyMouseReleasedListener(Mouse.getEventButton());
					}
				}
			}
		}

		int dw = Mouse.getDWheel();
		if (dw != 0) {
			panel.notifyMouseWheelListener(dw);
		}

		int dx = Mouse.getDX();
		int dy = Mouse.getDY();
		if (dx != 0 || dy != 0) {
			panel.notifyMouseMovedListener(dx, dy);
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				panel.notifyKeyPressedListener(Keyboard.getEventKey(), Keyboard.getEventCharacter());
			} else {
				panel.notifyKeyReleasedListener(Keyboard.getEventKey(), Keyboard.getEventCharacter());
			}
		}

	}

	public abstract void onSelect();

	public abstract void onDeselect();

	public void select() {
		notifyGuiSelectedListeners();
	}

	public void deselect() {
		notifyGuiDeselectedListeners();
	}

	public void resize(int screenWidth, int screenHeight) {
		panel.setSize(screenWidth, screenHeight);
		onResize(screenWidth, screenHeight);
	}

	public abstract void onResize(int screenWidth, int screenHeight);

}
