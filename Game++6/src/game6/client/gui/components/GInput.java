package game6.client.gui.components;

import game6.client.gui.listener.KeyboardListener;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.lwjgl.input.Keyboard;

/**
 * Gui-Component, that acts like a text-input-field. Consumes keystroked when focused.
 * @author Felk
 *
 */
public class GInput extends GComponent implements KeyboardListener {

	private GLabel text;
	private GColorfield bgColor;
	private Color colorUnfocused;

	public GInput(String text) {
		setText(text);
	}

	public GInput() {
	}

	public String getText() {
		return text.getText();
	}

	public void setText(String text) {
		this.text.setText(text);
	}

	@Override
	public void init() {
		addKeyboardListener(this);
		text = new GLabel();
		colorUnfocused = new Color(0, 150, 0);
		bgColor = new GColorfield(colorUnfocused);
	}

	@Override
	public void setSize(int x, int y) {
		super.setSize(x, y);
		text.setSize(x, y);
		bgColor.setSize(x, y);
	}

	@Override
	public void setOffset(int x, int y) {
		super.setOffset(x, y);
		text.setOffset(x, y);
		bgColor.setOffset(x, y);
	}

	@Override
	public void setPos(int x, int y) {
		super.setPos(x, y);
		text.setPos(x + 10, y);
		bgColor.setPos(x, y);
	}

	@Override
	public void render() {
		bgColor.render();
		text.render();
	}

	@Override
	public boolean keyPressed(GComponent source, int keyCode, char key) {
		if (isFocused()) {
			Character.UnicodeBlock block = Character.UnicodeBlock.of(key);
			if (keyCode == Keyboard.KEY_BACK) {
				if (text.getText().length() > 0) {
					text.setText(text.getText().substring(0, text.getText().length() - 1));
				}
				return true;
			} else if (!Character.isISOControl(key) && key != KeyEvent.CHAR_UNDEFINED && block != null && block != Character.UnicodeBlock.SPECIALS) {
				text.setText(text.getText() + key);
			}
		}
		return false;
	}

	@Override
	public boolean keyReleased(GComponent source, int keyCode, char key) {
		return false;
	}

	@Override
	public void onFocus() {
		bgColor.setColor(Color.GREEN);
	}

	@Override
	public void onUnfocus() {
		bgColor.setColor(colorUnfocused);
	}

}
