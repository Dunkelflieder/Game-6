package game6.client.effects;

import de.nerogar.render.*;
import de.nerogar.util.Color;
import de.nerogar.util.Vector3f;

public class SelectionMarker extends Effect {

	public Vector3f position;
	public float sizeX, sizeY;
	public Color color;

	private WavefrontMesh markerMesh;
	private RenderProperties3f renderProperties;

	public SelectionMarker(Vector3f position, float sizeX, float sizeY) {
		this.position = position;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		// color = new Color(0.8f, 0.8f, 0.1f, 1.0f);
		color = new Color(0.1f, 0.8f, 0.1f, 0.1f);

		markerMesh = WavefrontLoader.loadObject("res/shapes/selectionMarker.obj");
		renderProperties = new RenderProperties3f();
	}

	@Override
	public void initLights(LightContainer lightContainer) {
		addLight(new Light(color, position, 3.0f, 0.5f), lightContainer);
	}

	@Override
	public void render(Shader shader) {
		setColor(shader, color);
		renderProperties.setXYZ(position);
		renderProperties.setScale(sizeX, 1.0f, sizeY);
		markerMesh.render(renderProperties);
	}

	@Override
	public void update(float timeDelta) {

	}

}
