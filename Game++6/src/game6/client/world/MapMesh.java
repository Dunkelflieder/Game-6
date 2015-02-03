package game6.client.world;

import game6.core.CoreMap;
import de.nerogar.render.RenderProperties;
import de.nerogar.render.Renderable;
import de.nerogar.render.Texture2D;
import de.nerogar.render.TextureLoader;

public class MapMesh extends Renderable {

	private Texture2D texture;
	private CoreMap map;
	private boolean vboDirty = true;

	public MapMesh(CoreMap map) {
		this.texture = TextureLoader.loadTexture("res/terrain/chrome.png");
		this.map = map;
		reload();
	}

	public void reload() {
		vboDirty = true;
	}

	public void reloadVBO() {

		int tilesCount = map.getSizeX() * map.getSizeY();

		// each tile is a quad with 4 vertices.
		// But we need 2 triangles each. So 6 vertices * 3 components
		float[] vertices = new float[tilesCount * 6 * 3];

		// 1 texture coord per vertex. Only 2 components
		float[] textures = new float[tilesCount * 6 * 2];

		// 1 normal per vertex
		float[] normals = new float[vertices.length];

		// Fill vertices
		for (int x = 0; x < map.getSizeX(); x++) {
			for (int y = 0; y < map.getSizeY(); y++) {

				if (map.getBuildings()[x][y] == null) {
					// xyz xyz xyz xyz xyz xyz
					int i = 0;
					float error = 0.0f;
					int pos = (x * map.getSizeX() + y) * 6 * 3;
					vertices[pos + i++] = x + error;
					vertices[pos + i++] = 0;
					vertices[pos + i++] = y + error;

					vertices[pos + i++] = x + error;
					vertices[pos + i++] = 0;
					vertices[pos + i++] = y + 1;

					vertices[pos + i++] = x + 1;
					vertices[pos + i++] = 0;
					vertices[pos + i++] = y + error;

					vertices[pos + i++] = x + 1;
					vertices[pos + i++] = 0;
					vertices[pos + i++] = y + error;

					vertices[pos + i++] = x + error;
					vertices[pos + i++] = 0;
					vertices[pos + i++] = y + 1;

					vertices[pos + i++] = x + 1;
					vertices[pos + i++] = 0;
					vertices[pos + i++] = y + 1;
				}
			}
		}

		// Fill texture coords. Full texture for now
		for (int i = 0; i < textures.length; i += 6 * 2) {
			// xy xy
			textures[i + 0] = 0;
			textures[i + 1] = 0;
			textures[i + 2] = 0;
			textures[i + 3] = 1;
			textures[i + 4] = 1;
			textures[i + 5] = 0;

			textures[i + 6] = 1;
			textures[i + 7] = 0;
			textures[i + 8] = 0;
			textures[i + 9] = 1;
			textures[i + 10] = 1;
			textures[i + 11] = 1;
		}

		// Fill normals. Always upwards for now
		for (int i = 0; i < normals.length; i += 3) {
			normals[i + 0] = 0;
			normals[i + 1] = 1;
			normals[i + 2] = 0;
		}

		setVertexData(vertices, 3);
		setTextureData(textures, 2);
		setNormalData(normals, 3);
		initVBO();
		vboDirty = false;
	}

	@Override
	public void render(RenderProperties renderProperties) {
		texture.bind();
		if (vboDirty) {
			reloadVBO();
		}
		super.render(renderProperties);
	}

}