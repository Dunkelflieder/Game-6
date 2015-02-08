package game6.client.gui.components;

import game6.client.buildings.BuildingImageRenderer;
import game6.client.gui.listener.ClickedListener;
import game6.client.gui.listener.MouseListener;
import game6.core.buildings.BuildingType;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.render.GameDisplay;

public class GBuilding extends GComponent implements MouseListener {

	private List<ClickedListener> clickedListener = new ArrayList<>();

	private GImage image;
	private BuildingType type;

	public GBuilding(GameDisplay display, BuildingType type) {
		this.type = type;
		image = new GImage(BuildingImageRenderer.render(display, type.getClientBuilding(0)));
	}
	
	public BuildingType getBuildingType() {
		return type;
	}

	@Override
	public void init() {
		addMouseListener(this);
	}

	@Override
	public void setSizeX(int x) {
		super.setSizeX(x);
		image.setSizeX(x);
	}

	@Override
	public void setSizeY(int y) {
		super.setSizeY(y);
		image.setSizeY(y);
	}

	@Override
	public void setSize(int x, int y) {
		setSizeX(x);
		setSizeY(y);
	}

	@Override
	public void render(int offsetX, int offsetY) {
		image.render(getPosX() + offsetX, getPosY() + offsetY);
	}

	public boolean addClickedListener(ClickedListener listener) {
		return clickedListener.add(listener);
	}

	public boolean removeClickedListener(ClickedListener listener) {
		return clickedListener.remove(listener);
	}

	private void notifyClickedListener() {
		for (ClickedListener listener : clickedListener) {
			listener.clicked(this);
		}
	}

	@Override
	public void mouseEntered(GComponent source) {
	}

	@Override
	public void mouseLeft(GComponent source) {
	}

	@Override
	public boolean mouseClicked(GComponent source, int button) {
		if (button == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseReleased(GComponent source, int button) {
		if (button == 0) {
			if (isCurrentlyHovered()) {
				notifyClickedListener();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseWheel(GComponent source, int delta) {
		return false;
	}

	@Override
	public boolean mouseMoved(GComponent source, int dx, int dy) {
		return false;
	}

	@Override
	public void onFocus() {
	}

	@Override
	public void onUnfocus() {
	}

}
