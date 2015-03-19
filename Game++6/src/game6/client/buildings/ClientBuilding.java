package game6.client.buildings;

import game6.client.effects.Explosion;
import game6.core.buildings.CoreBuilding;
import game6.core.networking.packets.PacketUniqueID;
import game6.core.networking.packets.buildings.PacketBuildingRemove;
import game6.core.networking.packets.buildings.PacketBuildingUpdate;
import game6.core.world.Map;
import de.nerogar.util.Vector3f;

public interface ClientBuilding extends CoreBuilding, ClientBuildingBehaviour {

	default public void process(PacketUniqueID packet) {
		if (packet instanceof PacketBuildingUpdate) {
			setEnergy(((PacketBuildingUpdate) packet).energy);
		} else if (packet instanceof PacketBuildingRemove) {
			PacketBuildingRemove packetRemove = (PacketBuildingRemove) packet;
			if (packetRemove.killed) {
				getWorld().getEffectContainer().addEffect(new Explosion(new Vector3f(getPosX(), 0, getPosY())));
			}
			kill();
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
	default public Map<? extends CoreBuilding> getMap() {
		return getWorld().getMap();
	}
	
}
