package game6.client.world;

import game6.core.world.CoreMap;
import de.nerogar.render.RenderProperties;
import de.nerogar.render.Renderable;
import de.nerogar.render.Texture2D;
import de.nerogar.render.TextureLoader;
import de.nerogar.render.Texture2D.InterpolationType;

public class MapMesh extends Renderable {

	private Texture2D texture;
	private CoreMap map;
	private boolean vboDirty = true;

	public MapMesh(CoreMap map) {
		this.texture = TextureLoader.loadTexture("res/terrain/chrome3.png", InterpolationType.NEAREST);
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

				if (map.getBuildingMap()[x][y] == null) {
					// xyz xyz xyz xyz xyz xyz
					int i = 0;
					float error = 0.0f;
					float heightReduce = 1;
					int pos = (x * map.getSizeX() + y) * 6 * 3;
					vertices[pos + i++] = x + error;
					vertices[pos + i++] = map.getHeight(x, y) * heightReduce;
					vertices[pos + i++] = y + error;

					vertices[pos + i++] = x + error;
					vertices[pos + i++] = map.getHeight(x, y + 1) * heightReduce;
					vertices[pos + i++] = y + 1;

					vertices[pos + i++] = x + 1;
					vertices[pos + i++] = map.getHeight(x + 1, y) * heightReduce;
					vertices[pos + i++] = y + error;

					vertices[pos + i++] = x + 1;
					vertices[pos + i++] = map.getHeight(x + 1, y) * heightReduce;
					vertices[pos + i++] = y + error;

					vertices[pos + i++] = x + error;
					vertices[pos + i++] = map.getHeight(x, y + 1) * heightReduce;
					vertices[pos + i++] = y + 1;

					vertices[pos + i++] = x + 1;
					vertices[pos + i++] = map.getHeight(x + 1, y + 1) * heightReduce;
					vertices[pos + i++] = y + 1;
				}
			}
		}

		// Fill texture coordinates. each texture fills span*span tiles.
		int span = 16;
		float step = 1f / span;
		for (int x = 0; x < map.getSizeX(); x++) {
			for (int y = 0; y < map.getSizeY(); y++) {
				
				float texX = (x % span) * step;
				float texY = (y % span) * step;
				
				int i = 0;
				int pos = (x * map.getSizeX() + y) * 6 * 2;
				
				textures[pos + i++] = texX;
				textures[pos + i++] = texY;
				textures[pos + i++] = texX;
				textures[pos + i++] = texY + step;
				textures[pos + i++] = texX + step;
				textures[pos + i++] = texY;
				
				textures[pos + i++] = texX + step;
				textures[pos + i++] = texY;
				textures[pos + i++] = texX;
				textures[pos + i++] = texY + step;
				textures[pos + i++] = texX + step;
				textures[pos + i++] = texY + step;
				
			}
		}

		// Fill normals. Alternating diagonals for now
		for (int i = 0; i < normals.length; i += 6) {
			normals[i + 0] = 1;
			normals[i + 1] = 1;
			normals[i + 2] = 1;
			normals[i + 3] = -1;
			normals[i + 4] = 1;
			normals[i + 5] = -1;
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
