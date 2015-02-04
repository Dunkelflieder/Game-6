package game6.client.gui.components;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import game6.client.gui.components.listener.ButtonClickedListener;
import game6.client.gui.components.listener.MouseListener;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.render.Texture2D;
import de.nerogar.render.TextureLoader;

public class GButton extends GComponent implements MouseListener {

	private List<ButtonClickedListener> buttonClickedListener = new ArrayList<>();

	public GLabel text;
	private GImage buttonImage;
	private Texture2D texture;

	private boolean clicked = false;

	public GButton(String text) {
		this.text.setText(text);
	}

	@Override
	public void init() {
		addMouseListener(this);
		text = new GLabel();
		// TODO make and use proper button texture
		buttonImage = new GImage("res/buttons.png");
		texture = TextureLoader.loadTexture("res/buttons.png");
		// TODO add hover and click textures
	}

	@Override
	public void setSizeX(int x) {
		super.setSizeX(x);
		text.setSizeX(x);
		buttonImage.setSizeX(x);
	}

	@Override
	public void setSizeY(int y) {
		super.setSizeY(y);
		text.setSizeY(y);
		buttonImage.setSizeY(y);
	}

	@Override
	public void setSize(int x, int y) {
		setSizeX(x);
		setSizeY(y);
	}

	@Override
	public void render(int offsetX, int offsetY) {
		// buttonImage.render(getPosX() + offsetX, getPosY() + offsetY);

		float step = 1 / 4f;
		float offset = 0f;
		if (isCurrentlyHovered()) {
			offset = step;
		}
		if (clicked) {
			offset = 2 * step;
		}

		texture.bind();

		int x = getPosX() + offsetX;
		int y = getPosY() + offsetY;

		glBegin(GL_QUADS);

		glTexCoord2f(0, offset + step);
		glVertex3f(x, y, -1);

		glTexCoord2f(1, offset + step);
		glVertex3f(x + getSizeX(), y, -1);

		glTexCoord2f(1, offset);
		glVertex3f(x + getSizeX(), y + getSizeY(), -1);

		glTexCoord2f(0, offset);
		glVertex3f(x, y + getSizeY(), -1);

		glEnd();

		text.render(getPosX() + offsetX + 10, getPosY() + offsetY);
	}

	public boolean addButtonClickedListener(ButtonClickedListener listener) {
		return buttonClickedListener.add(listener);
	}

	public boolean removeButtonClickedListener(ButtonClickedListener listener) {
		return buttonClickedListener.remove(listener);
	}

	private void notifyButtonClickedListener() {
		for (ButtonClickedListener listener : buttonClickedListener) {
			listener.buttonClicked();
		}
	}

	@Override
	public void mouseEntered() {
	}

	@Override
	public void mouseLeft() {
	}

	@Override
	public void mouseClicked(int button) {
		if (button == 0) {
			clicked = true;
		}
	}

	@Override
	public void mouseReleased(int button) {
		if (button == 0 && isCurrentlyHovered()) {
			clicked = false;
			notifyButtonClickedListener();
		}
	}

}
