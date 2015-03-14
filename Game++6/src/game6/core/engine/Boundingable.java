package game6.core.engine;

public interface Boundingable {

	public BoundingAABB getBounding();
	
	default boolean intersects(Boundingable other) {
		return other.getBounding().intersects(getBounding());
	}
	
}
