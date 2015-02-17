package game6.client.effects;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.render.Shader;
import de.nerogar.util.Vector3f;

public class EffectContainer {

	private Shader shader;
	private List<Effect> effects;

	public EffectContainer() {
		effects = new ArrayList<Effect>();
		shader = new Shader("shaders/effects.vert", "shaders/effects.frag");
	}

	public void addEffect(Effect effect) {
		effects.add(effect);
	}

	private float runtime = 0;
	public void update(float timeDelta) {
		runtime += timeDelta;
		if (runtime > 0.3) {
			runtime = 0;
			addEffect(new LaserBullet(new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f((float)Math.random()*500f, 1.0f, (float)Math.random()*20f)));
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

		for (Effect effect : effects) {
			effect.render(shader);
		}

		shader.deactivate();
	}
}
