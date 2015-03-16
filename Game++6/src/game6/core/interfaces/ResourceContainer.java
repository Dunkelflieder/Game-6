package game6.core.interfaces;

import game6.core.util.Resource;

public interface ResourceContainer {

	/**
	 * @return the amount of resources this container currently holds in total.
	 */
	default public int getFilled() {
		int filled = 0;
		for (Resource resource : Resource.values()) {
			filled += getResource(resource);
		}
		return filled;
	}

	public void setCapacity(int capacity);

	default public void setUnlimitedCapacity() {
		setCapacity(-1);
	}

	default public boolean hasUnlimitedCapacity() {
		return getCapacity() < 0;
	}

	/**
	 * @return the amount of resources this container can hold in total.
	 */
	public int getCapacity();

	/**
	 * @return the amount of resources that could still be added to this container until it's full.
	 */
	default public int getFreeCapacity() {
		return hasUnlimitedCapacity() ? Integer.MAX_VALUE : (getCapacity() - getFilled());
	}

	default public boolean isFull() {
		return !hasUnlimitedCapacity() && getFreeCapacity() <= 0;
	}

	default public boolean isEmpty() {
		return getFilled() == 0;
	}

	/**
	 * Empties the contents of this resource container.
	 */
	default public void empty() {
		for (Resource resource : Resource.values()) {
			setResource(resource, 0);
		}
	}

	/**
	 * @param resource The resource type of which to retrieve the amount
	 * @return the amount of that resource this container currently holds.
	 */
	public int getResource(Resource resource);

	/**
	 * Returns, whether this container holds at least as much resources as another one.
	 * @param otherContainer The resource container to compare to.
	 * @return true, only if this container has at least the same amount of each resource as the other container.
	 */
	default public boolean contains(ResourceContainer otherContainer) {
		for (Resource resource : Resource.values()) {
			if (getResource(resource) < otherContainer.getResource(resource)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns, whether this container is identically filled as another one. The containers can have different capacities though.
	 * @param resources The resource container to compare to
	 * @return true, only if the other and this container are filled with the same amount of each resource. 
	 */
	default public boolean equalsContentwise(ResourceContainer resources) {
		for (Resource resource : Resource.values()) {
			if (getResource(resource) != resources.getResource(resource)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Adds a resource container's resources to this container.
	 * @param resources The resources to add
	 * @return an empty container if everything fit. Otherwise a container with the amounts of resources that did not fit and were therefore not added.
	 */
	default public ResourceContainer addResources(ResourceContainer resources) {
		ResourceContainer overload = new DefaultResourceContainer();
		for (Resource resource : Resource.values()) {
			overload.addResource(resource, addResource(resource, resources.getResource(resource)));
		}
		return overload;
	}

	/**
	 * Adds a resource to this container.
	 * @param resource The Resource-Type to add
	 * @param amount The amount to add to that resource.
	 * @return 0, if everything fit. Otherwise the amount that did not fit and therefore was not added.
	 */
	default public int addResource(Resource resource, int amount) {
		if (amount == 0) {
			return 0;
		}
		if (hasUnlimitedCapacity()) {
			setResource(resource, getResource(resource) + amount);
			if (amount > 0) {
				resourceContentChanged();
			}
			return 0;
		} else {
			int overload = amount - getFreeCapacity();
			if (overload > 0) {
				// overloaded
				amount -= overload;
			} else {
				overload = 0;
			}
			if (amount > 0) {
				setResource(resource, getResource(resource) + amount);
				resourceContentChanged();
			}
			return overload;
		}
	}

	/**
	 * Removes a resource container's resources from this container.
	 * @param resources The resources to be removed.
	 * @return A container equally filled as the supplied one if everything got removed. Otherwise a lesser filled container containing the resources that were actually removed.
	 */
	default public ResourceContainer removeResources(ResourceContainer resources) {
		ResourceContainer removed = new DefaultResourceContainer();
		for (Resource resource : Resource.values()) {
			removed.addResource(resource, removeResource(resource, resources.getResource(resource)));
		}
		return removed;
	}

	/**
	 * Removes a resource from this container.
	 * @param resource The Resource-Type to remove
	 * @param amount The amount to remove from that resource.
	 * @return the amount that was actually removed. Is only less than the supplied amount, if nothing more was available and amount is now 0.
	 */
	default public int removeResource(Resource resource, int amount) {
		if (amount == 0) {
			return 0;
		}
		int underload = amount - getResource(resource);
		if (underload > 0) {
			// underloaded
			amount -= underload;
		}
		if (amount > 0) {
			setResource(resource, getResource(resource) - amount);
			resourceContentChanged();
		}
		return amount;
	}

	/**
	 * Sets this container's content to another one's. Does <b>not</b> do any checking whether this container's capacity would allow this. For utilities.
	 * @param resources The resource container with the contents.
	 */
	default public void setResources(ResourceContainer resources) {
		for (Resource resource : Resource.values()) {
			setResource(resource, resources.getResource(resource));
		}
	}

	/**
	 * Sets this container's resource amount to a specific value. Does <b>not</b> do any checking whether this container's capacity would allow this. For utilities.
	 * @param resource The resource to be set.
	 * @param amount the new amount.
	 */
	public void setResource(Resource resource, int amount);

	default public void resourceContentChanged() {
	}
	
}
