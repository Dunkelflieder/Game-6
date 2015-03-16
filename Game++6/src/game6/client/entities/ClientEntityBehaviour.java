package game6.client.entities;

import game6.client.entities.guis.EntityGui;
import game6.client.world.World;
import game6.core.entities.CoreEntity;
import de.nerogar.render.Shader;

public interface ClientEntityBehaviour {

	public void render(Shader shader);

	public EntityGui<? extends CoreEntity> getGui();

	public World getWorld();

	public void setWorld(World world);

	public float getVisibleRotation();

	public void setVisibleRotation(float rotation);

}
