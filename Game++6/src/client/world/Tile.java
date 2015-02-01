package client.world;

import de.nerogar.render.*;

public enum Tile {
	CHROME(null, null),
	REACTOR(WavefrontLoader.loadObject("res/entities/reactor/mesh.obj"), TextureLoader.loadTexture("res/entities/reactor/color.png"));

	private Renderable renderable;
	private Texture2D texture;

	private Tile(Renderable renderable, Texture2D texture) {
		this.renderable = renderable;
		this.texture = texture;
	}

	public Texture2D getTexture() {
		return texture;
	}

	public Renderable getRenderable() {
		return renderable;
	}

}
