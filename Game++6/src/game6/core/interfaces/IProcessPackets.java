package game6.core.interfaces;

import game6.core.networking.packets.PacketUniqueID;

public interface IProcessPackets {

	public void process(PacketUniqueID packet);

}
