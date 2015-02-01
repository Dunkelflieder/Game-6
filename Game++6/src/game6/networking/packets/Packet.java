package game6.networking.packets;

import game6.networking.Loadable;
import de.felk.NodeFile.NodeFile;

public abstract class Packet implements Loadable {

	public abstract NodeFile toNode();

	public abstract void loadNode(NodeFile node);

}
