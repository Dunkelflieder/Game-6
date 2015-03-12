package game6.client.gui.components;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import de.nerogar.render.Texture2D;
import de.nerogar.render.Texture2DLoader;

/**
 * Gui-Component, that displays a part of a texture. The underlying texture is interpreted as a grid of images.
 * The portion that should be rendered can be set.
 * @author Felk
 *
 */
public class GMultiimage extends GComponent {

	private Texture2D texture;
	private int cols, rows;
	private int texX, texY;

	public GMultiimage(String filename, int cols, int rows) {
		texture = Texture2DLoader.loadTexture(filename);
		this.cols = cols;
		this.rows = rows;
	}

	public GMultiimage(Texture2D texture, int cols, int rows) {
		this.texture = texture;
		this.cols = cols;
		this.rows = rows;
	}

	public void setIndex(int i) {
		setIndex(i % cols, i / cols);
	}

	public void setIndex(int x, int y) {
		texX = x;
		texY = y;
	}

	@Override
	public void render() {
		// TODO don't hardcode OpenGL here
		texture.bind();

		int x = getPosX() + getOffsetX();
		int y = getPosY() + getOffsetY();

		float stepX = 1f / cols;
		float stepY = 1f / rows;
		float texX = this.texX * stepX;
		float texY = this.texY * stepY;

		glBegin(GL_QUADS);

		glTexCoord2f(texX, texY + stepY);
		glVertex3f(x, y, -1);

		glTexCoord2f(texX + stepX, texY + stepY);
		glVertex3f(x + getSizeX(), y, -1);

		glTexCoord2f(texX + stepX, texY);
		glVertex3f(x + getSizeX(), y + getSizeY(), -1);

		glTexCoord2f(texX, texY);
		glVertex3f(x, y + getSizeY(), -1);

		glEnd();
	}

	@Override
	public void onFocus() {
	}

	@Override
	public void onUnfocus() {
	}

}
