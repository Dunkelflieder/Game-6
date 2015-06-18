package game6.client.buildings;

import game6.client.buildings.guis.BuildingGui;
import game6.client.world.ClientWorld;
import de.nerogar.render.Shader;

public interface ClientBuildingBehaviour {

	/**
	 * @param shader the currently active shader
	 */
	public void render(Shader shader);

	/**
	 * @return A BuildingGui, derived from GPanel, which represents the Building's unique GUI
	 */
	public BuildingGui<? extends ClientBuilding> getGui();

	public ClientWorld getWorld();
	
	public void setWorld(ClientWorld world);
	
}
