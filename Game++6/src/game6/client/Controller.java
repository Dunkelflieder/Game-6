package game6.client;

import game6.client.world.Map;
import game6.client.world.World;
import game6.core.buildings.BuildingType;
import game6.core.networking.*;
import game6.core.networking.packets.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import de.nerogar.render.Camera;
import de.nerogar.util.InputHandler;

public class Controller {

	private InputHandler inputHandler;
	private Connection connection;
	
	private World world;
	private Camera camera;

	public Controller(World world, Camera camera) {
		this.world = world;
		this.camera = camera;
		this.inputHandler = new InputHandler();
		init();
	}

	public World getWorld() {
		return world;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public InputHandler getInputHandler() {
		return inputHandler;
	}
	
	public void cleanup() {
		if (isConnected()) {
			connection.close();
		}
	}

	public boolean connect(String host, int port) {
		if (isConnected()) {
			connection.close();
		}
		try {
			connection = new Connection(new Socket(host, port));
			connection.send(new PacketConnectionInfo(Packets.NETWORKING_VERSION));
			return true;
		} catch (IOException e) {
			System.err.println("Could not establish connection");
			e.printStackTrace();
		}
		return false;
	}

	public boolean isConnected() {
		return connection != null && !connection.isClosed();
	}
	
	public void disconnect() {
		world.unloadMap();
		if (isConnected()) {
			connection.close();
		}
	}

	protected void init() {
		camera.y = 15;
		camera.x = 15;
		camera.z = 15;
		camera.pitch = 60;
		camera.yaw = 0;
	}
	
	// TODO debug method
	public void placeBuilding(BuildingType type, int posX, int posY) {
		if (isConnected()) {
			connection.send(new PacketPlaceBuilding(type, -1, posX, posY));
		}
	}

	public void update(float timeDelta) {

		// TODO debug/testing code
		if (isConnected()) {
			List<Packet> packets = connection.get(PacketChannel.MAP);
			for (Packet packet : packets) {
				PacketMap packetMap = (PacketMap) packet;
				world.setMap(new Map(packetMap.map));
			}

			if (world.isReady()) {

			}
			packets = connection.get(PacketChannel.BUILDINGS);
			for (Packet packet : packets) {
				if (packet instanceof PacketPlaceBuilding) {
					PacketPlaceBuilding packetBuilding = (PacketPlaceBuilding) packet;
					((World) world).getMap().addBuilding(packetBuilding.posX, packetBuilding.posY, packetBuilding.building.getClientBuilding(packetBuilding.id));
				} else if (packet instanceof PacketPowerSupply) {
					//PacketPowerSupply packetPS = (PacketPowerSupply) packet;
				}
			}
		}

	}
}
