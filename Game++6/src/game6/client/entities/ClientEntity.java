package game6.client.entities;

import game6.client.effects.Explosion;
import game6.core.entities.CoreEntity;
import game6.core.networking.packets.entities.*;

import java.util.List;

import de.nerogar.util.Vector3f;

public interface ClientEntity extends CoreEntity, ClientEntityBehaviour {

	default public void setPath(List<Vector3f> newPath) {
		getPath().clear();
		getPath().addAll(newPath);
	}

	default public void process(PacketEntity packet) {
		if (packet instanceof PacketEntityUpdatePath) {
			setPath(((PacketEntityUpdatePath) packet).path);
		} else if (packet instanceof PacketEntityUpdatePosition) {
			teleport(((PacketEntityUpdatePosition) packet).position);
			setRotation(((PacketEntityUpdatePosition) packet).rotation);
		} else if (packet instanceof PacketEntityRemove) {
			PacketEntityRemove packetRemove = (PacketEntityRemove) packet;
			if (packetRemove.killed) {
				getWorld().getEffectContainer().addEffect(new Explosion(getPosition().clone()));
			}
			if (getWorld().getSelectedEntity() != null && packetRemove.id == getWorld().getSelectedEntity().getID()) {
				getWorld().selectEntity(null);
			}
			remove();
		}
	}

}
