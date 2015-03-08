package game6.client.effects;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import de.nerogar.render.Shader;

public class EffectContainer {

	private LightContainer lightContainer;

	private Shader shader;
	private List<Effect> effects;

	public EffectContainer() {
		effects = new ArrayList<Effect>();
		shader = new Shader("shaders/effects.vert", "shaders/effects.frag");
	}

	public void addEffect(Effect effect) {
		effects.add(effect);
		effect.initLights(lightContainer);
	}

	public void update(float timeDelta) {
		for (Effect effect : effects) {
			effect.update(timeDelta);
		}

		for (int i = effects.size() - 1; i >= 0; i--) {
			if (effects.get(i).dead()) effects.remove(i);
		}
	}

	public void render() {
		GL11.glEnable(GL11.GL_BLEND);
		GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE);
		
		shader.activate();
		for (Effect effect : effects) {
			effect.render(shader);
		}
		shader.deactivate();

		GL11.glDisable(GL11.GL_BLEND);
	}

	public void setLightContainer(LightContainer lightContainer) {
		this.lightContainer = lightContainer;
	}
}
