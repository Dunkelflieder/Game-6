package game6.core.engine;

import java.util.*;

public class IDList<T extends IDList.UniqueID> implements Iterable<T> {

	private List<T> items;

	public interface UniqueID {
		public long getID();
	}

	public IDList() {
		items = new ArrayList<>();
	}

	public void add(T entity) {
		items.add(entity);
		items.sort((a, b) -> (int) (a.getID() - b.getID()));
	}

	/*public List<BaseEntity<T>> getEntitiesWithin(T posSmall, T posBig) {
		List<BaseEntity<T>> candidates = new ArrayList<BaseEntity<T>>();

		entityLoop: for (BaseEntity<T> entity : entities) {
			for (int i = 0; i < entity.getPosition().getComponentCount(); i++) {
				if (entity.getPosition().get(i) < posSmall.get(i) || entity.getPosition().get(i) > posBig.get(i)) continue entityLoop;
			}

			candidates.add(entity);
		}

		return candidates;
	}*/

	/*public List<T> getList() {
		return items;
	}*/

	private int getIndex(long id) {
		// binary serch
		int l = 0;
		int r = items.size() - 1;
		int p;
		while (l <= r) {
			p = (l + r) / 2;

			if (items.get(p).getID() == id) {
				return p;
			}
			if (items.get(p).getID() < id) {
				l = p + 1;
			} else {
				r = p - 1;
			}
		}

		return -1;
	}

	public T get(long id) {

		int index = getIndex(id);
		if (index < 0) {
			return null;
		}
		return items.get(index);

	}

	public boolean replace(long id, T newItem) {
		int index = getIndex(id);
		if (index < 0) {
			return false;
		}
		items.set(index, newItem);
		return true;
	}
	
	public void clear() {
		items.clear();
	}

	@Override
	public Iterator<T> iterator() {
		return items.iterator();
	}

}
