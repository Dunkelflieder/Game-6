package game6.client.gui;

import org.lwjgl.input.Keyboard;

public class GInputNumber extends GInput {

	public GInputNumber(int number) {
		setText(String.valueOf(number));
	}

	public GInputNumber() {
	}

	@Override
	public boolean keyPressed(GComponent source, int keyCode, char key) {
		if (!Character.isDigit(key) && keyCode != Keyboard.KEY_BACK) {
			return false;
		}
		return super.keyPressed(source, keyCode, key);
	}

	public int getNumber() {
		try {
			return Integer.parseInt(getText());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
