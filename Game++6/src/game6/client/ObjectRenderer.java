package game6.client;

import de.nerogar.render.*;
import de.nerogar.util.Matrix4f;

public class ObjectRenderer {

	public Texture2D colorTexture;
	public Texture2D lightTexture;
	public Texture2D factionTexture;
	public Renderable[] meshes;

	public ObjectRenderer(Texture2D colorTexture, Texture2D lightTexture, Texture2D factionTexture, Renderable... meshes) {
		this.colorTexture = colorTexture;
		this.lightTexture = lightTexture;
		this.factionTexture = factionTexture;
		this.meshes = meshes;
	}

	public void render(Shader shader, Matrix4f... modelMatrix) {
		colorTexture.bind(0);
		lightTexture.bind(1);
		factionTexture.bind(2);

		for (int i = 0; i < meshes.length; i++) {
			if (shader != null) shader.setUniformMat4f("modelMatrix", modelMatrix[i].asBuffer());
			meshes[i].render(null);
		}
	}

}
