package game6.core.networking;

import game6.core.networking.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import de.felk.NodeFile.NodeFile;

public class SenderThread extends Thread {

	private Socket socket;
	private ArrayList<Packet> packets = new ArrayList<Packet>();

	public SenderThread(Socket socket) {
		setName("Sender Thread for " + socket.toString());
		this.socket = socket;
		this.setDaemon(true);
		this.start();
	}

	@Override
	public void run() {

		try (DataOutputStream stream = new DataOutputStream(socket.getOutputStream())) {
			while (!isInterrupted()) {
				synchronized (packets) {

					if (packets.isEmpty()) {
						stream.flush();
						packets.wait();
					}
					for (Packet packet : packets) {
						try {
							stream.writeInt(Packets.byClass(packet.getClass()).getID());
							NodeFile node = packet.toNode();
							stream.writeInt(node.length());
							stream.write(node.toBuffer().array());
						} catch (IOException e) {
							System.err.println("Could not send packet " + packet.toString());
							// e.printStackTrace();
						}

					}
					packets.clear();

				}
			}
		} catch (InterruptedException e) {
			interrupt();
			// System.err.println("InterruptedException in SenderThread");
			// e.printStackTrace();
		} catch (IOException e) {
			// System.err.println("DataOutputStream is unavailable in SenderThread.");
			// e.printStackTrace();
		}

	}

	public void send(Packet packet) {
		synchronized (packets) {
			packets.add(packet);
			packets.notify();
		}
	}

}
