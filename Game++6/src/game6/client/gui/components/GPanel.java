package game6.client.gui.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Gui-Component, that can hold components itself and renders them offsetted by the panel's position.
 * Is a component itself and propergates events and stuff to all contained components.
 * Events are processed in reverse the order they are added, to have a front-to-back event consuming.
 * Last added and therefore last rendered components are handled as topmost.
 * @author Felk
 *
 */
public class GPanel extends GComponent {

	private class ListReverser<T> implements Iterable<T> {
		private ListIterator<T> listIterator;

		public ListReverser(List<T> wrappedList) {
			this.listIterator = wrappedList.listIterator(wrappedList.size());
		}

		public Iterator<T> iterator() {
			return new Iterator<T>() {

				public boolean hasNext() {
					return listIterator.hasPrevious();
				}

				public T next() {
					return listIterator.previous();
				}

				public void remove() {
					listIterator.remove();
				}
			};
		}
	}

	private void updateOffsets() {
		for (GComponent component : components) {
			component.setOffset(getPosX() + getOffsetX(), getPosY() + getOffsetY());
		}
	}

	@Override
	public void setOffset(int x, int y) {
		super.setOffset(x, y);
		updateOffsets();
	}

	@Override
	public void setPos(int x, int y) {
		super.setPos(x, y);
		updateOffsets();
	}

	private List<GComponent> components = new ArrayList<>();

	public boolean add(GComponent component) {
		component.setOffset(getPosX() + getOffsetX(), getPosY() + getOffsetY());
		return components.add(component);
	}

	public boolean remove(GComponent component) {
		return components.remove(component);
	}

	public void removeAll() {
		components.clear();
	}

	@Override
	public void render() {
		for (GComponent component : components) {
			component.render();
		}
	}

	@Override
	public void update() {
		super.update();
		for (GComponent component : components) {
			component.update();
		}
	}

	@Override
	public void notifyMouseEnteredListener() {
		for (GComponent component : new ListReverser<GComponent>(components)) {
			component.notifyMouseEnteredListener();
		}
		super.notifyMouseEnteredListener();
	}

	@Override
	public void notifyMouseLeftListener() {
		for (GComponent component : new ListReverser<GComponent>(components)) {
			component.notifyMouseLeftListener();
		}
		super.notifyMouseLeftListener();
	}

	@Override
	public boolean notifyMouseClickedListener(int button) {
		for (GComponent c : components) {
			c.unfocus();
		}
		for (GComponent component : new ListReverser<GComponent>(components)) {
			if (component.isHovered()) {
				if (component.notifyMouseClickedListener(button)) {
					return true;
				}
			}
		}
		if (super.notifyMouseClickedListener(button)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean notifyMouseReleasedListener(int button) {
		for (GComponent component : new ListReverser<GComponent>(components)) {
			if (component.isHovered()) {
				if (component.notifyMouseReleasedListener(button)) {
					return true;
				}
			}
		}
		if (super.notifyMouseReleasedListener(button)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean notifyMouseWheelListener(int delta) {
		for (GComponent component : new ListReverser<GComponent>(components)) {
			if (component.isHovered()) {
				if (component.notifyMouseWheelListener(delta)) {
					return true;
				}
			}
		}
		if (super.notifyMouseWheelListener(delta)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean notifyMouseMovedListener(int dx, int dy) {
		for (GComponent component : new ListReverser<GComponent>(components)) {
			if (component.isHovered()) {
				if (component.notifyMouseMovedListener(dx, dy)) {
					return true;
				}
			}
		}
		if (super.notifyMouseMovedListener(dx, dy)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean notifyKeyPressedListener(int keyCode, char key) {
		for (GComponent component : new ListReverser<GComponent>(components)) {
			if (component.notifyKeyPressedListener(keyCode, key)) {
				return true;
			}
		}
		if (super.notifyKeyPressedListener(keyCode, key)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean notifyKeyReleasedListener(int keyCode, char key) {
		for (GComponent component : new ListReverser<GComponent>(components)) {
			if (component.notifyKeyReleasedListener(keyCode, key)) {
				return true;
			}
		}
		if (super.notifyKeyReleasedListener(keyCode, key)) {
			return true;
		}
		return false;
	}

	@Override
	public void onFocus() {
	}

	@Override
	public void onUnfocus() {
	}
}
