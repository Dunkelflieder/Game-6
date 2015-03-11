package game6.client.effects;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

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
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);

		shader.activate();
		for (Effect effect : effects) {
			effect.render(shader);
		}
		shader.deactivate();

		glDisable(GL_BLEND);
	}

	public void setLightContainer(LightContainer lightContainer) {
		this.lightContainer = lightContainer;
	}
}
