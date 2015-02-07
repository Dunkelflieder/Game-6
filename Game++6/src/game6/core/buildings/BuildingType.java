package game6.core.buildings;

import java.lang.reflect.InvocationTargetException;

public enum BuildingType {
	REACTOR(1, game6.client.buildings.BuildingReactor.class, game6.server.buildings.BuildingReactor.class),
	RESEARCH(2, game6.client.buildings.BuildingResearch.class, game6.server.buildings.BuildingResearch.class),
	TOWER(3, game6.client.buildings.BuildingTower.class, game6.server.buildings.BuildingTower.class),
	FACTORY(4, game6.client.buildings.BuildingFactory.class, game6.server.buildings.BuildingFactory.class);

	private int id;
	private Class<? extends game6.client.buildings.BaseBuilding> clientClass;
	private Class<? extends game6.server.buildings.BaseBuilding> serverClass;

	BuildingType(int id, Class<? extends game6.client.buildings.BaseBuilding> clientClass, Class<? extends game6.server.buildings.BaseBuilding> serverClass) {
		this.id = id;
		this.clientClass = clientClass;
		this.serverClass = serverClass;
	}

	public int getID() {
		return id;
	}

	public game6.client.buildings.BaseBuilding getClientBuilding(int id) {
		try {
			return clientClass.getConstructor(int.class).newInstance(id);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.err.println("Could not instanciate client building.");
			e.printStackTrace();
		}
		return null;
	}

	public game6.server.buildings.BaseBuilding getServerBuilding(int id) {
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

	public static BuildingType fromServerClass(Class<? extends game6.server.buildings.BaseBuilding> clazz) {
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
