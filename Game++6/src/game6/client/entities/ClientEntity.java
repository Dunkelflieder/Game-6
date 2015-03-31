package game6.client.entities;

import game6.client.effects.Explosion;
import game6.core.ai.pathfinding.Pathfinder;
import game6.core.entities.CoreEntity;
import game6.core.interfaces.ResourceContainer;
import game6.core.networking.packets.PacketUniqueID;
import game6.core.networking.packets.entities.*;

import java.util.List;

import de.nerogar.util.MathHelper;
import de.nerogar.util.Vector3f;

public interface ClientEntity extends CoreEntity, ClientEntityBehaviour {

	default public void setMovementPath(List<Vector3f> newPath) {
		getMovementPath().clear();
		getMovementPath().addAll(newPath);
	}

	default public void process(PacketUniqueID packet) {
		if (packet instanceof PacketEntityUpdatePath) {
			setMovementPath(((PacketEntityUpdatePath) packet).path);
		} else if (packet instanceof PacketEntityUpdatePosition) {
			teleport(((PacketEntityUpdatePosition) packet).position);
		} else if (packet instanceof PacketEntityUpdateRotation) {
			setRotation(((PacketEntityUpdateRotation) packet).rotation);
		} else if (packet instanceof PacketEntityRemove) {
			PacketEntityRemove packetRemove = (PacketEntityRemove) packet;
			if (packetRemove.killed) {
				getWorld().getEffectContainer().addEffect(new Explosion(getPosition().clone()));
			}
			kill();
		} else if (packet instanceof PacketEntityUpdateInventory) {
			if (this instanceof ResourceContainer) {
				((ResourceContainer) this).setResources(((PacketEntityUpdateInventory) packet).resources);
				((ResourceContainer) this).setCapacity(((PacketEntityUpdateInventory) packet).resources.getCapacity());
			}
		} else if (packet instanceof PacketEntityUpdateHealth) {
			setHealth(((PacketEntityUpdateHealth) packet).health);
		}
	}

	@Override
	default public void kill() {
		if (getWorld().getSelectedEntity() != null && getID() == getWorld().getSelectedEntity().getID()) {
			getWorld().selectEntity(null);
		}
		remove();
	}

	@Override
	default public Pathfinder getPathfinder() {
		return getWorld().getMap().getPathfinder();
	}

	default void updateVisibleRotation(float timeDelta) {
		int rotationSpeed = 9;

		// determine the delta this entity needs to turn.
		float turngoal = getRotation() - getVisibleRotation();
		// fix, if turning > 180°. Reverse the direction
		if (turngoal > Math.PI) {
			turngoal -= 2 * Math.PI;
		}
		// fix, if turning < -180°. Reverse the direction
		if (turngoal < -Math.PI) {
			turngoal += 2 * Math.PI;
		}
		float delta = MathHelper.clamp(turngoal, -rotationSpeed * timeDelta, rotationSpeed * timeDelta);
		setVisibleRotation((float) ((getVisibleRotation() + delta) % (2 * Math.PI)));
	}
	
	default void updateClient(float timeDelta) {
		updateVisibleRotation(timeDelta);
	}

}
