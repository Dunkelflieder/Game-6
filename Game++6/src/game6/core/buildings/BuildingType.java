package game6.core.buildings;

import game6.client.buildings.ClientBuilding;
import game6.core.interfaces.DefaultResourceContainer;
import game6.core.interfaces.ResourceContainer;
import game6.server.buildings.ServerBuilding;

import java.lang.reflect.InvocationTargetException;

public enum BuildingType {
	RESEARCH2(2, new DefaultResourceContainer(10, 10), game6.client.buildings.BuildingResearch2.class, game6.server.buildings.BuildingResearch2.class),
	TOWER(3, new DefaultResourceContainer(10, 10), game6.client.buildings.BuildingTower.class, game6.server.buildings.BuildingTower.class),
	FACTORY(4, new DefaultResourceContainer(10, 10), game6.client.buildings.BuildingFactory.class, game6.server.buildings.BuildingFactory.class),
	STORAGE(5, new DefaultResourceContainer(10, 10), game6.client.buildings.BuildingStorage1.class, game6.server.buildings.BuildingStorage1.class),
	ENERGY1(6, new DefaultResourceContainer(10, 10), game6.client.buildings.BuildingEnergy1.class, game6.server.buildings.BuildingEnergy1.class),
	ENERGY2(7, new DefaultResourceContainer(10, 10), game6.client.buildings.BuildingEnergy2.class, game6.server.buildings.BuildingEnergy2.class),
	LIVING1(8, new DefaultResourceContainer(10, 10), game6.client.buildings.BuildingLiving1.class, game6.server.buildings.BuildingLiving1.class),

	ROCK(100, new DefaultResourceContainer(0, 0), game6.client.buildings.BuildingRock.class, game6.server.buildings.BuildingRock.class),
	WELL(101, new DefaultResourceContainer(0, 0), game6.client.buildings.BuildingWell.class, game6.server.buildings.BuildingWell.class);

	private int typeID;
	private ResourceContainer buildingCost;
	private Class<? extends ClientBuilding> clientClass;
	private Class<? extends ServerBuilding> serverClass;

	BuildingType(int typeID, ResourceContainer buildingCost, Class<? extends ClientBuilding> clientClass, Class<? extends ServerBuilding> serverClass) {
		this.typeID = typeID;
		buildingCost.setCapacity(buildingCost.getFilled());
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
	public ClientBuilding getClientBuilding(long id) {
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
	public ServerBuilding getServerBuilding() {
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
	public static BuildingType fromServerClass(Class<? extends ServerBuilding> clazz) {
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
