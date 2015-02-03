package game6.client.main;

import java.awt.Color;

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

	private GButton button = new GButton("Hallo Welt TT ASDWREZfshg");

	@Override
	public void startup() {
		worldProperties = new ScreenProperties(90, false);
		worldProperties.setDepthTest(true);
		guiProperties = new ScreenProperties(90, true);
		guiProperties.setDepthTest(false);

		button.setSize(550, 100);
		button.addButtonClickedListener(b -> System.out.println("Button clicked. Mouse button: " + b));
		button.text.setColor(Color.RED);

		world = new World();
		Camera camera = new Camera();
		controller = new Controller(world, camera);
		worldProperties.setCamera(camera);

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
		button.render(0, 0);
	}

	@Override
	protected void cleanup() {

	}

}
