package game6.client;

import game6.client.effects.*;
import game6.client.sound.SoundContext;
import game6.client.world.World;
import game6.core.buildings.*;
import game6.core.entities.CoreEntity;
import game6.core.entities.EntityType;
import game6.core.faction.Faction;
import game6.core.networking.PacketList;
import game6.core.networking.packets.*;

import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.Map.Entry;

import de.nerogar.network.Connection;
import de.nerogar.network.Packets;
import de.nerogar.network.packets.Packet;
import de.nerogar.network.packets.PacketConnectionInfo;
import de.nerogar.render.Camera;
import de.nerogar.util.InputHandler;
import de.nerogar.util.Vector3f;

public class Controller {

	private InputHandler inputHandler;
	private Connection connection;
	private EffectContainer effects;

	// TODO public for now. change later
	public SoundContext soundMain;
	public SoundContext soundMusic;
	public SoundContext soundEffects;

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

	public Faction getFaction() {
		return faction;
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
			connection.send(new PacketPlaceBuilding(type, faction, -1, posX, posY));
		}
	}

	// TODO debug method
	public void requestEntity(EntityType type, Vector3f position) {
		if (isConnected()) {
			connection.send(new PacketSpawnEntity(type, faction, -1, position));
		}
	}

	// TODO debug method
	public void moveEntity(CoreEntity entity, Vector3f position) {
		if (isConnected()) {
			connection.send(new PacketEntityGoalChanged(entity, position));
		}
	}

	// TODO debug method
	public void setEntityTarget(CoreEntity sourceEntity, CoreEntity targetEntity) {
		if (isConnected()) {
			connection.send(new PacketCombatTargetSet(PacketCombatTargetSet.ENTITIY, sourceEntity.getID(), PacketCombatTargetSet.ENTITIY, targetEntity.getID()));
		}
	}

	// TODO debug method
	public void setEntityTarget(CoreEntity sourceEntity, CoreBuilding targetBuilding) {
		if (isConnected()) {
			connection.send(new PacketCombatTargetSet(PacketCombatTargetSet.ENTITIY, sourceEntity.getID(), PacketCombatTargetSet.BUILDING, targetBuilding.getID()));
		}
	}

	public void update(float timeDelta) {

		// TODO debug/testing code
		if (isConnected()) {
			List<Packet> packets = connection.get(PacketList.INIT);
			for (Packet packet : packets) {
				if (packet instanceof PacketPlayerInfo) {
					PacketPlayerInfo packetInfo = (PacketPlayerInfo) packet;
					faction = packetInfo.faction;
				} else if (packet instanceof PacketMap) {
					PacketMap packetMap = (PacketMap) packet;
					world.setMap(packetMap.map);
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
			packets = connection.get(PacketList.BUILDINGS);
			for (Packet packet : packets) {
				if (packet instanceof PacketPlaceBuilding) {

					PacketPlaceBuilding packetBuilding = (PacketPlaceBuilding) packet;
					CoreBuilding building = packetBuilding.building.getClientBuilding(packetBuilding.id);
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

				} else if (packet instanceof PacketBuildingUpdate) {
					PacketBuildingUpdate pbu = (PacketBuildingUpdate) packet;
					getWorld().getBuilding(pbu.id).setEnergy(pbu.energy);

				} else if (packet instanceof PacketEnabledBuildingsList) {
					PacketEnabledBuildingsList pebl = (PacketEnabledBuildingsList) packet;
					for (BuildingType building : pebl.buildings) {
						System.out.println(building);
					}
				} else if (packet instanceof PacketUpdateStorage) {
					PacketUpdateStorage pus = (PacketUpdateStorage) packet;
					CoreBuildingStorage building = (CoreBuildingStorage) getWorld().getBuilding(pus.buildingID);
					building.getResources().setResources(pus.resources);
					building.getResources().setCapacity(pus.resources.getTotalCapacity());
				}
			}

			for (Entry<LightningLine, Integer> entry : lightnings.entrySet()) {
				CoreBuilding building1 = getWorld().getBuilding(entry.getKey().a);
				CoreBuilding building2 = getWorld().getBuilding(entry.getKey().b);
				effects.addEffect(new Lightning(building1.getCenter(), building2.getCenter()));
			}

			packets = connection.get(PacketList.ENTITIES);
			for (Packet packet : packets) {
				if (packet instanceof PacketSpawnEntity) {

					PacketSpawnEntity packetEntity = (PacketSpawnEntity) packet;
					CoreEntity entity = packetEntity.entity.getClientEntity(packetEntity.id);
					entity.setFaction(packetEntity.faction);
					world.spawnEntity(entity, packetEntity.position);

				} else if (packet instanceof PacketEntityMoved) {

					PacketEntityMoved packetEntity = (PacketEntityMoved) packet;
					CoreEntity entity = (CoreEntity) world.getEntityList().getEntity(packetEntity.id);
					entity.teleport(packetEntity.position);
					entity.setRotation(packetEntity.rotation);

				} else if (packet instanceof PacketEntityGoalChanged) {

					PacketEntityGoalChanged packetEntity = (PacketEntityGoalChanged) packet;
					((CoreEntity) world.getEntityList().getEntity(packetEntity.id)).setGoal(packetEntity.goal);

				} else if (packet instanceof PacketAttackEffect) {

					PacketAttackEffect packetEntity = (PacketAttackEffect) packet;

					effects.addEffect(new LaserBullet(packetEntity.sourcePos, packetEntity.targetPos));
				} else if (packet instanceof PacketRemoveEntity) {

					PacketRemoveEntity pre = (PacketRemoveEntity) packet;
					CoreEntity entity = (CoreEntity) getWorld().getEntityList().getEntity(pre.id);
					if (pre.killed) {
						effects.addEffect(new Explosion(entity.getPosition().clone()));

						//entity.playDeathAnimation();
					}
					if (getWorld().getSelectedEntity() != null && pre.id == getWorld().getSelectedEntity().getID()) {
						getWorld().selectEntity(null);
					}
					entity.removeFromWorld();

				}
			}
		}

	}
}
