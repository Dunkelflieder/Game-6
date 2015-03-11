package game6.core.faction;

import de.nerogar.network.Connection;

public class Player {

	private Connection connection;

	public Player(Connection connection) {
		this.connection = connection;
	}
	
	public Connection getConnection() {
		return connection;
	}

}
