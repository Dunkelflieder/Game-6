package game6.server.entities;

import game6.core.ai.pathfinding.Pathfinder;
import game6.core.combat.ICombat;
import game6.core.entities.CoreEntity;
import game6.core.entities.MoveTargetPosition;
import game6.core.faction.Faction;
import game6.core.networking.packets.PacketUniqueID;
import game6.core.networking.packets.entities.*;
import game6.server.world.World;

import java.util.List;

import de.nerogar.util.Vector3f;

public interface ServerEntity extends CoreEntity, ServerEntityBehaviour {

	@Override
	default public void setMovementPath(List<Vector3f> newPath) {
		getMovementPath().clear();
		getMovementPath().addAll(newPath);
		broadcastPath();
		broadcastPosition();
	}

	public World getWorld();

	public void setWorld(World world);

	@Override
	default public void process(PacketUniqueID packet) {
		if (packet instanceof PacketEntityMove) {
			if (this instanceof ICombat) {
				((ICombat) this).setCombatTarget(null);
			}
			move(new MoveTargetPosition(this, ((PacketEntityMove) packet).position));
		} else if (packet instanceof PacketEntityRemove) {
			kill();
		}
	}

	@Override
	default public Pathfinder getPathfinder() {
		return getWorld().getMap().getPathfinder();
	}

	@Override
	default public void kill() {
		Faction.broadcastAll(new PacketEntityRemove(getID(), true));
		remove();
	}

	default public void broadcastPosition() {
		Faction.broadcastAll(new PacketEntityUpdatePosition(this));
	}

	default public void broadcastPath() {
		Faction.broadcastAll(new PacketEntityUpdatePath(this));
	}

	default public void broadcastAll() {
		broadcastPath();
		broadcastPosition();
	}

	@Override
	default public void stopMovement() {
		CoreEntity.super.stopMovement();
		broadcastAll();
	}

	@Override
	default void rotationChanged() {
		Faction.broadcastAll(new PacketEntityUpdateRotation(this));
	}

}
