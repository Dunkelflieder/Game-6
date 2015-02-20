package game6.client.effects;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.render.Shader;
import de.nerogar.util.Color;

public abstract class Effect {

	protected List<Light> lights;

	private boolean dead;

	public Effect() {
		lights = new ArrayList<Light>();
	}

	public abstract void initLights(LightContainer lightContainer);

	public void addLight(Light light, LightContainer lightContainer) {
		lights.add(light);
		lightContainer.addLight(light);
	}

	public abstract void update(float timeDelta);

	public abstract void render(Shader shader);

	public void kill() {
		dead = true;

		for (Light light : lights) {
			light.kill();
		}
	}

	public boolean dead() {
		return dead;
	}

	protected void setColor(Shader shader, Color color) {
		shader.setUniform4f("effectsColor", color.getR(), color.getG(), color.getB(), color.getA());
	}

}