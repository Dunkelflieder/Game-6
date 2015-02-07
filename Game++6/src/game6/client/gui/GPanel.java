package game6.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

	private List<GComponent> components = new ArrayList<>();

	public boolean add(GComponent component) {
		return components.add(component);
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

	@Override
	protected void notifyMouseEnteredListener() {
		for (GComponent component : new ListReverser<GComponent>(components)) {
			component.notifyMouseEnteredListener();
		}
		super.notifyMouseEnteredListener();
	}

	@Override
	protected void notifyMouseLeftListener() {
		for (GComponent component : new ListReverser<GComponent>(components)) {
			component.notifyMouseLeftListener();
		}
		super.notifyMouseLeftListener();
	}

	@Override
	protected boolean notifyMouseClickedListener(int button) {
		for (GComponent c : components) {
			c.unfocus();
		}
		for (GComponent component : new ListReverser<GComponent>(components)) {
			if (component.isCurrentlyHovered()) {
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
	protected boolean notifyMouseReleasedListener(int button) {
		for (GComponent component : new ListReverser<GComponent>(components)) {
			if (component.isCurrentlyHovered()) {
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
	protected boolean notifyMouseWheelListener(int delta) {
		for (GComponent component : new ListReverser<GComponent>(components)) {
			if (component.isCurrentlyHovered()) {
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
	protected boolean notifyMouseMovedListener(int dx, int dy) {
		for (GComponent component : new ListReverser<GComponent>(components)) {
			if (component.isCurrentlyHovered()) {
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
	protected boolean notifyKeyPressedListener(int keyCode, char key) {
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
	protected boolean notifyKeyReleasedListener(int keyCode, char key) {
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
