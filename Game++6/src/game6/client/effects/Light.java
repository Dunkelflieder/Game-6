package game6.client.effects;

import de.nerogar.render.RenderProperties3f;
import de.nerogar.util.Color;
import de.nerogar.util.Vector3f;

public class Light {

	public Color color;
	public Vector3f position;
	public float reach;
	public float intensity;
	public RenderProperties3f renderProperties;

	private boolean dead;

	public Light(Color color, Vector3f position, float reach, float intensity) {
		this.color = color;
		this.position = position;
		this.reach = reach;
		this.intensity = intensity;

		renderProperties = new RenderProperties3f(0.0f, 0.0f, 0.0f, position.getX(), position.getY(), position.getZ());
		renderProperties.setScale(reach, reach, reach);
	}

	public void setPosition(float x, float y, float z) {
		position.setX(x);
		position.setY(y);
		position.setZ(z);
	}

	public void update(float timeDelta) {
		renderProperties.setXYZ(position);
	}

	public void kill() {
		dead = true;
	}

	public boolean dead() {
		return dead;
	}

}
