package game6.client.entities.guis;

import game6.client.gui.components.GPanel;
import game6.core.entities.CoreEntity;

public abstract class EntityGui<T extends CoreEntity> extends GPanel {

	protected T entity;

	public EntityGui(T entity) {
		this.entity = entity;
		initComponents();
	}

	@Override
	public void update() {
		super.update();
		updateComponents();
	}

	protected abstract void initComponents();

	protected abstract void updateComponents();

}
