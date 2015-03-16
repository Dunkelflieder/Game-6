package game6.client.buildings;

import game6.client.effects.Explosion;
import game6.core.buildings.CoreBuilding;
import game6.core.networking.packets.buildings.*;
import de.nerogar.util.Vector3f;

public interface ClientBuilding extends CoreBuilding, ClientBuildingBehaviour {

	default public void process(PacketBuilding packet) {
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

}
