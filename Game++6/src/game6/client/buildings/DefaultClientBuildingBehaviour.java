package game6.client.buildings;

import game6.client.buildings.guis.BuildingGui;
import game6.client.world.World;
import de.nerogar.render.Shader;

public class DefaultClientBuildingBehaviour implements ClientBuildingBehaviour {

	private World world;
	
	@Override
	public void render(Shader shader) {
		// TODO Auto-generated method stub
	}

	@Override
	public BuildingGui<? extends ClientBuilding> getGui() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public void setWorld(World world) {
		this.world = world;
	}

}
