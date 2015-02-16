package game6.client.effects;

import static org.lwjgl.opengl.GL11.*;
import de.nerogar.render.Shader;
import de.nerogar.util.Color;
import de.nerogar.util.Vector3f;

public class LaserBullet extends Effect {
	protected float lifeTime;
	protected final float MAX_LIFETIME;

	private float speed;

	public Vector3f start;
	public Vector3f end;
	public Vector3f position;
	public Vector3f direction;
	public Vector3f moveStep;
	public Color color;

	public LaserBullet(Vector3f start, Vector3f end) {
		this.start = start;
		this.end = end;
		position = start.clone();
		direction = end.subtracted(start);
		speed = 150f;

		MAX_LIFETIME = direction.getValue() / speed;
		lifeTime = MAX_LIFETIME;

		direction.setValue(5.0f);
		moveStep = direction.clone().setValue(speed);

		color = new Color(0.1f, 0.1f, 1.0f, 1.0f);
	}

	@Override
	public void render(Shader shader) {
		glLineWidth(1f);
		setColor(shader, color);

		glBegin(GL_LINES);

		glVertex3f(position.getX(), position.getY(), position.getZ());
		glVertex3f(position.getX() + direction.getX(), position.getY() + direction.getY(), position.getZ() + direction.getZ());

		glEnd();
		glLineWidth(1f);
	}

	@Override
	public void update(float timeDelta) {
		lifeTime -= timeDelta;
		position.add(moveStep.multiplied(timeDelta));
	}

	@Override
	public boolean dead() {
		return lifeTime < 0;
	}

}
