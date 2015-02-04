package game6.server.world;

import game6.core.networking.Connection;

public class Player {

	private Connection connection;
	
	public Player(Connection connection) {
		this.connection = connection;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
}
