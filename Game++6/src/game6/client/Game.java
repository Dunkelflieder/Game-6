package game6.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import game6.client.effects.EffectContainer;
import game6.client.gui.Guis;
import game6.client.world.World;
import de.nerogar.engine.BaseGame;
import de.nerogar.render.*;

public class Game extends BaseGame {

	private Controller controller;
	private World world = new World();
	private ScreenProperties worldProperties;
	private ScreenProperties effectProperties;
	private ScreenProperties guiProperties;
	private ScreenProperties compositionProperties;

	private EffectContainer effectContainer;
	private Compositer compositer;

	@Override
	public void startup() {
		Camera camera = new Camera();
		compositer = new Compositer();

		worldProperties = new ScreenProperties(90, false);
		worldProperties.setScreenDimension(1280, 720);
		worldProperties.setCamera(camera);
		compositer.renderTargetWorld = new RenderTarget(true, new Texture2D("color", 0, 0));
		worldProperties.setRenderTarget(compositer.renderTargetWorld);

		effectContainer = new EffectContainer();
		effectProperties = new ScreenProperties(90, false);
		effectProperties.setScreenDimension(1280, 720);
		effectProperties.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		effectProperties.setCamera(camera);
		compositer.renderTargetEffects = new RenderTarget(true, new Texture2D("color", 0, 0));
		effectProperties.setRenderTarget(compositer.renderTargetEffects);

		guiProperties = new ScreenProperties(0, true);
		guiProperties.setDepthTest(false);
		guiProperties.setScreenDimension(1280, 720);
		guiProperties.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		compositer.renderTargetGui = new RenderTarget(false, new Texture2D("color", 0, 0));
		guiProperties.setRenderTarget(compositer.renderTargetGui);

		compositionProperties = new ScreenProperties(0, true);
		compositionProperties.setDepthTest(false);
		compositionProperties.setScreenDimension(1280, 720);

		Keyboard.enableRepeatEvents(true);

		world = new World();

		controller = new Controller(world, camera, effectContainer);

		Guis.init(display, controller);
		Guis.resize(Display.getWidth(), Display.getHeight());
		display.addDisplayResizeListener((width, height) -> {
			Guis.resize(width, height);
		});

		display.setScreenProperties(worldProperties, true);
		setTargetFPS(60);

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

		display.setScreenProperties(effectProperties, true);
		effectContainer.render();

		display.setScreenProperties(guiProperties, true);
		Guis.render();

		compositer.render(display, compositionProperties);
	}

	@Override
	protected void cleanup() {
		controller.cleanup();
	}

}
