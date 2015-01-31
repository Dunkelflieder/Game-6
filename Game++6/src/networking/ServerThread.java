package networking;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import networking.packets.Packet;

public class ServerThread extends Thread {

	private ServerSocket socket;
	private boolean verbose = false;

	private List<Connection> connections = new ArrayList<>();

	public ServerThread(int port) throws BindException {
		try {
			socket = new ServerSocket(port);
		} catch (BindException e) {
			throw e;
		} catch (IOException e) {
			System.err.println("The server crashed brutally");
			e.printStackTrace();
		}
		this.start();
	}

	@Override
	public void run() {

		try {
			while (!isInterrupted()) {
				addConnection(socket.accept());
			}
		} catch (SocketException e) {
			// System.err.println("SocketException in Server");
			// e.printStackTrace();
		} catch (IOException e) {
			System.err.println("The server crashed brutally");
			e.printStackTrace();
		}

		stopThread();
		System.out.println("SHUTDOWN: Server - " + socket);

	}

	private void addConnection(Socket newSocket) {
		synchronized (connections) {
			connections.add(new Connection(newSocket));
		}
		if (verbose) {
			System.out.println("Added Client, socket: " + newSocket);
		}
		// TODO init connection and stuff?
	}

	public void broadcast(Packet packet) {
		for (Connection connection : connections) {
			connection.send(packet);
			if (verbose) {
				System.out.println("Sent " + packet + " to " + connection);
			}
		}
	}

	public void stopThread() {
		for (Connection connection : connections) {
			connection.close();
		}
		interrupt();
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				System.err.println("Could not close Server-Socket");
				e.printStackTrace();
			}
		}
	}

	public List<Connection> getConnections() {
		return connections;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

}
