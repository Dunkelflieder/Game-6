package game6.client.world;

import game6.client.buildings.ClientBuilding;
import game6.core.world.Map;
import game6.core.world.Tile;
import de.nerogar.render.*;

public class MapMeshChunk extends Renderable {

	private Map<ClientBuilding> map;
	private boolean vboDirty = true;

	private int posX, posY, sizeX, sizeY;

	public MapMeshChunk(Map<ClientBuilding> map, int posX, int posY, int sizeX, int sizeY) {

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
		// The texture step each tile advanced in the texture
		float step = (1f / TerrainTexture.ROWCOUNT) / span;

		for (int x = posX; x < posX + sizeX; x++) {
			for (int y = posY; y < posY + sizeY; y++) {

				Tile tile = map.getTile(x, y);

				// get texture coorinates in stitched texture
				float texOffsetX = TerrainTexture.getOffsetXForID(tile.getID());
				float texOffsetY = TerrainTexture.getOffsetYForID(tile.getID());

				// get texture coorinates local to previous coordinates for texture-span over multiple tiles.
				float texX = texOffsetX + (x % span) * step;
				float texY = texOffsetY + (y % span) * step;

				int i = 0;
				// current textures-array position
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

		// bind the stitched texture containing all terrain textures
		TerrainTexture.getTexture().bind();
		if (vboDirty) {
			reloadVBO();
		}
		super.render(renderProperties);

	}

}
