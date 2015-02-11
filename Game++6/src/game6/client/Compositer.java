package game6.client;

import org.lwjgl.input.Keyboard;

import de.nerogar.render.*;

public class Compositer {

	public RenderTarget renderTargetWorld;
	public RenderTarget renderTargetEffects;
	public RenderTarget renderTargetGui;
	private Shader shader;

	public Compositer() {
		shader = new Shader("shaders/composition.vert", "shaders/composition.frag");
	}

	public void render(GameDisplay display, ScreenProperties screenProperties) {
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			shader.reloadFiles();
			shader.reCompile();
		}

		display.setScreenProperties(screenProperties, true);
		shader.activate();

		renderTargetWorld.getTexture("color").bind(0);
		renderTargetWorld.getTexture("depth").bind(1);
		renderTargetEffects.getTexture("color").bind(2);
		renderTargetEffects.getTexture("depth").bind(3);
		renderTargetGui.getTexture("color").bind(4);

		shader.setUniform1i("worldTexture", 0);
		shader.setUniform1i("worldDepth", 1);
		shader.setUniform1i("effectsTexture", 2);
		shader.setUniform1i("effectsDepth", 3);
		shader.setUniform1i("guiTexture", 4);

		RenderHelper.renderFullscreenQuad(screenProperties);

		shader.deactivate();

	}
}
