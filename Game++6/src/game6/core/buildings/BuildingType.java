package game6.core.buildings;

import java.lang.reflect.InvocationTargetException;

public enum BuildingType {
	REACTOR(1, game6.client.buildings.BuildingReactor.class, game6.server.buildings.BuildingReactor.class),
	RESEARCH(2, game6.client.buildings.BuildingResearch.class, game6.server.buildings.BuildingResearch.class),
	TOWER(3, game6.client.buildings.BuildingTower.class, game6.server.buildings.BuildingTower.class),
	FACTORY(4, game6.client.buildings.BuildingFactory.class, game6.server.buildings.BuildingFactory.class);

	private int id;
	private Class<? extends CoreBuilding> clientClass;
	private Class<? extends CoreBuilding> serverClass;

	BuildingType(int id, Class<? extends CoreBuilding> clientClass, Class<? extends CoreBuilding> serverClass) {
		this.id = id;
		this.clientClass = clientClass;
		this.serverClass = serverClass;
	}

	public int getID() {
		return id;
	}

	public CoreBuilding getClientBuilding(int id) {
		try {
			return clientClass.getConstructor(int.class).newInstance(id);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.err.println("Could not instanciate client building.");
			e.printStackTrace();
		}
		return null;
	}

	public CoreBuilding getServerBuilding(int id) {
		try {
			return serverClass.getConstructor(int.class).newInstance(id);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.err.println("Could not instanciate server building.");
			e.printStackTrace();
		}
		return null;
	}

	public static BuildingType byID(int id) {
		for (BuildingType type : values()) {
			if (type.getID() == id) {
				return type;
			}
		}
		return null;
	}

	public static BuildingType fromServerClass(Class<? extends CoreBuilding> clazz) {
		for (BuildingType type : values()) {
			if (type.serverClass.equals(clazz)) {
				return type;
			}
		}
		return null;
	}

	public static BuildingType getRandom() {
		return values()[(int) (Math.random() * values().length)];
	}
}
