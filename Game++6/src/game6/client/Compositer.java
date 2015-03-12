package game6.client;

import org.lwjgl.input.Keyboard;

import de.nerogar.render.*;

public class Compositer {

	public RenderTarget renderTargetWorld;
	public RenderTarget renderTargetEffects;
	public RenderTarget renderTargetEffectsMask;
	public RenderTarget renderTargetGui;
	public RenderTarget renderTargetLights;

	private Shader compositionShader;
	private Shader effectMaskShader;

	private ScreenProperties effectMaskProperties;

	private TextureCubeMap cubeMap;

	public Camera camera;

	public Compositer() {
		compositionShader = new Shader("shaders/composition.vert", "shaders/composition.frag");
		effectMaskShader = new Shader("shaders/effectsMask.vert", "shaders/effectsMask.frag");

		effectMaskProperties = new ScreenProperties(0, true);
		effectMaskProperties.setScreenDimension(1280, 720);
		renderTargetEffectsMask = new RenderTarget(false, new Texture2D("color", 0, 0));
		effectMaskProperties.setRenderTarget(renderTargetEffectsMask);

		cubeMap = TextureCubeMapLoader.loadTexture(
				"res/colors/skybox/right.png",
				"res/colors/skybox/left.png",
				"res/colors/skybox/bottom.png",
				"res/colors/skybox/top.png",
				"res/colors/skybox/back.png",
				"res/colors/skybox/front.png");
	}

	public void render(GameDisplay display, ScreenProperties screenProperties) {
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			compositionShader.reloadFiles();
			compositionShader.reCompile();

			effectMaskShader.reloadFiles();
			effectMaskShader.reCompile();
		}

		//effects mask
		display.setScreenProperties(effectMaskProperties, true);
		effectMaskShader.activate();

		renderTargetWorld.getTexture("depth").bind(0);
		renderTargetEffects.getTexture("depth").bind(1);
		renderTargetEffects.getTexture("color").bind(2);

		effectMaskShader.setUniform1i("worldDepth", 0);
		effectMaskShader.setUniform1i("effectsDepth", 1);
		effectMaskShader.setUniform1i("effectsTexture", 2);

		RenderHelper.renderFBOFullscreenQuad(screenProperties);
		effectMaskShader.deactivate();

		//composition
		display.setScreenProperties(screenProperties, true);

		if (!Keyboard.isKeyDown(Keyboard.KEY_T)) {

			compositionShader.activate();

			renderTargetWorld.getTexture("color").bind(0);
			renderTargetWorld.getTexture("normal").bind(1);
			renderTargetWorld.getTexture("position").bind(2);
			renderTargetWorld.getTexture("ambient").bind(3);
			renderTargetLights.getTexture("light").bind(4);
			renderTargetEffectsMask.getTexture("color").bind(5);
			renderTargetGui.getTexture("color").bind(6);

			compositionShader.setUniform1i("tex_worldColor", 0);
			compositionShader.setUniform1i("tex_worldNormal", 1);
			compositionShader.setUniform1i("tex_worldPosition", 2);
			compositionShader.setUniform1i("tex_worldAmbient", 3);
			compositionShader.setUniform1i("tex_worldLight", 4);
			compositionShader.setUniform1i("tex_effectsColor", 5);
			compositionShader.setUniform1i("tex_guiColor", 6);

			cubeMap.bind(7);
			compositionShader.setUniform1i("tex_cube_sky", 7);
			
			compositionShader.setUniform2f("resolution", screenProperties.getRenderWidth(), screenProperties.getRenderHeight());
			compositionShader.setUniform3f("cameraPosition", camera.getX(), camera.getY(), camera.getZ());

			RenderHelper.renderFBOFullscreenQuad(screenProperties);

			compositionShader.deactivate();
		} else {
			renderTargetWorld.getTexture("color").bind();
			RenderHelper.renderFBOQuad(screenProperties, 0.333f, 0.333f, 0.0f, 0.0f);

			renderTargetWorld.getTexture("normal").bind();
			RenderHelper.renderFBOQuad(screenProperties, 0.333f, 0.333f, 0.333f, 0.0f);

			renderTargetWorld.getTexture("position").bind();
			RenderHelper.renderFBOQuad(screenProperties, 0.333f, 0.333f, 0.666f, 0.0f);

			renderTargetWorld.getTexture("ambient").bind();
			RenderHelper.renderFBOQuad(screenProperties, 0.333f, 0.333f, 0.0f, 0.333f);

			renderTargetLights.getTexture("light").bind();
			RenderHelper.renderFBOQuad(screenProperties, 0.333f, 0.333f, 0.333f, 0.333f);

			renderTargetEffectsMask.getTexture("color").bind();
			RenderHelper.renderFBOQuad(screenProperties, 0.333f, 0.333f, 0.666f, 0.333f);

			renderTargetGui.getTexture("color").bind();
			RenderHelper.renderFBOQuad(screenProperties, 0.333f, 0.333f, 0.0f, 0.666f);
		}

	}
}
