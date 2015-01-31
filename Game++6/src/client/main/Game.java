package client.main;
import client.entities.TestEntity;
import client.world.Controller;
import client.world.World;
import de.nerogar.engine.BaseGame;
import de.nerogar.render.Camera;
import de.nerogar.render.ScreenProperties;
import de.nerogar.util.Vector3f;

public class Game extends BaseGame {

	private Controller controller;
	private World world = new World();
	private Camera camera;
	private ScreenProperties properties;

	@Override
	public void startup() {

		properties = new ScreenProperties(90, false);

		world = new World();
		camera = new Camera();
		controller = new Controller(world, camera);
		properties.setCamera(camera);

		display.setScreenProperties(properties, true);
		setTargetFPS(60);

		world.spawnEntity(new TestEntity(), new Vector3f(0));
	}

	@Override
	protected void update(float timeDelta) {
		controller.update(timeDelta);
		world.update(timeDelta);
	}

	@Override
	protected void render() {
		display.setScreenProperties(properties, true);
		world.render();
	}

	@Override
	protected void cleanup() {

	}

}
