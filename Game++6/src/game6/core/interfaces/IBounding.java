package game6.core.interfaces;

public interface IBounding {

	public BoundingAABB getBounding();
	
	default boolean intersects(IBounding other) {
		return other.getBounding().intersects(getBounding());
	}
	
}
