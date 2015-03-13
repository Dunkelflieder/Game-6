package game6.client.buildings;

import game6.client.buildings.guis.BuildingGui;
import game6.client.world.World;
import de.nerogar.render.Shader;

public interface ClientBehaviour {

	/**
	 * @param shader the currently active shader
	 */
	public void render(Shader shader);

	/**
	 * @return A BuildingGui, derived from GPanel, which represents the Building's unique GUI
	 */
	public BuildingGui<? extends ClientBuilding> getGui();

	public World getWorld();
	
	public void setWorld(World world);
	
}
