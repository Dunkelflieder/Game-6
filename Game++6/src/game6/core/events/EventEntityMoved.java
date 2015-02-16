package game6.core.events;

import game6.core.entities.CoreEntity;
import game6.core.networking.packets.PacketEntityMoved;

import java.util.List;

import de.nerogar.engine.UpdateEventInterface;

public class EventEntityMoved extends Event {

	private CoreEntity entity;

	public EventEntityMoved(CoreEntity entity) {
		super(entity.getFaction());
		this.entity = entity;
	}

	public CoreEntity getEntity() {
		return entity;
	}

	@Override
	public void process(List<UpdateEventInterface> players) {
		for (UpdateEventInterface i : players) {
			i.getConnection().send(new PacketEntityMoved(entity));
		}
	}

}
