package game6.core.entities;

import game6.client.entities.ClientEntity;
import game6.server.entities.ServerEntity;

import java.lang.reflect.InvocationTargetException;

public enum EntityType {
	HELICOPTER1(1, game6.client.entities.EntityHelicopter1.class, game6.server.entities.EntityHelicopter1.class),
	TANK1(2, game6.client.entities.EntityTank1.class, game6.server.entities.EntityTank1.class),
	SCOUT1(3, game6.client.entities.EntityScout1.class, game6.server.entities.EntityScout1.class),
	TRANSPORT1(4, game6.client.entities.EntityTransport1.class, game6.server.entities.EntityTransport1.class),
	TRANSPORT2(5, game6.client.entities.EntityTransport2.class, game6.server.entities.EntityTransport2.class);

	private int typeID;
	private Class<? extends ClientEntity> clientClass;
	private Class<? extends ServerEntity> serverClass;

	EntityType(int typeID, Class<? extends ClientEntity> clientClass, Class<? extends ServerEntity> serverClass) {
		this.typeID = typeID;
		this.clientClass = clientClass;
		this.serverClass = serverClass;
	}

	/**
	 * Returns the Entity-Type-ID of the current enum.
	 * It is unique per entity type.
	 * @return Entity-Type-ID
	 */
	public int getTypeID() {
		return typeID;
	}

	/**
	 * Returns an client-class-instance of the entity type 
	 * @param id The unique ID of the instance.
	 * @return Entity instance for client
	 */
	public ClientEntity getClientEntity(long id) {
		try {
			return clientClass.getConstructor(long.class).newInstance(id);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.err.println("Could not instanciate client entity.");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns an server-class-instance of the entity type 
	 * @return Entity instance for server
	 */
	public ServerEntity getServerEntity() {
		try {
			return serverClass.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.err.println("Could not instanciate server entity.");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns the EntityType-Enum with the corresponding Type-ID
	 * @param typeID the Entity-Type-ID
	 * @return corresponding enum
	 */
	public static EntityType byTypeID(int typeID) {
		for (EntityType type : values()) {
			if (type.getTypeID() == typeID) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Returns the EntityType enum by a server-class
	 * @param clazz Class of the instance
	 * @return corresponding enum
	 */
	public static EntityType fromServerClass(Class<? extends ServerEntity> clazz) {
		for (EntityType type : values()) {
			if (type.serverClass.equals(clazz)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Returns a random EntityType enum. For debug and stuff
	 * @return random enum
	 */
	public static EntityType getRandom() {
		return values()[(int) (Math.random() * values().length)];
	}
}
