package game6.client.effects;

import static org.lwjgl.opengl.GL11.*;
import de.nerogar.util.Vector3f;

public class Lightning extends Effect {
	protected float lifeTime;
	protected final float MAX_LIFETIME;

	//private Color color;

	private Vector3f[] positions;
	private Vector3f[] smallExtensions;

	public Lightning(Vector3f start, Vector3f end) {
		MAX_LIFETIME = 0.15f;
		lifeTime = MAX_LIFETIME;

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
	}

	@Override
	public void render() {
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_LINES);

		glColor4f(0.1f, 0.2f, 1.0f, lifeTime / MAX_LIFETIME);

		for (int i = 0; i < positions.length - 1; i++) {
			glVertex3f(positions[i].getX(), positions[i].getY(), positions[i].getZ());
			glVertex3f(positions[i + 1].getX(), positions[i + 1].getY(), positions[i + 1].getZ());
		}

		for (int i = 0; i < positions.length; i++) {
			glVertex3f(positions[i].getX(), positions[i].getY(), positions[i].getZ());
			glVertex3f(smallExtensions[i].getX(), smallExtensions[i].getY(), smallExtensions[i].getZ());
		}

		glEnd();
		glEnable(GL_TEXTURE_2D);
	}

	@Override
	public void update(float timeDelta) {
		lifeTime -= timeDelta;
	}

	@Override
	public boolean dead() {
		return lifeTime < 0;
	}

}
