package game6.client.effects;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import de.nerogar.render.*;

public class LightContainer {

	private List<Light> lights;
	private RenderTarget worldRenderTarget;
	private Shader lightsShader;
	private WavefrontMesh sphere;

	public LightContainer() {
		lights = new ArrayList<Light>();
	}

	public void setWorldRenderTarget(RenderTarget worldRenderTarget) {
		this.worldRenderTarget = worldRenderTarget;

		lightsShader = new Shader("shaders/lights.vert", "shaders/lights.frag");

		sphere = WavefrontLoader.loadObject("res/shapes/sphere.obj");
	}

	public void addLight(Light light) {
		lights.add(light);
	}

	public void update(float timeDelta) {
		for (Light light : lights) {
			light.update(timeDelta);
		}

		for (int i = lights.size() - 1; i >= 0; i--) {
			if (lights.get(i).dead()) lights.remove(i);
		}
	}

	public void render(ScreenProperties screenProperties) {
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			lightsShader.reloadFiles();
			lightsShader.reCompile();
		}

		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);

		glEnable(GL_CULL_FACE);
		glCullFace(GL_FRONT);

		lightsShader.activate();
		lightsShader.setUniform2f("resolution", 1280f, 720f);

		worldRenderTarget.getTexture("position").bind(0);
		worldRenderTarget.getTexture("normal").bind(1);

		lightsShader.setUniform1i("tex_worldPosition", 0);
		lightsShader.setUniform1i("tex_worldNormal", 1);

		for (Light light : lights) {
			lightsShader.setUniform3f("color", light.color.getR(), light.color.getG(), light.color.getB());
			lightsShader.setUniform3f("position", light.position.getX(), light.position.getY(), light.position.getZ());
			lightsShader.setUniform1f("reach", light.reach);
			lightsShader.setUniform1f("intensity", light.intensity);

			sphere.render(light.renderProperties);
		}

		glDisable(GL_BLEND);
		glDisable(GL_CULL_FACE);

		lightsShader.deactivate();

	}

}
