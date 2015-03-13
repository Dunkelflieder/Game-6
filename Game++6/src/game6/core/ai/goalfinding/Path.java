package game6.core.ai.goalfinding;

import game6.core.buildings.ICoreBuilding;

import java.util.List;

public class Path {

	private List<ICoreBuilding> waypoints;

	public Path(List<ICoreBuilding> waypoints) {
		this.waypoints = waypoints;
	}

	public ICoreBuilding getGoal() {
		if (waypoints.size() == 0) {
			return null;
		}
		return waypoints.get(waypoints.size() - 1);
	}
	
	public int size() {
		return waypoints.size();
	}
	
	public List<ICoreBuilding> getWaypoints() {
		return waypoints;
	}

}
