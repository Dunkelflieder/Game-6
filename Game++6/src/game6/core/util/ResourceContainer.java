package game6.core.util;

public class ResourceContainer {

	private int capacity;
	private int filled; // How much the container is filled in total in units. Didn't find a better word.
	private int[] contents;
	private ChangeCallback changeCallback;

	public interface ChangeCallback {
		public void onChange();
	}

	public ResourceContainer() {
		this.capacity = -1;
		this.filled = 0;
		contents = new int[Resource.values().length];
	}
	
	public ResourceContainer(int capacity) {
		this();
		setCapacity(capacity);
	}

	public ResourceContainer(int wood, int metal) {
		this();
		addResource(Resource.WOOD, wood);
		addResource(Resource.METAL, metal);
	}

	private void reloadFilled() {
		filled = 0;
		for (int load : contents) {
			filled += load;
		}
	}

	/**
	 * @return the amount of resources this container currently holds in total.
	 */
	public int getFilled() {
		return filled;
	}

	public void setChangeCallback(ChangeCallback callback) {
		changeCallback = callback;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setUnlimitedCapacity() {
		capacity = -1;
	}

	public boolean hasUnlimitedCapacity() {
		return capacity < 0;
	}

	/**
	 * @return the amount of resources this container can hold in total.
	 */
	public int getTotalCapacity() {
		return capacity;
	}

	/**
	 * @return the amount of resources that could still be added to this container until it's full.
	 */
	public int getFreeCapacity() {
		return hasUnlimitedCapacity() ? Integer.MAX_VALUE : (getTotalCapacity() - getFilled());
	}

	public boolean isFull() {
		return !hasUnlimitedCapacity() && getFreeCapacity() <= 0;
	}

	public boolean isEmpty() {
		return getFilled() == 0;
	}

	/**
	 * Empties the contents of this resource container.
	 */
	public void empty() {
		for (int i = 0; i < contents.length; i++) {
			contents[i] = 0;
		}
		reloadFilled();
	}

	/**
	 * @param resource The resource type of which to retrieve the amount
	 * @return the amount of that resource this container currently holds.
	 */
	public int getAmount(Resource resource) {
		return contents[resource.ordinal()];
	}

	/**
	 * Returns, whether this container holds at least as much resources as another one.
	 * @param otherContainer The resource container to compare to.
	 * @return true, only if this container has at least the same amount of each resource as the other container.
	 */
	public boolean contains(ResourceContainer otherContainer) {
		for (Resource resource : Resource.values()) {
			if (getAmount(resource) < otherContainer.getAmount(resource)) {
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
	public boolean equalsContentwise(ResourceContainer resources) {
		for (Resource resource : Resource.values()) {
			if (getAmount(resource) != resources.getAmount(resource)) {
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
	public ResourceContainer addResources(ResourceContainer resources) {
		ResourceContainer overload = new ResourceContainer();
		for (Resource resource : Resource.values()) {
			overload.addResource(resource, addResource(resource, resources.getAmount(resource)));
		}
		return overload;
	}

	/**
	 * Adds a resource to this container.
	 * @param resource The Resource-Type to add
	 * @param amount The amount to add to that resource.
	 * @return 0, if everything fit. Otherwise the amount that did not fit and therefore was not added.
	 */
	public int addResource(Resource resource, int amount) {
		if (amount == 0) {
			return 0;
		}
		if (hasUnlimitedCapacity()) {
			contents[resource.ordinal()] += amount;
			reloadFilled();
			if (changeCallback != null && amount > 0) {
				changeCallback.onChange();
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
				contents[resource.ordinal()] += amount;
				reloadFilled();
				if (changeCallback != null) {
					changeCallback.onChange();
				}
			}
			return overload;
		}
	}

	/**
	 * Removes a resource container's resources from this container.
	 * @param resources The resources to be removed.
	 * @return A container equally filled as the supplied one if everything got removed. Otherwise a lesser filled container containing the resources that were actually removed.
	 */
	public ResourceContainer removeResources(ResourceContainer resources) {
		ResourceContainer removed = new ResourceContainer();
		for (Resource resource : Resource.values()) {
			removed.addResource(resource, removeResource(resource, resources.getAmount(resource)));
		}
		return removed;
	}

	/**
	 * Removes a resource from this container.
	 * @param resource The Resource-Type to remove
	 * @param amount The amount to remove from that resource.
	 * @return the amount that was actually removed. Is only less than the supplied amount, if nothing more was available and amount is now 0.
	 */
	public int removeResource(Resource resource, int amount) {
		if (amount == 0) {
			return 0;
		}
		int underload = amount - contents[resource.ordinal()];
		if (underload > 0) {
			// underloaded
			amount -= underload;
		}
		if (amount > 0) {
			contents[resource.ordinal()] -= amount;
			reloadFilled();
			if (changeCallback != null) {
				changeCallback.onChange();
			}
		}
		return amount;
	}

	/**
	 * Sets this container's content to another one's. Does <b>not</b> do any checking whether this container's capacity would allow this. For utilities.
	 * @param resources The resource container with the contents.
	 */
	public void setResources(ResourceContainer resources) {
		for (Resource resource : Resource.values()) {
			setResource(resource, resources.getAmount(resource));
		}
	}

	/**
	 * Sets this container's resource amount to a specific value. Does <b>not</b> do any checking whether this container's capacity would allow this. For utilities.
	 * @param resource The resource to be set.
	 * @param amount the new amount.
	 */
	public void setResource(Resource resource, int amount) {
		contents[resource.ordinal()] = amount;
	}

	/**
	 * @return a new resource container with this container's capacity and contents. Does <b>not</b> clone callbacks.
	 */
	public ResourceContainer clone() {
		ResourceContainer cloned = new ResourceContainer();
		cloned.addResources(this);
		cloned.setCapacity(capacity);
		return cloned;
	}

}
