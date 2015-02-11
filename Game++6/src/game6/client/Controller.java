package game6.client;

import game6.client.effects.EffectContainer;
import game6.client.effects.Lightning;
import game6.client.world.Map;
import game6.client.world.World;
import game6.core.Faction;
import game6.core.buildings.BuildingType;
import game6.core.buildings.CoreBuilding;
import game6.core.networking.*;
import game6.core.networking.packets.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import de.nerogar.render.Camera;
import de.nerogar.util.InputHandler;
import de.nerogar.util.Vector3f;

public class Controller {

	private InputHandler inputHandler;
	private Connection connection;
	private EffectContainer effects;

	private World world;
	private Camera camera;
	private Faction faction;

	public Controller(World world, Camera camera, EffectContainer effects) {
		this.world = world;
		this.camera = camera;
		this.effects = effects;
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
			connection.send(new PacketPlaceBuilding(type, faction, -1, posX, posY));
		}
	}

	public void update(float timeDelta) {

		// TODO debug/testing code
		if (isConnected()) {
			List<Packet> packets = connection.get(PacketChannel.INIT);
			for (Packet packet : packets) {
				if (packet instanceof PacketPlayerInfo) {
					PacketPlayerInfo packetInfo = (PacketPlayerInfo) packet;
					faction = packetInfo.faction;
				} else if (packet instanceof PacketMap) {
					PacketMap packetMap = (PacketMap) packet;
					world.setMap(new Map(packetMap.map));
				}
			}

			if (world.isReady()) {

			}

			// TODO Don't process the packets here
			packets = connection.get(PacketChannel.BUILDINGS);
			for (Packet packet : packets) {
				if (packet instanceof PacketPlaceBuilding) {
					PacketPlaceBuilding packetBuilding = (PacketPlaceBuilding) packet;
					CoreBuilding building = packetBuilding.building.getClientBuilding(packetBuilding.id);
					building.setFaction(packetBuilding.faction);
					((World) world).getMap().addBuilding(packetBuilding.posX, packetBuilding.posY, building);
				} else if (packet instanceof PacketPowerSupply) {
					PacketPowerSupply packetPS = (PacketPowerSupply) packet;
					CoreBuilding start = getWorld().getMap().getBuildings().get(packetPS.source);
					CoreBuilding dest = getWorld().getMap().getBuildings().get(packetPS.destination);
					Vector3f from = new Vector3f(start.getPosX() + (0.5f * start.getSizeX()), 0.5f, start.getPosY() + (0.5f * start.getSizeY()));
					Vector3f to = new Vector3f(dest.getPosX() + (0.5f * dest.getSizeX()), 0.5f, dest.getPosY() + (0.5f * dest.getSizeY()));
					effects.addEffect(new Lightning(from, to));
				} else if (packet instanceof PacketBuildingUpdate) {
					PacketBuildingUpdate pbu = (PacketBuildingUpdate) packet;
					getWorld().getMap().getBuildings().get(pbu.id).setEnergy(pbu.energy);
				}
			}
		}

	}
}
