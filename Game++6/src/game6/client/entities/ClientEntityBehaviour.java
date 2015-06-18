package game6.client.entities;

import game6.client.entities.guis.EntityGui;
import game6.client.world.ClientWorld;
import game6.core.entities.CoreEntity;
import de.nerogar.render.Shader;

public interface ClientEntityBehaviour {

	public void render(Shader shader);

	public EntityGui<? extends CoreEntity> getGui();

	public ClientWorld getWorld();

	public void setWorld(ClientWorld world);

	public float getVisibleRotation();

	public void setVisibleRotation(float rotation);

}
