package game6.client.gui.components;

import game6.client.world.Minimap;

public class GMinimap extends GImage {

	private Minimap minimap;

	public GMinimap(Minimap minimap) {
		super(minimap.getTexture());
		this.minimap = minimap;
	}

	@Override
	public void update() {
		super.update();
		super.setTexture(minimap.getTexture());
	}

	@Override
	public void render() {
		if (minimap.getTexture() != null) {
			super.render();
		}
	}

}
