package game6.core.ai.goalfindingNPcomplete;

import game6.core.buildings.CoreBuilding;
import game6.core.buildings.CoreBuildingTower;
import game6.core.world.CoreWorld;

import java.util.*;

/**
 * This algorithm finds the fastest paths starting at a given building, over towers in range, to all reachable energy consumers.
 * Because this is a NP-complete problem, it is slow as fuck.
 * I don't know why I did this. Don't use it for the actual goalfinding please.
 * @author Felk
 *
 */
public class GoalfinderNPcomplete {

	private CoreWorld world;
	private HashMap<CoreBuilding, Node> nodes;
	private HashMap<CoreBuilding, Path> paths;

	public GoalfinderNPcomplete(CoreWorld world) {
		this.world = world;
		paths = new HashMap<>();
		init();
	}

	public void init() {
		nodes = new HashMap<>();

		for (CoreBuilding building : world.getBuildings()) {
			nodes.put(building, new Node(building));
		}

		for (Node node : nodes.values()) {
			CoreBuilding building = node.getBuilding();
			List<CoreBuilding> neighbourBuildings = world.getMap().getBuildingsWithin(building.getPosX(), building.getPosY(), building.getRange());
			// remove self
			neighbourBuildings.remove(building);

			Link[] neighbours = new Link[neighbourBuildings.size()];
			for (int i = 0; i < neighbours.length; i++) {
				CoreBuilding neighbourBuilding = neighbourBuildings.get(i);
				double distance = Math.pow(Math.abs(neighbourBuilding.getPosX() - building.getPosX()), 2) + Math.pow(Math.abs(neighbourBuilding.getPosY() - building.getPosY()), 2);
				neighbours[i] = new Link(nodes.get(neighbourBuilding), distance);
			}

			// Sort by inverse distance. Goal-Buildings always first though
			Arrays.sort(neighbours, new Comparator<Link>() {
				@Override
				public int compare(Link a, Link b) {
					if (a.node.getBuilding().canReceiveEnergy()) {
						if (b.node.getBuilding().canReceiveEnergy()) {
							return 0;
						}
						return -1;
					}
					if (b.node.getBuilding().canReceiveEnergy()) {
						return 1;
					}
					return a.distance < b.distance ? -1 : (a.distance > b.distance ? 1 : 0);
				}
			});

			node.setNeighbours(neighbours);
		}

	}

	public Collection<Path> search(CoreBuilding start) {
		paths.clear();
		search(nodes.get(start), new LinkedHashSet<>());
		return paths.values();
	}

	private void search(Node start, LinkedHashSet<Link> originPath) {
		for (Link neighbour : start.getNeighbours()) {
			LinkedHashSet<Link> sub = new LinkedHashSet<>(originPath);
			sub.add(neighbour);
			if (neighbour.node.getBuilding().canReceiveEnergy()) {

				Path newPath = new Path(sub.toArray(new Link[0]));
				Path existingPath = paths.get(neighbour.node.getBuilding());

				if (existingPath != null) {
					if (existingPath.getDistance() < newPath.getDistance()) {
						continue;
					}
				}

				paths.put(neighbour.node.getBuilding(), newPath);
			}
			if (!(neighbour.node.getBuilding() instanceof CoreBuildingTower) || originPath.contains(neighbour)) {
				continue;
			}
			search(neighbour.node, sub);
		}
	}
}
