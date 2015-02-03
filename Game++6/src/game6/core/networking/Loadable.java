package game6.core.networking;

import de.felk.NodeFile.NodeFile;

public interface Loadable {

	public void loadNode(NodeFile node);

	public NodeFile toNode();

}
