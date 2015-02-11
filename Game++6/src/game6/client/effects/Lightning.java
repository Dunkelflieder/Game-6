package game6.client.effects;

import static org.lwjgl.opengl.GL11.*;
import de.nerogar.util.Color;
import de.nerogar.util.Vector3f;

public class Lightning extends Effect {
	public Vector3f start;
	public Vector3f end;
	public Color color;

	private Vector3f[] positions;

	public Lightning(Vector3f start, Vector3f end) {
		super();
		this.start = start;
		this.end = end;

		Vector3f direction = end.subtracted(start);
		int vertices = (int) direction.getValue();
		if (vertices < 2) vertices = 2;
		direction.multiply(1f / vertices);

		positions = new Vector3f[vertices];

		for (int i = 0; i < vertices; i++) {
			positions[i] = direction.multiplied(i).add(start);
			positions[i].addX((float) Math.random() - 0.5f);
			positions[i].addY((float) Math.random() - 0.5f);
			positions[i].addZ((float) Math.random() - 0.5f);
		}

		positions[0] = start;
		positions[positions.length - 1] = end;
	}

	@Override
	public void render() {
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_LINES);

		for (int i = 0; i < positions.length - 1; i++) {
			glVertex3f(positions[i].getX(), positions[i].getY(), positions[i].getZ());
			glVertex3f(positions[i + 1].getX(), positions[i + 1].getY(), positions[i + 1].getZ());
		}

		glEnd();
		glEnable(GL_TEXTURE_2D);
	}
}
