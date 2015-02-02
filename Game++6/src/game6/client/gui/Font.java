package game6.client.gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.nerogar.render.Texture2D;
import de.nerogar.render.TextureLoader;

public class Font {

	private int charSize = 64;
	private int charRowNum = 16;

	private Texture2D fontTexture;

	public static final int PLAIN = java.awt.Font.PLAIN;
	public static final int BOLD = java.awt.Font.BOLD;
	public static final int ITALIC = java.awt.Font.ITALIC;

	public static final Font DEFAULT = new Font("Times New Roman", PLAIN);

	public Font(String name, int style) {
		this.fontTexture = loadFontFromAwt(name, style, 64);
	}

	private String getStringFromUnicode(int unicode) {
		return String.valueOf(Character.toChars(unicode));
	}

	private Texture2D loadFontFromAwt(String name, int style, int size) {

		BufferedImage image = new BufferedImage(charSize * charRowNum, charSize * charRowNum, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		java.awt.Font font = new java.awt.Font(name, style, size - 16);
		g.setColor(Color.BLACK);
		//g.fillRect(0, 0, charRowNum * charSize, charRowNum * charSize);
		g.setFont(font);

		g.setColor(Color.WHITE);
		for (int x = 0; x < charRowNum; x++) {
			for (int y = 0; y < charRowNum; y++) {
				int offset = y * charRowNum + x;
				g.drawString(getStringFromUnicode(offset), x * charSize, (y + 1) * charSize - 16);
			}
		}
		try {
			// TODO Get Texture2D From TextureLoader by BufferedImage
			ImageIO.write(image, "png", new File("res/font.png"));
			return TextureLoader.loadTexture("res/font.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void render(int posX, int posY, String text) {
		fontTexture.bind();

		float step = 1f / charRowNum;
		char[] chars = text.toCharArray();

		// TODO don't hardcode OpenGL here
		glBegin(GL_QUADS);
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			float texX = (c % charRowNum) / (float) charRowNum;
			float texY = (c / charRowNum) / (float) charRowNum;

			int x = posX + charSize * i;

			glTexCoord2f(texX, texY + step);
			glVertex3f(x, posY, -1);

			glTexCoord2f(texX + step, texY + step);
			glVertex3f(x + charSize, posY, -1);

			glTexCoord2f(texX + step, texY);
			glVertex3f(x + charSize, posY + charSize, -1);

			glTexCoord2f(texX, texY);
			glVertex3f(x, posY + charSize, -1);

		}
		glEnd();

	}
}
