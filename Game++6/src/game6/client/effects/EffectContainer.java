package game6.client.effects;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

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

	private float runtime = 0;

	public void update(float timeDelta) {
		runtime += timeDelta;
		if (runtime > 0.3) {
			runtime = 0;
			addEffect(new LaserBullet(new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f((float) Math.random() * 500f, 1.0f, (float) Math.random() * 20f)));
		}
		for (Effect effect : effects) {
			effect.update(timeDelta);
		}

		for (int i = effects.size() - 1; i >= 0; i--) {
			if (effects.get(i).dead()) effects.remove(i);
		}
	}

	public void render() {
		shader.activate();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		for (Effect effect : effects) {
			effect.render(shader);
		}

		GL11.glDisable(GL11.GL_BLEND);
		shader.deactivate();
	}

	public void setLightContainer(LightContainer lightContainer) {
		this.lightContainer = lightContainer;
	}
}
