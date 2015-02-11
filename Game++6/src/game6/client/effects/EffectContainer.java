package game6.client.effects;

import java.util.ArrayList;
import java.util.List;

public class EffectContainer {

	private List<Effect> effects;

	public EffectContainer() {
		effects = new ArrayList<Effect>();
	}

	public void addEffect(Effect effect) {
		effects.add(effect);
	}

	public void update(float timeDelta) {
		for (Effect effect : effects) {
			effect.lifeTime -= timeDelta;
		}

		for (int i = effects.size() - 1; i >= 0; i--) {
			if (effects.get(i).lifeTime < 0) effects.remove(i);
		}
	}

	public void render() {
		for (Effect effect : effects) {
			effect.render();
		}
	}
}
