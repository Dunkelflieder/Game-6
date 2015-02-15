package game6.client.effects;

import de.nerogar.render.Shader;
import de.nerogar.util.Color;

public abstract class Effect {
	public abstract void update(float timeDelta);

	public abstract void render(Shader shader);

	public abstract boolean dead();

	protected void setColor(Shader shader, Color color) {
		shader.setUniform4f("effectsColor", color.getR(), color.getG(), color.getB(), color.getA());
	}
}