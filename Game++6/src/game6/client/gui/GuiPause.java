package game6.client.gui;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import game6.client.gui.components.*;
import game6.client.gui.listener.KeyboardAdapter;

/**
 * Gui, which represents the Pause-Menu
 * @author Felk
 *
 */
public class GuiPause extends Gui {

	public static GuiPause instance = new GuiPause();

	private GLabel text;
	private GButton resume;
	private GButton exit;

	@Override
	public void initComponents() {
		GColorfield background = new GColorfield(new Color(0f, 0f, 0f, 0.5f));
		background.setPos(0, 0);
		background.setSize(99999, 99999);

		text = new GLabel("Menü");
		text.setAlignment(Font.CENTER);
		resume = new GButton("Fortfahren");
		exit = new GButton("Beenden");

		resume.addClickedListener(source -> Guis.select(Guis.INGAME));

		// exit pause menu
		panel.addKeyboardListener(new KeyboardAdapter() {
			@Override
			public boolean keyPressed(GComponent source, int keyCode, char key) {
				if (keyCode == Keyboard.KEY_ESCAPE) {
					Guis.select(Guis.INGAME);
				}
				return false;
			}
		});

		// disconnect and return to title screen
		exit.addClickedListener(source -> {
			GuiIngame.instance.reset();
			controller.disconnect();
			Guis.select(Guis.TITLESCREEN);
		});

		add(background);
		add(text);
		add(resume);
		add(exit);
	}

	@Override
	public void onSelect() {
	}

	@Override
	public void onDeselect() {
	}

	@Override
	public void onResize(int screenWidth, int screenHeight) {
		text.setPos(0, screenHeight - 200);
		text.setSize(screenWidth, 60);

		resume.setPos((screenWidth - 320) / 2, (int) (screenHeight * 0.5f));
		resume.setSize(320, 80);

		exit.setPos((screenWidth - 320) / 2, (int) (screenHeight * 0.5f - 120));
		exit.setSize(320, 80);
	}

}
