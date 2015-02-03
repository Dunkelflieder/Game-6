package game6.client.world;

import java.util.ArrayList;
import java.util.List;

import game6.client.entities.EntityFighting;
import game6.client.world.buildings.BaseBuilding;
import game6.client.world.buildings.BuildingReactor;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import de.nerogar.engine.BaseController;
import de.nerogar.engine.BaseWorld;
import de.nerogar.render.Camera;
import de.nerogar.util.*;

public class Controller extends BaseController {

	// 0 = not grabbed, 1 = grabbed for movement, 2 = grabbed for rotation
	private byte grabbed = 0;

	private InputHandler inputHandler;
	private Terrain terrain;

	private List<EntityFighting> ownEntities;

	public Controller(BaseWorld world, Camera camera) {
		super(world, camera);
		inputHandler = new InputHandler();

		ownEntities = new ArrayList<EntityFighting>();
	}

	@Override
	protected void init(BaseWorld world) {
		camera.y = 15;
		camera.x = 15;
		camera.z = 15;
		camera.pitch = 60;
		camera.yaw = 0;

		terrain = TerrainGenerator.getTerrain(50, 50);
		world.spawnEntity(terrain, new Vector3f());
	}

	@Override
	public void update(float timeDelta) {
		//TODO don't hardcode fov
		
		inputHandler.updateMousePositions(camera, 90);
		RayIntersection intersection = world.getPhysicsSpace().getIntersecting(inputHandler.getMouseRay());

		if (intersection != null && intersection.intersectionPoint.getX() < terrain.getSizeX() && intersection.intersectionPoint.getZ() < terrain.getSizeY()) {
			int clickX = (int) intersection.intersectionPoint.getX();
			int clickY = (int) intersection.intersectionPoint.getZ();

			//Tile tile = terrain.getTile((int) intersection.intersectionPoint.getX(), (int) intersection.intersectionPoint.getZ());

			BaseBuilding reactor = new BuildingReactor();
			if (Mouse.isButtonDown(0) && terrain.canAddBuilding(clickX, clickY, reactor)) terrain.addBuilding(clickX, clickY, reactor);
		}

		if (Mouse.isButtonDown(2)) {

			if ((Mouse.getY() < Display.getHeight() * 0.3 && grabbed != 1) || grabbed == 2) {

				grabbed = 2;

				// Grabbed lower 30% of screen
				camera.yaw += Mouse.getDX() * 0.5f;

			} else if (grabbed != 2) {

				// Grabbed higher 70% of screen

				grabbed = 1;

				float slow = 0.01f * camera.y;
				float angle = (float) (camera.yaw / (180f / Math.PI));

				float dx = Mouse.getDX();
				float dy = Mouse.getDY();

				camera.x -= slow * dx * Math.cos(angle) + slow * dy * Math.sin(angle);
				camera.z -= slow * dx * Math.sin(angle) - slow * dy * Math.cos(angle);
			}

		} else {

			grabbed = 0;

		}

		camera.y = MathHelper.clamp(camera.y - 0.01f * (Mouse.getDWheel()), 2f, 10f);
	}
}
