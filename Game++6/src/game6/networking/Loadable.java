package game6.networking;

import de.felk.NodeFile.NodeFile;

public interface Loadable {

	public void loadNode(NodeFile node);

	public NodeFile toNode();

}
