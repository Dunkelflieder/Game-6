package game6.client.world;

import org.lwjgl.opengl.GL11;

import game6.core.world.Map;
import de.nerogar.render.*;

public class MapMesh {

	private MapMeshChunk[][] chunks;
	private MapMeshChunkGrid[][] chunksGrid;
	private Shader shader;

	private boolean gridActivated = false;

	public static final int CHUNKSIZE = 32;
	public static final int CHUNK_RENDER_RADIUS = 6;

	public MapMesh(Map map) {
		this.shader = new Shader("shaders/map.vert", "shaders/map.frag");

		int numX = (int) Math.ceil(map.getSizeX() / (float) CHUNKSIZE);
		int numY = (int) Math.ceil(map.getSizeY() / (float) CHUNKSIZE);
		chunks = new MapMeshChunk[numX][numY];
		chunksGrid = new MapMeshChunkGrid[numX][numY];

		for (int x = 0; x < chunks.length; x++) {
			for (int y = 0; y < chunks[x].length; y++) {
				chunks[x][y] = new MapMeshChunk(map, x * CHUNKSIZE, y * CHUNKSIZE, Math.min(CHUNKSIZE, map.getSizeX() - x * CHUNKSIZE), Math.min(CHUNKSIZE, map.getSizeY() - y * CHUNKSIZE));
				chunksGrid[x][y] = new MapMeshChunkGrid(map, x * CHUNKSIZE, y * CHUNKSIZE, Math.min(CHUNKSIZE, map.getSizeX() - x * CHUNKSIZE), Math.min(CHUNKSIZE, map.getSizeY() - y * CHUNKSIZE));
			}
		}

		reload(0, 0, map.getSizeX(), map.getSizeY());
	}

	public boolean isGridActivated() {
		return gridActivated;
	}

	public void setGridActivated(boolean is) {
		gridActivated = is;
	}

	public void reload(int posX, int posY, int sizeX, int sizeY) {
		for (int x = (int) Math.floor(posX / (float) CHUNKSIZE); x < (posX + sizeX) / (float) CHUNKSIZE; x++) {
			for (int y = (int) Math.floor(posY / (float) CHUNKSIZE); y < (posY + sizeY) / (float) CHUNKSIZE; y++) {
				chunks[x][y].reload();
				chunksGrid[x][y].reload();
			}
		}
	}

	public void render(RenderProperties renderProperties, float atX, float atY) {

		shader.activate();

		for (int x = (int) (atX / CHUNKSIZE - CHUNK_RENDER_RADIUS); x < atX / CHUNKSIZE + CHUNK_RENDER_RADIUS; x++) {
			for (int y = (int) (atY / CHUNKSIZE - CHUNK_RENDER_RADIUS); y < atY / CHUNKSIZE + CHUNK_RENDER_RADIUS; y++) {
				if (x < 0 || y < 0 || x >= chunks.length || y >= chunks[0].length) {
					continue;
				}
				chunks[x][y].render(renderProperties);
			}
		}

		// TODO don't render the grid as a 2nd mesh, but rather in the shader. Improves performance.
		if (gridActivated) {
			// TODO don't avoid z-fighting by clearing depth buffer
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			for (int x = (int) (atX / CHUNKSIZE - CHUNK_RENDER_RADIUS); x < atX / CHUNKSIZE + CHUNK_RENDER_RADIUS; x++) {
				for (int y = (int) (atY / CHUNKSIZE - CHUNK_RENDER_RADIUS); y < atY / CHUNKSIZE + CHUNK_RENDER_RADIUS; y++) {
					if (x < 0 || y < 0 || x >= chunksGrid.length || y >= chunksGrid[0].length) {
						continue;
					}
					chunksGrid[x][y].render(renderProperties);
				}
			}
		}

		shader.deactivate();

	}

	public void cleanup() {
		for (int x = 0; x < chunks.length; x++) {
			for (int y = 0; y < chunks[x].length; y++) {
				chunks[x][y].cleanup();
				chunksGrid[x][y].cleanup();
			}
		}
	}

}
