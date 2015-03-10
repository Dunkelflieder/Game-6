package game6.client.effects;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.render.Shader;
import de.nerogar.util.Vector3f;

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

	float runtime;

	public void update(float timeDelta) {
		runtime += timeDelta;

		if (runtime > 2.0f) {
			runtime = 0;
			addEffect(new Explosion(new Vector3f(5.0f, 0.0f, 5.0f)));
			addEffect(new LaserBullet(new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f(10.0f, 1.0f, 5.0f) ));
		}

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
