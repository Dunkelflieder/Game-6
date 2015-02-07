package game6.client.gui;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

public class GColorfield extends GComponent {

	private Color color;

	public GColorfield(Color color) {
		this.color = color;
	}
	
	@Override
	public void render(int offsetX, int offsetY) {
		// TODO don't hardcode OpenGL here
		int x = getPosX() + offsetX;
		int y = getPosY() + offsetY;
		
		glDisable(GL_TEXTURE_2D);

		glColor4ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(), (byte) color.getAlpha());
		glBegin(GL_QUADS);
		glVertex3f(x, y, -1);
		glVertex3f(x + getSizeX(), y, -1);
		glVertex3f(x + getSizeX(), y + getSizeY(), -1);
		glVertex3f(x, y + getSizeY(), -1);
		glEnd();
		glColor4f(1f, 1f, 1f, 1f);
		
		glEnable(GL_TEXTURE_2D);
	}

	@Override
	public void onFocus() {
	}

	@Override
	public void onUnfocus() {
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
