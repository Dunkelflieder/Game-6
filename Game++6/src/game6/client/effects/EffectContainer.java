package game6.client.effects;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.util.Vector3f;

public class EffectContainer {

	private List<Effect> effects;

	public EffectContainer() {
		effects = new ArrayList<Effect>();
		effects.add(new LaserBeam(new Vector3f(0.0f, 2.0f, 0.0f), new Vector3f(20.0f, 0.0f, 20.0f)));
	}

	public void addEffect(Effect effect) {
		effects.add(effect);
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
		for (Effect effect : effects) {
			effect.render();
		}
	}
}
