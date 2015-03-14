package game6.client.entities;

import game6.client.entities.guis.EntityGuiDefault;
import game6.client.world.World;
import game6.core.ai.pathfinding.Pathfinder;
import de.nerogar.render.Shader;
import de.nerogar.util.MathHelper;

public class DefaultClientEntityBehaviour implements ClientEntityBehaviour {

	private static final float rotationSpeed = 6;
	
	private ClientEntity entity;
	
	private World world;
	private float visibleRotation;

	public DefaultClientEntityBehaviour(ClientEntity entity) {
		this.entity = entity;
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

	public void updateVisibleRotation(float timeDelta) {
		// determine the delta this entity needs to turn.
		float turngoal = entity.getRotation() - visibleRotation;
		// fix, if turning > 180°. Reverse the direction
		if (turngoal > Math.PI) {
			turngoal -= 2 * Math.PI;
		}
		// fix, if turning < -180°. Reverse the direction
		if (turngoal < -Math.PI) {
			turngoal += 2 * Math.PI;
		}
		float delta = MathHelper.clamp(turngoal, -rotationSpeed * timeDelta, rotationSpeed * timeDelta);
		visibleRotation = (float) ((visibleRotation + delta) % (2 * Math.PI));
	}

	public float getVisibleRotation() {
		return visibleRotation;
	}

}
