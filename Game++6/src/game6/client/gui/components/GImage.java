package game6.client.gui.components;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;

import de.nerogar.render.*;
import de.nerogar.render.Texture2D.InterpolationType;

/**
 * Gui-Component, that renders a given texture within its bounds
 * @author Felk
 *
 */
public class GImage extends GComponent {

	private Texture2D texture;

	public GImage(String filename) {
		texture = TextureLoader.loadTexture(filename);
	}

	public GImage(BufferedImage image, String name) {
		texture = TextureLoader.loadTexture(image, name, InterpolationType.NEAREST);
	}

	public GImage(Texture2D texture) {
		this.texture = texture;
	}

	public void setTexture(Texture2D texture) {
		this.texture = texture;
	}

	@Override
	public void render() {
		// TODO don't hardcode OpenGL here
		texture.bind();

		int x = getPosX() + getOffsetX();
		int y = getPosY() + getOffsetY();

		glBegin(GL_QUADS);

		glTexCoord2f(0, 1);
		glVertex3f(x, y, -1);

		glTexCoord2f(1, 1);
		glVertex3f(x + getSizeX(), y, -1);

		glTexCoord2f(1, 0);
		glVertex3f(x + getSizeX(), y + getSizeY(), -1);

		glTexCoord2f(0, 0);
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
