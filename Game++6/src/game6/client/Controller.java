package game6.client;

import game6.client.buildings.ClientBuilding;
import game6.client.buildings.Constructionsite;
import game6.client.effects.Lightning;
import game6.client.entities.ClientEntity;
import game6.client.gui.GuiIngame;
import game6.client.sound.SoundContext;
import game6.client.world.World;
import game6.core.buildings.BuildingType;
import game6.core.entities.EntityType;
import game6.core.faction.Faction;
import game6.core.networking.PacketList;
import game6.core.networking.packets.*;
import game6.core.networking.packets.entities.PacketEntityMove;

import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.Map.Entry;

import de.nerogar.network.Connection;
import de.nerogar.network.packets.Packet;
import de.nerogar.render.Camera;
import de.nerogar.util.InputHandler;
import de.nerogar.util.Vector3f;

public class Controller {

	private InputHandler inputHandler;
	private Connection connection;

	// TODO public for now. change later
	public SoundContext soundMain;
	public SoundContext soundMusic;
	public SoundContext soundEffects;

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

	public void setWorld(World world) {
		this.world = world;
	}

	public Camera getCamera() {
		return camera;
	}

	public InputHandler getInputHandler() {
		return inputHandler;
	}

	public void cleanup() {
		if (world != null) {
			world.cleanup();
		}
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
		cleanup();
	}

	protected void init() {
		camera.setY(15f);
		camera.setX(15f);
		camera.setZ(15f);
		// camera.setPitch(60f / (float) (180f * Math.PI));
		camera.setPitch(1);
		camera.setYaw(0f);

		soundMain = new SoundContext();
		soundMusic = soundMain.createSubContext();
		soundEffects = soundMain.createSubContext();
	}

	// TODO debug method
	public void placeBuilding(BuildingType type, int posX, int posY) {
		if (isConnected()) {
			// Instead, use PacketStartConstruction!
			// connection.send(new PacketPlaceBuilding(type, faction, -1, posX, posY));
			connection.send(new PacketStartConstruction(type, Faction.own, posX, posY, -1));
		}
	}

	// TODO debug method
	public void requestEntity(EntityType type, Vector3f position) {
		if (isConnected()) {
			connection.send(new PacketSpawnEntity(type, Faction.own, -1, position));
		}
	}

	// TODO debug method
	public void moveEntity(ClientEntity entity, Vector3f position) {
		if (isConnected()) {
			connection.send(new PacketEntityMove(entity, position));
		}
	}

	// TODO debug method
	public void setEntityTarget(ClientEntity sourceEntity, ClientEntity targetEntity) {
		if (isConnected()) {
			connection.send(new PacketCombatTargetSet(PacketCombatTargetSet.ENTITIY, sourceEntity.getID(), PacketCombatTargetSet.ENTITIY, targetEntity.getID()));
		}
	}

	// TODO debug method
	public void setEntityTarget(ClientEntity sourceEntity, ClientBuilding targetBuilding) {
		if (isConnected()) {
			connection.send(new PacketCombatTargetSet(PacketCombatTargetSet.ENTITIY, sourceEntity.getID(), PacketCombatTargetSet.BUILDING, targetBuilding.getID()));
		}
	}

	public void update(float timeDelta) {

		if (world != null) {
			world.update(timeDelta);
		}

		// TODO debug/testing code
		if (isConnected()) {
			List<Packet> packets = connection.get(PacketList.INIT);
			for (Packet packet : packets) {
				if (packet instanceof PacketPlayerInfo) {
					PacketPlayerInfo packetInfo = (PacketPlayerInfo) packet;
					Faction.own = packetInfo.faction;
				} else if (packet instanceof PacketMap) {
					PacketMap packetMap = (PacketMap) packet;
					getWorld().setMap(packetMap.getClientMap());
				}
			}

			class LightningLine {
				long a, b;

				public LightningLine(long a, long b, int amount) {
					this.a = a;
					this.b = b;
				}

				@Override
				public boolean equals(Object obj) {
					if (!(obj instanceof LightningLine)) { return false; }
					LightningLine l = (LightningLine) obj;
					return l.a == a && l.b == b || l.a == b && l.b == a;
				}

				@Override
				public int hashCode() {
					return (int) (a + b);
				}
			}
			HashMap<LightningLine, Integer> lightnings = new HashMap<>();

			// TODO Don't process the packets here
			packets = connection.get(PacketList.INFO);
			for (Packet packet : packets) {
				if (packet instanceof PacketEnabledBuildingsList) {
					PacketEnabledBuildingsList pebl = (PacketEnabledBuildingsList) packet;
					Faction.own.setBuildableBuildings(pebl.buildings);
					GuiIngame.instance.reloadBuildingList();
				}
			}

			packets = connection.get(PacketList.WORLD);
			for (Packet packet : packets) {
				if (packet instanceof PacketSpawnEntity) {

					PacketSpawnEntity packetEntity = (PacketSpawnEntity) packet;
					ClientEntity entity = packetEntity.entity.getClientEntity(packetEntity.id);
					entity.setFaction(packetEntity.faction);
					entity.teleport(packetEntity.position);
					world.addEntity(entity);

				} else if (packet instanceof PacketStartConstruction) {

					PacketStartConstruction packetBuilding = (PacketStartConstruction) packet;
					ClientBuilding building = packetBuilding.building.getClientBuilding(packetBuilding.id);
					building.setFaction(packetBuilding.faction);
					world.addBuilding(packetBuilding.posX, packetBuilding.posY, new Constructionsite(building, packetBuilding.building.getBuildingCost()));

				} else if (packet instanceof PacketSpawnBuilding) {

					PacketSpawnBuilding packetBuilding = (PacketSpawnBuilding) packet;
					ClientBuilding building = packetBuilding.building.getClientBuilding(packetBuilding.id);
					building.setFaction(packetBuilding.faction);
					world.addBuilding(packetBuilding.posX, packetBuilding.posY, building);

				} else if (packet instanceof PacketPowerSupply) {

					PacketPowerSupply packetPS = (PacketPowerSupply) packet;

					long[] waypoints = packetPS.waypoints;
					if (waypoints.length == 0) {
						System.err.println("Got PacketPowerSupply with empty waypoints array!");
					} else {
						for (int i = 1; i < waypoints.length; i++) {
							LightningLine line = new LightningLine(waypoints[i - 1], waypoints[i], packetPS.amount);
							if (lightnings.containsKey(line)) {
								lightnings.put(line, lightnings.get(line) + packetPS.amount);
							} else {
								lightnings.put(line, packetPS.amount);
							}
						}
					}
				}
				// TODO reimplement combat
				/*else if (packet instanceof PacketAttackEffect) {

					PacketAttackEffect packetEntity = (PacketAttackEffect) packet;

					world.getEffectContainer().addEffect(new LaserBullet(packetEntity.sourcePos, packetEntity.targetPos));
				}*/
			}

			for (Entry<LightningLine, Integer> entry : lightnings.entrySet()) {
				ClientBuilding building1 = getWorld().getBuilding(entry.getKey().a);
				ClientBuilding building2 = getWorld().getBuilding(entry.getKey().b);
				world.getEffectContainer().addEffect(new Lightning(building1.getCenter(), building2.getCenter()));
			}

			packets = connection.get(PacketList.ENTITIES);
			for (Packet packet : packets) {
				PacketUniqueID packetUniqueID = (PacketUniqueID) packet;
				ClientEntity entity = getWorld().getEntity(packetUniqueID.id);
				if (entity == null) {
					System.err.println("Received packet <" + packet + "> for non-existent entity #" + packetUniqueID.id);
				} else {
					entity.process(packetUniqueID);
				}
			}

			packets = connection.get(PacketList.BUILDINGS);
			for (Packet packet : packets) {
				PacketUniqueID packetUniqueID = (PacketUniqueID) packet;
				ClientBuilding building = getWorld().getBuilding(packetUniqueID.id);
				if (building == null) {
					System.err.println("Received packet <" + packet + "> for non-existent building #" + packetUniqueID.id);
				} else {
					building.process(packetUniqueID);
				}
			}

			connection.flush();
		}

	}
}
