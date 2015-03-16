package game6.client.entities;

import game6.client.entities.guis.EntityGuiDefault;
import game6.client.world.World;
import de.nerogar.render.Shader;

public interface ClientEntityBehaviour {

	public void render(Shader shader);

	public EntityGuiDefault getGui();

	public World getWorld();

	public void setWorld(World world);

	public float getVisibleRotation();

	public void setVisibleRotation(float rotation);

}
