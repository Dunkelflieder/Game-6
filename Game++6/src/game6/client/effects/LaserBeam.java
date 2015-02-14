package game6.client.effects;

import static org.lwjgl.opengl.GL11.*;
import de.nerogar.util.Color;
import de.nerogar.util.Vector3f;

public class LaserBeam extends Effect {
	protected float lifeTime;
	protected final float MAX_LIFETIME;

	public Vector3f start;
	public Vector3f end;
	public Color color;

	public LaserBeam(Vector3f start, Vector3f end) {
		MAX_LIFETIME = Float.MAX_VALUE;
		lifeTime = MAX_LIFETIME;

		this.start = start;
		this.end = end;
		color = new Color(0.3f, 0.1f, 0.8f, 1.0f);
	}

	@Override
	public void render() {
		glDisable(GL_TEXTURE_2D);
		glLineWidth(4f);
		glBegin(GL_LINES);

		glColor4f(color.getR(), color.getG(), color.getB(), 1);

		glVertex3f(start.getX(), start.getY(), start.getZ());
		glVertex3f(end.getX(), end.getY(), end.getZ());

		glEnd();
		glLineWidth(1f);
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
