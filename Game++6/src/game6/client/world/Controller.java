package game6.client.world;

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

	public Controller(BaseWorld world, Camera camera) {
		super(world, camera);
		inputHandler = new InputHandler();
	}

	@Override
	protected void init(BaseWorld world) {
		camera.y = 15;
		camera.x = 15;
		camera.z = 15;
		camera.pitch = 60;
		camera.yaw = 0;
	}

	@Override
	public void update(float timeDelta) {
		//TODO don't hardcode fov
		inputHandler.updateMousePositions(camera, 90);
//		RayIntersection intersection = world.getPhysicsSpace().getIntersecting(inputHandler.getMouseRay());
//		
//		if (intersection != null && intersection.intersectionPoint.getX() < terrain.getSizeX() && intersection.intersectionPoint.getZ() < terrain.getSizeY()) {
//			System.out.print((int) intersection.intersectionPoint.getX() + " : " + (int) intersection.intersectionPoint.getZ());
//
//			Tile tile = terrain.getTile((int) intersection.intersectionPoint.getX(), (int) intersection.intersectionPoint.getZ());
//
//			System.out.println(tile);
//		}

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
