package game6.client.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.lwjgl.input.Keyboard;

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
	public void setSizeX(int x) {
		super.setSizeX(x);
		text.setSizeX(x);
		bgColor.setSizeX(x);
	}

	@Override
	public void setSizeY(int y) {
		super.setSizeY(y);
		text.setSizeY(y);
		bgColor.setSizeY(y);
	}

	@Override
	public void setSize(int x, int y) {
		setSizeX(x);
		setSizeY(y);
	}

	@Override
	public void render(int offsetX, int offsetY) {
		bgColor.render(getPosX() + offsetX, getPosY() + offsetY);
		text.render(getPosX() + offsetX + 10, getPosY() + offsetY);
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
