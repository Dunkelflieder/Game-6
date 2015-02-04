package game6.client.main;

import game6.client.entities.TestEntity;
import game6.client.gui.components.GButton;
import game6.client.world.Controller;
import game6.client.world.World;

import de.nerogar.engine.BaseGame;
import de.nerogar.render.Camera;
import de.nerogar.render.ScreenProperties;
import de.nerogar.util.Vector3f;

public class Game extends BaseGame {

	private Controller controller;
	private World world = new World();
	private ScreenProperties worldProperties;
	private ScreenProperties guiProperties;

	private GButton button = new GButton("Connect to local server.");
	private GButton button2 = new GButton("Place building");
	
	@Override
	public void startup() {
		worldProperties = new ScreenProperties(90, false);
		worldProperties.setDepthTest(true);
		guiProperties = new ScreenProperties(90, true);
		guiProperties.setDepthTest(false);

		world = new World();
		Camera camera = new Camera();
		controller = new Controller(world, camera);
		worldProperties.setCamera(camera);

		display.setScreenProperties(worldProperties, true);
		setTargetFPS(60);

		world.spawnEntity(new TestEntity(), new Vector3f(0));

		button.setPos(50, 50);
		button.setSize(500, 60);
		button.addButtonClickedListener(b -> {
			System.out.println("Button clicked.");
			controller.connect("localhost", 34543);
		});
		
		button2.setPos(50, 250);
		button2.setSize(500, 60);
		button2.addButtonClickedListener(b -> {
			controller.addBuildingDebug();
		});
	}

	@Override
	protected void update(float timeDelta) {
		controller.update(timeDelta);
		world.update(timeDelta);
		button.update();
		button2.update();
	}

	@Override
	protected void render() {
		display.setScreenProperties(worldProperties, true);
		world.render();
		display.setScreenProperties(guiProperties, false);
		button.render(0, 0);
		button2.render(0, 0);
	}

	@Override
	protected void cleanup() {
		controller.cleanup();
	}

}
