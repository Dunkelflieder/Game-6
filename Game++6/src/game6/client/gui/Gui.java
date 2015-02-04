package game6.client.gui;

import game6.client.gui.components.GComponent;
import game6.client.gui.components.GPanel;
import game6.client.gui.components.listener.GuiListener;

import java.util.ArrayList;
import java.util.List;

public abstract class Gui {

	private GPanel panel;
	private List<GuiListener> guiListeners = new ArrayList<>();

	public Gui() {
		panel = new GPanel();
		init();
	}
	
	public abstract void init();

	public boolean add(GComponent component) {
		return panel.add(component);
	}

	public boolean addAll(List<GComponent> components) {
		return panel.addAll(components);
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
		panel.render(0, 0);
	}
	
	public void update() {
		panel.update();
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
		panel.setSizeX(screenWidth);
		panel.setSizeY(screenHeight);
		onResize(screenWidth, screenHeight);
	}
	
	public abstract void onResize(int screenWidth, int screenHeight);

}
