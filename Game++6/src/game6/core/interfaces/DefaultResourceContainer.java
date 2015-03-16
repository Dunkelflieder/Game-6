package game6.core.interfaces;

import game6.core.util.Resource;

public class DefaultResourceContainer implements ResourceContainer {

	private int capacity;
	private int[] content;

	public DefaultResourceContainer() {
		content = new int[Resource.values().length];
		setUnlimitedCapacity();
	}

	public DefaultResourceContainer(int capacity) {
		this();
		setCapacity(capacity);
	}

	public DefaultResourceContainer(int wood, int metal) {
		this();
		setResource(Resource.WOOD, wood);
		setResource(Resource.METAL, metal);
	}

	public DefaultResourceContainer(ResourceContainer container) {
		this();
		setCapacity(container.getCapacity());
		setResources(container);
	}

	@Override
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public int getResource(Resource resource) {
		return content[resource.ordinal()];
	}

	@Override
	public void setResource(Resource resource, int amount) {
		content[resource.ordinal()] = amount;
	}

}
