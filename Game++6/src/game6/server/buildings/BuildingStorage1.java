package game6.server.buildings;

import game6.client.buildings.guis.BuildingGui;
import game6.core.buildings.CoreBuilding;
import game6.core.buildings.CoreBuildingStorage1;
import game6.core.networking.packets.PacketUpdateStorage;
import game6.core.util.Resource;
import de.nerogar.render.Shader;

public class BuildingStorage1 extends CoreBuildingStorage1 {

	private int tick;

	public BuildingStorage1() {
		super(getNextID());
		getResources().setChangeCallback(this::resourcesChanged);
	}

	private void resourcesChanged() {
		faction.broadcast(new PacketUpdateStorage(getID(), getResources()));
	}

	@Override
	public void init() {
	}

	@Override
	public void render(Shader shader) {
	}

	@Override
	public void update() {
		tick++;

		// TODO testing. remove later.
		if (tick % 10 == 0) {
			getResources().addResource(Resource.STUFF, 5);
		}
	}

	@Override
	public BuildingGui<CoreBuilding> getGui() {
		return null;
	}

}
