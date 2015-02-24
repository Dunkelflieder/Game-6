package game6.core.ai.goalfindingNPcomplete;

public class Link {

	public Node node;
	public double distance;

	public Link(Node node, double distance) {
		this.node = node;
		this.distance = distance;
	}

}
