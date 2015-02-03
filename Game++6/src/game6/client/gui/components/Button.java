package game6.client.gui.components;

import game6.client.gui.Font;
import game6.client.gui.components.listener.ButtonClickedListener;
import game6.client.gui.components.listener.MouseListener;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

import de.nerogar.render.Texture2D;
import de.nerogar.render.TextureLoader;

public class Button extends Component implements MouseListener {

	private List<ButtonClickedListener> buttonClickedListener = new ArrayList<>();

	private String text;
	private Font font;

	private static Texture2D buttonTexture;
	static {
		buttonTexture = TextureLoader.loadTexture("res/terrain/grass.png");
	}

	public Button() {
		addMouseListener(this);
		font = Font.DEFAULT;
	}

	public Button(String text) {
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

	@Override
	public void render() {
		// TODO don't hardcode OpenGL here
		buttonTexture.bind();
		glBegin(GL_QUADS);

		glTexCoord2f(0, 0);
		glVertex3f(getPosX(), getPosY(), -1);

		glTexCoord2f(1, 0);
		glVertex3f(getPosX() + getSizeX(), getPosY(), -1);

		glTexCoord2f(1, 1);
		glVertex3f(getPosX() + getSizeX(), getPosY() + getSizeY(), -1);

		glTexCoord2f(0, 1);
		glVertex3f(getPosX(), getPosY() + getSizeY(), -1);

		glEnd();

		//glEnable(GL_BLEND);
		//glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		font.render(getPosX(), getPosY(), getText());
	}

	public boolean addButtonClickedListener(ButtonClickedListener listener) {
		return buttonClickedListener.add(listener);
	}

	public boolean removeButtonClickedListener(ButtonClickedListener listener) {
		return buttonClickedListener.remove(listener);
	}

	private void notifyButtonClickedListener(int button) {
		for (ButtonClickedListener listener : buttonClickedListener) {
			listener.buttonClicked(button);
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
		notifyButtonClickedListener(button);
	}

	@Override
	public void mouseReleased(int button) {
	}

}
