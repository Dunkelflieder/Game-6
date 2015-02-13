package game6.client.world;

import org.lwjgl.opengl.GL11;

import game6.core.world.CoreMap;
import de.nerogar.render.*;

public class MapMesh extends Renderable {

	private CoreMap map;
	private MapMeshChunk[][] chunks;
	private MapMeshGridChunk[][] gridChunks;
	private Shader shader;

	private boolean gridActivated = false;

	public static final int CHUNKSIZE = 32;

	public MapMesh(CoreMap map) {
		this.map = map;
		this.shader = new Shader("shaders/map.vert", "shaders/map.frag");

		int numX = (int) Math.ceil(map.getSizeX() / (float) CHUNKSIZE);
		int numY = (int) Math.ceil(map.getSizeY() / (float) CHUNKSIZE);
		chunks = new MapMeshChunk[numX][numY];
		gridChunks = new MapMeshGridChunk[numX][numY];

		for (int x = 0; x < chunks.length; x++) {
			for (int y = 0; y < chunks[x].length; y++) {
				chunks[x][y] = new MapMeshChunk(map, x * CHUNKSIZE, y * CHUNKSIZE, Math.min(CHUNKSIZE, map.getSizeX() - x * CHUNKSIZE), Math.min(CHUNKSIZE, map.getSizeY() - y * CHUNKSIZE));
				gridChunks[x][y] = new MapMeshGridChunk(map, x * CHUNKSIZE, y * CHUNKSIZE, Math.min(CHUNKSIZE, map.getSizeX() - x * CHUNKSIZE), Math.min(CHUNKSIZE, map.getSizeY() - y * CHUNKSIZE));
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
				gridChunks[x][y].reload();
			}
		}
		System.gc();
	}

	@Override
	public void render(RenderProperties renderProperties) {

		shader.activate();

		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				chunks[x][y].render(renderProperties);
			}
		}

		if (gridActivated) {
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					gridChunks[x][y].render(renderProperties);
				}
			}
		}

		shader.deactivate();

	}

}
