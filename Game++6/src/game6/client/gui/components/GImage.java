package game6.client.gui.components;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import de.nerogar.render.Texture2D;
import de.nerogar.render.TextureLoader;

public class GImage extends GComponent {

	private static Texture2D texture;

	public GImage(String filename) {
		texture = TextureLoader.loadTexture(filename);
	}
	
	public GImage(Texture2D texture) {
		this.texture = texture;
	}

	@Override
	public void render(int offsetX, int offsetY) {
		// TODO don't hardcode OpenGL here
		texture.bind();

		int x = getPosX() + offsetX;
		int y = getPosY() + offsetY;

		glBegin(GL_QUADS);

		glTexCoord2f(0, 0);
		glVertex3f(x, y, -1);

		glTexCoord2f(1, 0);
		glVertex3f(x + getSizeX(), y, -1);

		glTexCoord2f(1, 1);
		glVertex3f(x + getSizeX(), y + getSizeY(), -1);

		glTexCoord2f(0, 1);
		glVertex3f(x, y + getSizeY(), -1);

		glEnd();
	}

}
