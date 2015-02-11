package game6.client.effects;

import static org.lwjgl.opengl.GL11.*;
import de.nerogar.util.Color;
import de.nerogar.util.Vector3f;

public class Lightning extends Effect {
	public Vector3f start;
	public Vector3f end;
	public Color color;

	public Lightning(Vector3f start, Vector3f end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public void render() {
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_LINES);

		glVertex3f(start.getX(), start.getY(), start.getZ());
		glVertex3f(end.getX(), end.getY(), end.getZ());

		glEnd();
		glEnable(GL_TEXTURE_2D);
	}
}
