package game6.client;

import game6.client.effects.EffectContainer;
import game6.client.effects.LightContainer;
import game6.client.gui.Guis;
import game6.client.sound.SoundManager;
import game6.client.world.World;
import game6.core.networking.PacketList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

import de.nerogar.engine.BaseGame;
import de.nerogar.render.*;
import de.nerogar.render.Texture2D.DataType;
import de.nerogar.render.Texture2D.InterpolationType;

public class Game extends BaseGame {

	private Controller controller;
	private World world;
	private ScreenProperties worldProperties;
	private ScreenProperties effectProperties;
	private ScreenProperties lightProperties;
	private ScreenProperties guiProperties;
	private ScreenProperties compositionProperties;

	private EffectContainer effectContainer;
	private LightContainer lightContainer;
	private Compositer compositer;

	@Override
	public void startup() {
		PacketList.init();
		Keyboard.enableRepeatEvents(true);

		compositer = new Compositer();

		Camera camera = new Camera();
		initProperties(camera);
		world = new World(effectContainer);
		controller = new Controller(world, camera, effectContainer);

		Guis.init(display, controller);
		// Trigger gui resize event once at startup
		Guis.resize(Display.getWidth(), Display.getHeight());
		// Trigger gui resize on display resize
		display.addDisplayResizeListener((width, height) -> {
			Guis.resize(width, height);
		});

		setTargetFPS(60);
	}

	private void initProperties(Camera camera) {
		worldProperties = new ScreenProperties(90, false);
		worldProperties.setScreenDimension(1280, 720);
		worldProperties.setCamera(camera);
		compositer.renderTargetWorld = new RenderTarget(true,
				new Texture2D("color", 0, 0, null, InterpolationType.NEAREST, DataType.BGRA_8_8_8_8I),
				new Texture2D("normal", 0, 0, null, InterpolationType.NEAREST, DataType.BGRA_32_32_32_32F),
				new Texture2D("position", 0, 0, null, InterpolationType.NEAREST, DataType.BGRA_32_32_32_32F),
				new Texture2D("ambient", 0, 0, null, InterpolationType.NEAREST, DataType.BGRA_8_8_8_8I));
		worldProperties.setRenderTarget(compositer.renderTargetWorld);

		lightContainer = new LightContainer();
		lightProperties = new ScreenProperties(90, false);
		lightProperties.setScreenDimension(1280, 720);
		lightProperties.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		lightProperties.setCamera(camera);
		lightProperties.setDepthTest(false);
		compositer.renderTargetLights = new RenderTarget(false, new Texture2D("light", 0, 0, null, InterpolationType.NEAREST, DataType.BGRA_32_32_32_32F));
		lightContainer.setWorldRenderTarget(compositer.renderTargetWorld);
		lightProperties.setRenderTarget(compositer.renderTargetLights);

		effectContainer = new EffectContainer();
		effectContainer.setLightContainer(lightContainer);
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
	}

	@Override
	protected void update(float timeDelta) {
		controller.update(timeDelta);
		world.update(timeDelta);
		effectContainer.update(timeDelta);
		lightContainer.update(timeDelta);
		Guis.update();
		SoundManager.update();
	}

	@Override
	protected void render() {
		display.setScreenProperties(worldProperties, true);
		world.render(null);

		display.setScreenProperties(lightProperties, true);
		lightContainer.render(lightProperties);

		display.setScreenProperties(effectProperties, true);
		effectContainer.render();

		display.setScreenProperties(guiProperties, true);
		GL11.glEnable(GL11.GL_BLEND);
		GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE);
		Guis.render();
		GL11.glDisable(GL11.GL_BLEND);

		compositer.render(display, compositionProperties);
	}

	@Override
	protected void cleanup() {
		controller.cleanup();
		SoundManager.shutdown();
	}

}
