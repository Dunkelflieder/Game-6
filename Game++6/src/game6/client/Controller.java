package game6.client;

import game6.client.gui.GuiIngame;
import game6.client.gui.GuiTitlescreen;
import game6.client.gui.Guis;
import game6.client.world.Map;
import game6.client.world.World;
import game6.core.buildings.BuildingType;
import game6.core.networking.Connection;
import game6.core.networking.PacketChannel;
import game6.core.networking.Packets;
import game6.core.networking.packets.Packet;
import game6.core.networking.packets.PacketConnectionInfo;
import game6.core.networking.packets.PacketMap;
import game6.core.networking.packets.PacketPlaceBuilding;
import game6.core.networking.packets.PacketPowerSupply;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import de.nerogar.engine.BaseController;
import de.nerogar.engine.BaseWorld;
import de.nerogar.render.Camera;
import de.nerogar.util.InputHandler;
import de.nerogar.util.MathHelper;
import de.nerogar.util.RayIntersection;

public class Controller extends BaseController {

	// 0 = not grabbed, 1 = grabbed for movement, 2 = grabbed for rotation
	private byte grabbed = 0;

	private InputHandler inputHandler;
	private Connection connection;

	public Controller(BaseWorld world, Camera camera) {
		super(world, camera);
		inputHandler = new InputHandler();
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

	@Override
	protected void init(BaseWorld world) {
		camera.y = 15;
		camera.x = 15;
		camera.z = 15;
		camera.pitch = 60;
		camera.yaw = 0;

		GuiTitlescreen.instance.addConnectListener((host, port) -> {
			if (connect(host, port)) {
				Guis.select(Guis.INGAME);
			}
		});

		// TODO temporary debug stuff
		GuiIngame.instance.addBuildingClickedListener(() -> {
			if (isConnected()) {
				World worldWorld = (World) world;
				connection.send(new PacketPlaceBuilding(BuildingType.getRandom(), -1, (int) (Math.random() * (worldWorld.getMap().getSizeX() - 2)), (int) (Math.random() * (worldWorld.getMap().getSizeY() - 2))));
			}
		});
	}

	@Override
	public void update(float timeDelta) {

		if (isConnected()) {
			List<Packet> packets = connection.get(PacketChannel.MAP);
			for (Packet packet : packets) {
				PacketMap packetMap = (PacketMap) packet;
				// FIXME Can only World's methods with casting
				((World) world).setMap(new Map(packetMap.tiles));
			}

			if (((World) world).isReady()) {

			}
			packets = connection.get(PacketChannel.BUILDINGS);
			for (Packet packet : packets) {
				if (packet instanceof PacketPlaceBuilding) {
					PacketPlaceBuilding packetBuilding = (PacketPlaceBuilding) packet;
					((World) world).getMap().addBuilding(packetBuilding.posX, packetBuilding.posY, packetBuilding.building.getClientBuilding(packetBuilding.id));
				} else if (packet instanceof PacketPowerSupply) {
					PacketPowerSupply packetPS = (PacketPowerSupply) packet;
				}
			}
		}

		// TODO don't hardcode fov

		inputHandler.updateMousePositions(camera, 90);
		RayIntersection intersection = world.getPhysicsSpace().getIntersecting(inputHandler.getMouseRay());

		/*if (intersection != null && intersection.intersectionPoint.getX() < terrain.getSizeX() && intersection.intersectionPoint.getZ() < terrain.getSizeY()) {
			int clickX = (int) intersection.intersectionPoint.getX();
			int clickY = (int) intersection.intersectionPoint.getZ();

			//Tile tile = terrain.getTile((int) intersection.intersectionPoint.getX(), (int) intersection.intersectionPoint.getZ());

			BaseBuilding reactor = new BuildingReactor();

			
			if (Mouse.isButtonDown(0) && world.canAddBuilding(clickX, clickY, reactor)) world.addBuilding(clickX, clickY, reactor);
		}*/

		if (Mouse.isButtonDown(2)) {

			if ((Mouse.getY() < Display.getHeight() * 0.3 && grabbed != 1) || grabbed == 2) {

				grabbed = 2;

				// Grabbed lower 30% of screen
				camera.yaw += Mouse.getDX() * 0.5f;

			} else if (grabbed != 2) {

				// Grabbed higher 70% of screen

				grabbed = 1;

				float slow = 0.01f * camera.y;
				float angle = (float) (camera.yaw / (180f / Math.PI));

				float dx = Mouse.getDX();
				float dy = Mouse.getDY();

				camera.x -= slow * dx * Math.cos(angle) + slow * dy * Math.sin(angle);
				camera.z -= slow * dx * Math.sin(angle) - slow * dy * Math.cos(angle);
			}

		} else {

			grabbed = 0;

		}

		camera.y = MathHelper.clamp(camera.y - 0.01f * (Mouse.getDWheel()), 2f, 15f);
	}
}
