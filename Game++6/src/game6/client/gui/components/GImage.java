package game6.client.gui.components;

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
		render(false);
	}

	public void render(boolean fbo) {
		texture.bind();

		int x = getPosX() + getOffsetX();
		int y = getPosY() + getOffsetY();

		if (fbo) {
			RenderHelper.renderFBOQuadAbsolute(getSizeX(), getSizeY(), x, y);
		} else {
			RenderHelper.renderTextureQuadAbsolute(getSizeX(), getSizeY(), x, y);
		}
	}

	@Override
	public void onFocus() {
	}

	@Override
	public void onUnfocus() {
	}

}
