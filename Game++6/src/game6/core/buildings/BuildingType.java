package game6.core.buildings;

import game6.core.util.ResourceContainer;

import java.lang.reflect.InvocationTargetException;

public enum BuildingType {
	REACTOR(1, new ResourceContainer(10), game6.client.buildings.BuildingReactor.class, game6.server.buildings.BuildingReactor.class),
	RESEARCH(2, new ResourceContainer(10), game6.client.buildings.BuildingResearch.class, game6.server.buildings.BuildingResearch.class),
	TOWER(3, new ResourceContainer(10), game6.client.buildings.BuildingTower.class, game6.server.buildings.BuildingTower.class),
	FACTORY(4, new ResourceContainer(10), game6.client.buildings.BuildingFactory.class, game6.server.buildings.BuildingFactory.class),
	STORAGE(5, new ResourceContainer(10), game6.client.buildings.BuildingStorage1.class, game6.server.buildings.BuildingStorage1.class),
	ENERGY1(6, new ResourceContainer(10), game6.client.buildings.BuildingEnergy1.class, game6.server.buildings.BuildingEnergy1.class),
	LIVING1(7, new ResourceContainer(10), game6.client.buildings.BuildingLiving1.class, game6.server.buildings.BuildingLiving1.class),

	ROCK(100, null, game6.client.buildings.BuildingRock.class, game6.server.buildings.BuildingRock.class);

	private int typeID;
	private ResourceContainer buildingCost;
	private Class<? extends CoreBuilding> clientClass;
	private Class<? extends CoreBuilding> serverClass;

	BuildingType(int typeID, ResourceContainer buildingCost, Class<? extends CoreBuilding> clientClass, Class<? extends CoreBuilding> serverClass) {
		this.typeID = typeID;
		this.buildingCost = buildingCost;
		this.clientClass = clientClass;
		this.serverClass = serverClass;
	}

	/**
	 * Returns the Building-Type-ID of the current enum.
	 * It is unique per building type.
	 * @return Building-Type-ID
	 */
	public int getTypeID() {
		return typeID;
	}

	/**
	 * @return the building type's cost for construction.
	 */
	public ResourceContainer getBuildingCost() {
		return buildingCost;
	}

	/**
	 * Returns an client-class-instance of the building type 
	 * @param id The unique ID of the instance.
	 * @return Building instance for client
	 */
	public CoreBuilding getClientBuilding(long id) {
		try {
			return clientClass.getConstructor(long.class).newInstance(id);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.err.println("Could not instanciate client building.");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns an server-class-instance of the building type 
	 * @return Building instance for server
	 */
	public CoreBuilding getServerBuilding() {
		try {
			return serverClass.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.err.println("Could not instanciate server building.");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns the BuildingType-Enum with the corresponding Type-ID
	 * @param typeID the Building-Type-ID
	 * @return corresponding enum
	 */
	public static BuildingType byTypeID(int typeID) {
		for (BuildingType type : values()) {
			if (type.getTypeID() == typeID) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Returns the BuildingType enum by a server-class
	 * @param clazz Class of the instance
	 * @return corresponding enum
	 */
	public static BuildingType fromServerClass(Class<? extends CoreBuilding> clazz) {
		for (BuildingType type : values()) {
			if (type.serverClass.equals(clazz)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Returns a random BuildingType enum. For debug and stuff
	 * @return random enum
	 */
	public static BuildingType getRandom() {
		return values()[(int) (Math.random() * values().length)];
	}
}
