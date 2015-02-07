package game6.core.events;

import game6.core.networking.ServerThread;
import game6.core.world.CoreMap;

import java.util.List;

public interface Event {
	
	public void doNetwork(List<Event> events, ServerThread serverThread);
	public void doMap(List<Event> events, CoreMap map);
	
}
