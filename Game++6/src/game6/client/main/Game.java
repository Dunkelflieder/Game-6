package game6.client.main;

import game6.client.entities.TestEntity;
import game6.client.gui.components.Button;
import game6.client.world.Controller;
import game6.client.world.World;
import de.nerogar.engine.BaseGame;
import de.nerogar.render.Camera;
import de.nerogar.render.ScreenProperties;
import de.nerogar.util.Vector3f;

public class Game extends BaseGame {

	private Controller controller;
	private World world = new World();
	private Camera camera;
	private ScreenProperties worldProperties;
	private ScreenProperties guiProperties;

	private Button button = new Button("Hallo Welt TT ASDWREZfshg");

	@Override
	public void startup() {
		worldProperties = new ScreenProperties(90, false);
		guiProperties = new ScreenProperties(90, true);

		button.setSize(550, 100);
		button.addButtonClickedListener(b -> System.out.println("Button clicked. Mouse button: " + b));

		world = new World();
		camera = new Camera();
		controller = new Controller(world, camera);
		worldProperties.setCamera(camera);
		guiProperties.setCamera(new Camera());

		display.setScreenProperties(worldProperties, true);
		setTargetFPS(60);

		world.spawnEntity(new TestEntity(), new Vector3f(0));
	}

	@Override
	protected void update(float timeDelta) {
		controller.update(timeDelta);
		world.update(timeDelta);
		button.update();
	}

	@Override
	protected void render() {
		display.setScreenProperties(worldProperties, true);
		world.render();
		display.setScreenProperties(guiProperties, false);
		button.render();
	}

	@Override
	protected void cleanup() {

	}

}
