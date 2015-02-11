package game6.server.world;

import game6.core.Faction;
import game6.core.networking.Connection;

public class Player {

	private Connection connection;
	private Faction faction;

	public Player(Connection connection, Faction faction) {
		this.connection = connection;
		this.faction = faction;
	}

	public Connection getConnection() {
		return connection;
	}

	public Faction getFaction() {
		return faction;
	}

}
