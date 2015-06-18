package game6.client.entities;

import game6.client.entities.guis.EntityGuiDefault;
import game6.client.world.ClientWorld;
import game6.core.ai.pathfinding.Pathfinder;
import de.nerogar.render.Shader;

public class DefaultClientEntityBehaviour implements ClientEntityBehaviour {

	private ClientWorld world;
	private float visibleRotation;

	@Override
	public ClientWorld getWorld() {
		return world;
	}

	@Override
	public void setWorld(ClientWorld world) {
		this.world = world;
	}

	public Pathfinder getPathfinder() {
		return world.getMap().getPathfinder();
	}

	@Override
	public float getVisibleRotation() {
		return visibleRotation;
	}

	@Override
	public void setVisibleRotation(float rotation) {
		this.visibleRotation = rotation;
	}

	@Override
	public void render(Shader shader) {
		// TODO Auto-generated method stub
	}

	@Override
	public EntityGuiDefault getGui() {
		// TODO Auto-generated method stub
		return null;
	}

}
