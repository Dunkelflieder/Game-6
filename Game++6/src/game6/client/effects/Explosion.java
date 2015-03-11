package game6.client.effects;

import org.lwjgl.input.Keyboard;

import de.nerogar.render.*;
import de.nerogar.util.Color;
import de.nerogar.util.Vector3f;

public class Explosion extends Effect {

	public Vector3f position;

	private WavefrontMesh explosionMesh;
	private RenderProperties3f renderProperties;
	private Light light;

	private float lifeTime = 0;

	private static Shader explosionShader = new Shader("shaders/explosion.vert", "shaders/explosion.frag");

	public Explosion(Vector3f position) {
		this.position = position;

		explosionMesh = WavefrontLoader.loadObject("res/shapes/sphereHR5.obj");
		renderProperties = new RenderProperties3f();
	}

	@Override
	public void initLights(LightContainer lightContainer) {
		light = new Light(new Color(1.0f, 0.2f, 0.2f, 1.0f), position, 8.0f, 1.0f);
		addLight(light, lightContainer);
	}

	@Override
	public void render(Shader shader) {
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			explosionShader.reloadFiles();
			explosionShader.reCompile();
		}

		shader.deactivate();

		explosionShader.activate();
		explosionShader.setUniform1f("offset", lifeTime * 2f);
		explosionShader.setUniform1f("size", lifeTime * 0.3f + 0.1f);
		explosionShader.setUniform1f("heat", 1f - lifeTime);

		TextureLoader.loadTexture("res/colors/fire3.png").bind();
		renderProperties.setXYZ(position);
		explosionMesh.render(renderProperties);
		explosionShader.deactivate();

		shader.activate();
	}

	@Override
	public void update(float timeDelta) {
		lifeTime += timeDelta;

		position.addY(timeDelta * 2.0f);

		light.intensity = (1f - lifeTime) * 1.5f;
		if (lifeTime > 1f) kill();
	}

}
