package game6.core.buildings;

public enum BuildingType {
	REACTOR(1, game6.client.buildings.BuildingReactor.class, game6.server.buildings.BuildingReactor.class);

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

	public game6.client.buildings.BaseBuilding getClientBuilding() {
		try {
			return clientClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Could not instanciant client building.");
			e.printStackTrace();
		}
		return null;
	}

	public game6.server.buildings.BaseBuilding getServerBuilding() {
		try {
			return serverClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Could not instanciant server building.");
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

}
