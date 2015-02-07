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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;

import de.nerogar.render.Texture2D;
import de.nerogar.render.Texture2D.InterpolationType;
import de.nerogar.render.TextureLoader;

public class Font {

	private int charSize = 64;
	private int charRowNum = 16;
	private int kerning = 0;

	private Texture2D fontTexture;
	private int[] widths;

	public static final int PLAIN = java.awt.Font.PLAIN;
	public static final int BOLD = java.awt.Font.BOLD;
	public static final int ITALIC = java.awt.Font.ITALIC;
	
	public static final int LEFT = 10;
	public static final int CENTER = 11;
	public static final int RIGHT = 12;

	// public static final Font DEFAULT = new Font("Times New Roman", PLAIN);
	public static final Font DEFAULT = new Font("res/defaultFont");

	private boolean debug_saveDefaultFontIfNotExists = true;

	public Font(String filename) {
		fontTexture = TextureLoader.loadTexture(filename + ".png", "defaultFont", InterpolationType.LINEAR);
		File widthsFile = new File(filename + ".widths");
		if (fontTexture == null || widthsFile.isDirectory() || !widthsFile.exists()) {

			System.err.println("Could not load font: " + filename + ", creating font from scratch.");
			BufferedImage defaultFont = makeFontmapFromAwt("Calibri", PLAIN);
			int[] widths = getWidths(defaultFont);

			if (debug_saveDefaultFontIfNotExists) {
				try {
					ImageIO.write(defaultFont, "png", new File(filename + ".png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try (PrintWriter writer = new PrintWriter(widthsFile)) {
					String str = Arrays.toString(widths);
					writer.println(str.substring(1, str.length() - 1));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			fontTexture = TextureLoader.loadTexture(defaultFont, "defaultFont", InterpolationType.LINEAR);
			this.widths = widths;
		} else {
			widths = new int[charRowNum * charRowNum];
			try (Scanner scanner = new Scanner(widthsFile)) {
				if (!scanner.hasNextLine()) {
					System.err.println("font widths file is empty!");
				} else {
					String line = scanner.nextLine();
					int i = 0;
					for (String part : line.split(",")) {
						widths[i] = Integer.parseInt(part.trim());
						i++;
						if (i >= line.length()) {
							break;
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

		}
	}

	public Font(String name, int style) {
		fontTexture = TextureLoader.loadTexture(makeFontmapFromAwt(name, style), "defaultFont", InterpolationType.LINEAR);
	}

	private String getStringFromUnicode(int unicode) {
		return String.valueOf(Character.toChars(unicode));
	}

	private int[] getWidths(BufferedImage fontImage) {

		int[] widths = new int[charRowNum * charRowNum];

		for (int i = 0; i < widths.length; i++) {

			if (i == ' ') {
				widths[i] = (byte) (charSize * 0.3f);
				continue;
			}

			int x = i % charRowNum;
			int y = i / charRowNum;

			widths[i] = (byte) charSize;

			outer: while (widths[i] > 0) {

				for (int scanY = 0; scanY < charSize; scanY++) {
					int color = fontImage.getRGB(x * charSize + widths[i] - 1, y * charSize + scanY);
					if ((color >>> 24) > 0) {
						break outer;
					}
				}

				widths[i]--;
			}

		}

		return widths;
	}

	private BufferedImage makeFontmapFromAwt(String name, int style) {

		int size = 64;

		BufferedImage image = new BufferedImage(charSize * charRowNum, charSize * charRowNum, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		java.awt.Font font = new java.awt.Font(name, style, size - 16);
		g.setFont(font);

		g.setColor(Color.WHITE);
		for (int x = 0; x < charRowNum; x++) {
			for (int y = 0; y < charRowNum; y++) {
				int offset = y * charRowNum + x;
				g.drawString(getStringFromUnicode(offset), x * charSize + 2, (y + 1) * charSize - 16);
			}
		}

		return image;
	}

	public void render(int posX, int posY, String text, float scale) {
		fontTexture.bind();

		float step = 1f / charRowNum;
		char[] chars = text.toCharArray();

		// TODO don't hardcode OpenGL here
		glBegin(GL_QUADS);
		int offsetX = 0;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			float texX = (c % charRowNum) / (float) charRowNum;
			float texY = (c / charRowNum) / (float) charRowNum;

			int x = posX + offsetX;
			offsetX += getWidhtFor(c) * scale;

			glTexCoord2f(texX, texY + step);
			glVertex3f(x, posY, -1);

			glTexCoord2f(texX + step, texY + step);
			glVertex3f(x + charSize * scale, posY, -1);

			glTexCoord2f(texX + step, texY);
			glVertex3f(x + charSize * scale, posY + charSize * scale, -1);

			glTexCoord2f(texX, texY);
			glVertex3f(x, posY + charSize * scale, -1);

		}
		glEnd();

	}

	public int getWidthFor(String text) {
		int l = 0;
		for (char c : text.toCharArray()) {
			l += getWidhtFor(c);
		}
		return l;
	}

	public int getWidhtFor(char c) {
		return widths[c] + kerning;
	}
}
