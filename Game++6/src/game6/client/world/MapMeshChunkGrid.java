package game6.client.world;

import game6.client.buildings.ClientBuilding;
import game6.core.world.Map;
import de.nerogar.render.*;
import de.nerogar.render.Texture2D.InterpolationType;

public class MapMeshChunkGrid extends Renderable {

	private Texture2D texture;
	private Map<ClientBuilding> map;
	private boolean vboDirty = true;

	private int posX, posY, sizeX, sizeY;

	public MapMeshChunkGrid(Map<ClientBuilding> map, int posX, int posY, int sizeX, int sizeY) {

		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		this.texture = Texture2DLoader.loadTexture("res/terrain/transparents.png", InterpolationType.NEAREST);
		this.map = map;

		reload();
	}

	public void reload() {
		vboDirty = true;
	}

	public void reloadVBO() {

		int tilesCount = sizeX * sizeY;

		// each tile is a quad with 4 vertices.
		// But we need 2 triangles each. So 6 vertices * 3 components
		float[] vertices = new float[tilesCount * 6 * 3];

		// 1 texture coord per vertex. Only 2 components
		float[] textures = new float[tilesCount * 6 * 2];

		// 1 normal per vertex
		float[] normals = new float[vertices.length];

		// Fill vertices
		for (int x = posX; x < posX + sizeX; x++) {
			for (int y = posY; y < posY + sizeY; y++) {

				// xyz xyz xyz xyz xyz xyz
				int i = 0;
				int pos = ((x - posX) * sizeY + (y - posY)) * 6 * 3;
				vertices[pos + i++] = x;
				vertices[pos + i++] = 0;
				vertices[pos + i++] = y;

				vertices[pos + i++] = x;
				vertices[pos + i++] = 0;
				vertices[pos + i++] = y + 1;

				vertices[pos + i++] = x + 1;
				vertices[pos + i++] = 0;
				vertices[pos + i++] = y;

				vertices[pos + i++] = x + 1;
				vertices[pos + i++] = 0;
				vertices[pos + i++] = y;

				vertices[pos + i++] = x;
				vertices[pos + i++] = 0;
				vertices[pos + i++] = y + 1;

				vertices[pos + i++] = x + 1;
				vertices[pos + i++] = 0;
				vertices[pos + i++] = y + 1;
			}
		}

		// Fill texture coordinates. each texture fills span*span tiles.
		for (int x = posX; x < posX + sizeX; x++) {
			for (int y = posY; y < posY + sizeY; y++) {

				float step = 1 / 2f;
				float texX = ((x + y) % 2) * step;
				float texY = (map.getBuildingAt(x, y) == null) ? 0 : step;

				int i = 0;
				int pos = ((x - posX) * sizeY + (y - posY)) * 6 * 2;

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
			normals[i + 0] = 0;
			normals[i + 1] = 1;
			normals[i + 2] = 0;
			normals[i + 3] = 0;
			normals[i + 4] = 1;
			normals[i + 5] = 0;
		}

		initVBO(vertices, 3, textures, 2, normals, 3);
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
