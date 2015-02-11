package game6.client.effects;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.util.Vector3f;

public class EffectContainer {

	private List<Effect> effects;

	public EffectContainer() {
		effects = new ArrayList<Effect>();
		addEffect(new Lightning(new Vector3f(0, 0, 0), new Vector3f(5, 2, 5)));
	}

	public void addEffect(Effect effect) {
		effects.add(effect);
	}

	public void render() {
		for (Effect effect : effects) {
			effect.render();
		}
	}
}
