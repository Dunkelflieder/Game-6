package game6.client.gui.components;

import game6.client.gui.Font;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

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

		int x = getPosX() + offsetX;
		int y = getPosY() + offsetY;

		// glEnable(GL_BLEND);
		// glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(), (byte) color.getAlpha());
		font.render(x, y, getText());
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

}
