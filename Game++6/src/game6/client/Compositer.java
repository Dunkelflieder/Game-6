package game6.client;

import org.lwjgl.input.Keyboard;

import de.nerogar.render.*;

public class Compositer {

	public RenderTarget renderTargetWorld;
	public RenderTarget renderTargetEffects;
	public RenderTarget renderTargetEffectsMask;
	public RenderTarget renderTargetGui;

	private Shader compositionShader;
	private Shader effectMaskShader;

	private ScreenProperties effectMaskProperties;

	public Compositer() {
		compositionShader = new Shader("shaders/composition.vert", "shaders/composition.frag");
		effectMaskShader = new Shader("shaders/effectsMask.vert", "shaders/effectsMask.frag");

		effectMaskProperties = new ScreenProperties(0, true);
		effectMaskProperties.setScreenDimension(1280, 720);
		renderTargetEffectsMask = new RenderTarget(false, new Texture2D("color", 0, 0));
		effectMaskProperties.setRenderTarget(renderTargetEffectsMask);
	}

	public void render(GameDisplay display, ScreenProperties screenProperties) {
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			compositionShader.reloadFiles();
			compositionShader.reCompile();

			effectMaskShader.reloadFiles();
			effectMaskShader.reCompile();
		}

		//mask
		display.setScreenProperties(effectMaskProperties, true);
		effectMaskShader.activate();

		renderTargetWorld.getTexture("depth").bind(0);
		renderTargetEffects.getTexture("depth").bind(1);
		renderTargetEffects.getTexture("color").bind(2);

		effectMaskShader.setUniform1i("worldDepth", 0);
		effectMaskShader.setUniform1i("effectsDepth", 1);
		effectMaskShader.setUniform1i("effectsTexture", 2);

		RenderHelper.renderFullscreenQuad(screenProperties);
		effectMaskShader.deactivate();

		//composition
		display.setScreenProperties(screenProperties, true);
		compositionShader.activate();

		renderTargetWorld.getTexture("color").bind(0);
		renderTargetEffectsMask.getTexture("color").bind(1);
		renderTargetGui.getTexture("color").bind(2);

		compositionShader.setUniform1i("worldTexture", 0);
		compositionShader.setUniform1i("effectsTexture", 1);
		compositionShader.setUniform1i("guiTexture", 2);
		compositionShader.setUniform2f("resolution", screenProperties.getRenderWidth(), screenProperties.getRenderHeight());

		RenderHelper.renderFullscreenQuad(screenProperties);
		compositionShader.deactivate();
	}
}
