package game6.server.world;

import de.nerogar.engine.UpdateEventInterface;
import de.nerogar.network.Connection;
import game6.core.faction.Faction;

public class Player implements UpdateEventInterface {

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
