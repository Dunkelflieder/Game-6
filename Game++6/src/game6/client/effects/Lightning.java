package game6.client.effects;

import static org.lwjgl.opengl.GL11.*;
import de.nerogar.render.Shader;
import de.nerogar.util.Color;
import de.nerogar.util.Vector3f;

public class Lightning extends Effect {
	protected float lifeTime;
	protected final float MAX_LIFETIME;
	protected final float MAX_BRIGHTNES;

	private Vector3f center;
	private Vector3f[] positions;
	private Vector3f[] smallExtensions;

	private Color color;
	private Light light;

	public Lightning(Vector3f start, Vector3f end) {
		MAX_BRIGHTNES = 3.0f;
		MAX_LIFETIME = 0.1f;
		lifeTime = MAX_LIFETIME;

		center = start.added(end).multiply(0.5f);

		Vector3f direction = end.subtracted(start);
		int vertices = (int) direction.getValue();
		if (vertices < 2) vertices = 2;
		direction.multiply(1f / vertices);

		positions = new Vector3f[vertices];
		smallExtensions = new Vector3f[vertices];

		for (int i = 0; i < vertices; i++) {
			positions[i] = direction.multiplied(i).add(start);
			positions[i].addX((float) Math.random() - 0.5f);
			positions[i].addY((float) Math.random() - 0.5f);
			positions[i].addZ((float) Math.random() - 0.5f);
		}

		for (int i = 0; i < vertices; i++) {
			smallExtensions[i] = positions[i].clone();
			smallExtensions[i].addX(((float) Math.random() - 0.5f) * 0.8f);
			smallExtensions[i].addY(((float) Math.random() - 0.5f) * 0.8f);
			smallExtensions[i].addZ(((float) Math.random() - 0.5f) * 0.8f);
		}

		positions[0] = start;
		positions[positions.length - 1] = end;

		color = new Color(0.1f, 0.2f, 1.0f, lifeTime / MAX_LIFETIME);
	}

	@Override
	public void initLights(LightContainer lightContainer) {
		light = new Light(color, center, 10.0f, MAX_BRIGHTNES);
		addLight(light, lightContainer);
	}

	@Override
	public void render(Shader shader) {
		setColor(shader, new Color(color.getR(), color.getG(), color.getB(), lifeTime / MAX_LIFETIME));

		glBegin(GL_LINES);

		for (int i = 0; i < positions.length - 1; i++) {
			glVertex3f(positions[i].getX(), positions[i].getY(), positions[i].getZ());
			glVertex3f(positions[i + 1].getX(), positions[i + 1].getY(), positions[i + 1].getZ());
		}

		for (int i = 0; i < positions.length; i++) {
			glVertex3f(positions[i].getX(), positions[i].getY(), positions[i].getZ());
			glVertex3f(smallExtensions[i].getX(), smallExtensions[i].getY(), smallExtensions[i].getZ());
		}

		glEnd();
	}

	@Override
	public void update(float timeDelta) {
		lifeTime -= timeDelta;

		light.intensity = MAX_BRIGHTNES * (lifeTime / MAX_LIFETIME);

		if (lifeTime < 0) kill();
	}

}
