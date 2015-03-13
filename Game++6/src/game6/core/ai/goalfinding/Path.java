package game6.core.ai.goalfinding;

import game6.core.buildings.CoreBuilding;

import java.util.List;

public class Path {

	private List<CoreBuilding> waypoints;

	public Path(List<CoreBuilding> waypoints) {
		this.waypoints = waypoints;
	}

	public CoreBuilding getGoal() {
		if (waypoints.size() == 0) {
			return null;
		}
		return waypoints.get(waypoints.size() - 1);
	}
	
	public int size() {
		return waypoints.size();
	}
	
	public List<CoreBuilding> getWaypoints() {
		return waypoints;
	}

}
