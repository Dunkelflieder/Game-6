package game6.server.entities.jobs;

import game6.core.entities.MoveTargetBuilding;
import game6.server.buildings.*;
import game6.server.entities.ServerEntityInventory;

import java.util.ArrayList;
import java.util.List;

public class JobDeliver extends Job {

	private boolean isDelivering;
	private ServerEntityInventory entity;
	private ServerBuildingInventory workplace;
	private ArrayList<Constructionsite> targets;

	public JobDeliver(ServerEntityInventory entity, ServerBuildingInventory workplace) {
		this.entity = entity;
		this.workplace = workplace;
		isDelivering = false;
		targets = new ArrayList<>();
	}

	@Override
	public void update(float timeDelta) {
		if (!entity.isMoving()) {
			if (isDelivering) {

				if (targets.isEmpty()) {
					moveBack();
				} else {

					Constructionsite reachedTarget = targets.remove(0);

					
					
					entity.removeResources(reachedTarget.removeResources(entity));

					if (entity.isEmpty() || targets.isEmpty()) {
						isDelivering = false;
						moveBack();
					} else {
						moveToNext();
					}
				}

			} else {
				/*if (!entity.isEmpty()) {
					// put stuff back
					entity.setResources(workplace.addResources(entity));
				}*/
				// get stuff
				
				workplace.setResources(entity.addResources(workplace));
				// TODO only get stuff that will actually be needed

				renewList();
				if (!targets.isEmpty()) {
					isDelivering = true;
					moveToNext();
				}
			}
		}
	}

	private void moveToNext() {
		entity.move(new MoveTargetBuilding(entity, targets.get(0)));
	}

	private void moveBack() {
		entity.move(new MoveTargetBuilding(entity, workplace));
	}

	private void renewList() {
		targets.clear();
		List<ServerBuilding> buildings = entity.getWorld().getMap().getBuildingsWithin(workplace.getPosX(), workplace.getPosY(), workplace.getRange());
		for (ServerBuilding building : buildings) {
			if (building instanceof Constructionsite) {
				targets.add((Constructionsite) building);
			}
		}
	}

}
