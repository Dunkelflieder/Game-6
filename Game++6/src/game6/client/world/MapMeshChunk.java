package game6.client.world;

import game6.core.world.CoreMap;
import game6.core.world.Tile;
import de.nerogar.render.*;

public class MapMeshChunk extends Renderable {

	private CoreMap map;
	private boolean vboDirty = true;

	private int posX, posY, sizeX, sizeY;

	public MapMeshChunk(CoreMap map, int posX, int posY, int sizeX, int sizeY) {

		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;

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

				// TODO enable this again
				// if (map.getBuildingMap()[x][y] != null) {
				// continue;
				// }

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
		int span = 16;
		float step = (1f / TerrainTexture.ROWCOUNT) / span;
		for (int x = posX; x < posX + sizeX; x++) {
			for (int y = posY; y < posY + sizeY; y++) {

				Tile tile = map.getTile(x, y);
				
				float texOffsetX = TerrainTexture.getOffsetXForID(tile.getID());
				float texOffsetY = TerrainTexture.getOffsetYForID(tile.getID());
				
				float texX = texOffsetX + (x % span) * step;
				float texY = texOffsetY + (y % span) * step;

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
			normals[i + 0] = 1;
			normals[i + 1] = 1;
			normals[i + 2] = 1;
			normals[i + 3] = -1;
			normals[i + 4] = 1;
			normals[i + 5] = -1;
		}

		initVBO(vertices, 3, textures, 2, normals, 3);
		vboDirty = false;
	}

	@Override
	public void render(RenderProperties renderProperties) {

		TerrainTexture.getTexture().bind();
		if (vboDirty) {
			reloadVBO();
		}
		super.render(renderProperties);

	}

}
