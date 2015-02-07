package game6.core.buildings;

import game6.core.events.Event;
import game6.core.events.ShockEvent;

import java.util.List;

public class CoreBuildingReactor extends CoreBuilding {

	private int tick = 0;
	private int shockCooldown = 30;
	private int shockPower = 10;
	private int shockRadius = 10;
	
	public CoreBuildingReactor(int id) {
		super(id, 2, 2);
	}

	@Override
	public void update(List<Event> events) {
		
		tick++;
		
		if (tick % shockCooldown == 0) {
			tick = 0;
			System.out.println("Shocking!");
			events.add(new ShockEvent(this, shockPower, shockRadius));
		}
		
	}
	
	public int getShockCooldown() {
		return shockCooldown;
	}
	
	public int getShockPower() {
		return shockPower;
	}
	
}
