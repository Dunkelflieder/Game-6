package game6.client.entities;

import game6.client.entities.guis.EntityGuiDefault;
import game6.client.world.World;
import game6.core.ai.pathfinding.Pathfinder;
import de.nerogar.render.Shader;

public class DefaultClientEntityBehaviour implements ClientEntityBehaviour {

	private World world;
	private float visibleRotation;

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public void setWorld(World world) {
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
