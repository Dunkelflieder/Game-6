package game6.client.main;

import org.lwjgl.opengl.Display;

import game6.client.entities.TestEntity;
import game6.client.gui.GuiStart;
import game6.client.gui.Guis;
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

	@Override
	public void startup() {
		worldProperties = new ScreenProperties(90, false);
		worldProperties.setDepthTest(true);
		worldProperties.setScreenDimension(1280, 720);
		guiProperties = new ScreenProperties(90, true);
		guiProperties.setDepthTest(false);
		guiProperties.setScreenDimension(1280, 720);
		

		world = new World();
		Camera camera = new Camera();
		controller = new Controller(world, camera);
		worldProperties.setCamera(camera);
		
		GuiStart.instance.useThisDisplay(display);
		Guis.resize(Display.getWidth(), Display.getHeight());
		display.addDisplayResizeListener((width, height) -> {
			Guis.resize(width, height);
		});
		
		display.setScreenProperties(worldProperties, true);
		setTargetFPS(60);

		world.spawnEntity(new TestEntity(), new Vector3f(0));
		
	}

	@Override
	protected void update(float timeDelta) {
		controller.update(timeDelta);
		world.update(timeDelta);
		Guis.update();
	}

	@Override
	protected void render() {
		display.setScreenProperties(worldProperties, true);
		world.render();
		display.setScreenProperties(guiProperties, false);
		Guis.render();
	}

	@Override
	protected void cleanup() {
		controller.cleanup();
	}

}
