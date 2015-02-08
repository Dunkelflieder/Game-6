package game6.core.networking;

import game6.core.networking.packets.Packet;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import de.felk.NodeFile.NoSuchNodeException;
import de.felk.NodeFile.NodeFile;

public class ReceiverThread extends Thread {

	private Socket socket;
	private SenderThread send;
	private ArrayList<Packet> packets = new ArrayList<Packet>();
	
	public ReceiverThread(Socket socket, SenderThread send) {
		setName("Reveiver Thread for " + socket.toString());
		this.socket = socket;
		this.send = send;
		this.setDaemon(true);
		this.start();
	}

	@Override
	public void run() {

		try {
			DataInputStream stream = new DataInputStream(socket.getInputStream());
			int packetId = 0;
			while (!isInterrupted() && packetId >= 0) {

				packetId = stream.readInt();

				Packets packetEnum = Packets.byId(packetId);
				if (packetEnum == null) {
					System.out.println("received invalid packet id: " + packetId + ", ignored.");
				} else {
					int length = stream.readInt();

					ByteBuffer buffer = ByteBuffer.allocate(length);
					for (int i = 0; i < length; i++) {
						buffer.put(stream.readByte());
					}
					buffer.flip();

					try {
						Packet packet;
						packet = packetEnum.load(new NodeFile(buffer, length));
						addPacket(packet);
						// packets.add(packet);
					} catch (NoSuchNodeException e) {
						System.err.println("Could not load packet from node data");
						e.printStackTrace();
					}

				}

			}
		} catch (SocketException e) {
			// System.err.println("SocketException in ReceiverThread");
			// e.printStackTrace();
		} catch (IOException e) {
			// System.err.println("ReceiverThread crashed (maybe due to connection abort)");
			// e.printStackTrace();
		}
		
		send.interrupt();

	}

	private void addPacket(Packet packet) {
		synchronized (packets) {
			packets.add(packet);
		}
	}

	public ArrayList<Packet> getPackets() {
		return packets;
	}

}
