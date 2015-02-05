package game6.client.gui;

import java.awt.Color;

import static org.lwjgl.opengl.GL11.*;

public class GLabel extends GComponent {

	private String text;
	private Font font;
	private Color color;

	public GLabel() {
		font = Font.DEFAULT;
		color = Color.WHITE;
	}

	public GLabel(String text) {
		this();
		setText(text);
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Font getFont() {
		return font;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public void render(int offsetX, int offsetY) {
		// TODO don't hardcode OpenGL here

		float scale = getSizeY() / 64f;
		
		int x = getPosX() + offsetX;
		int y = getPosY() + offsetY;

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glColor4ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(), (byte) color.getAlpha());
		font.render(x, y, getText(), scale);
		glColor4f(1f, 1f, 1f, 1f);
	}

}