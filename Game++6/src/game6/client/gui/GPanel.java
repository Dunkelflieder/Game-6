package game6.client.gui;

import java.util.ArrayList;
import java.util.List;

public class GPanel extends GComponent {

	private List<GComponent> components = new ArrayList<>();

	public boolean add(GComponent component) {
		return components.add(component);
	}

	public boolean addAll(List<GComponent> components) {
		return this.components.addAll(components);
	}

	public boolean remove(GComponent component) {
		return components.remove(component);
	}

	public void removeAll() {
		components.clear();
	}

	@Override
	public void render(int offsetX, int offsetY) {
		for (GComponent component : components) {
			component.render(offsetX + getPosX(), offsetY + getPosY());
		}
	}
	
	@Override
	public void update() {
		super.update();
		for (GComponent component : components) {
			component.update();
		}
	}

}
