package game6.client.effects;

import static org.lwjgl.opengl.GL11.*;
import de.nerogar.render.Shader;
import de.nerogar.util.Color;
import de.nerogar.util.Vector3f;

public class LightPillar extends Effect {
	protected float lifeTime;
	protected final float MAX_LIFETIME;

	public Vector3f position;
	public Color color;

	public LightPillar(Vector3f position) {
		MAX_LIFETIME = Float.MAX_VALUE;
		lifeTime = MAX_LIFETIME;

		this.position = position;
		color = new Color(0.1f, 1.0f, 0.1f, 1.0f);
	}

	@Override
	public void initLights(LightContainer lightContainer) {
	}

	@Override
	public void render(Shader shader) {
		setColor(shader, color);

		glBegin(GL_QUADS);

		glVertex3f(position.getX() - 0.1f, 0f, position.getZ());
		glVertex3f(position.getX() + 0.1f, 0f, position.getZ());
		glVertex3f(position.getX() + 0.1f, 100f, position.getZ());
		glVertex3f(position.getX() - 0.1f, 100f, position.getZ());

		glVertex3f(position.getX(), 0f, position.getZ() - 0.1f);
		glVertex3f(position.getX(), 0f, position.getZ() + 0.1f);
		glVertex3f(position.getX(), 100f, position.getZ() + 0.1f);
		glVertex3f(position.getX(), 100f, position.getZ() - 0.1f);

		glEnd();
	}

	@Override
	public void update(float timeDelta) {
		lifeTime -= timeDelta;

		if (lifeTime < 0) kill();
	}

}
