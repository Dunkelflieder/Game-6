package game6.core.networking;

import game6.core.networking.packets.Packet;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Connection {

	private Socket socket;
	private ReceiverThread recv;
	private SenderThread send;

	public Connection(Socket socket) {
		if (socket == null) {
			return;
		}
		this.socket = socket;
		this.recv = new ReceiverThread(socket);
		this.send = new SenderThread(socket);
	}

	public void send(Packet packet) {
		send.send(packet);
	}

	public ArrayList<Packet> get(PacketChannel channel) {
		ArrayList<Packet> packets = new ArrayList<Packet>();
		ArrayList<Packet> availablePackets = recv.getPackets();
		synchronized (availablePackets) {
			for (Iterator<Packet> iter = availablePackets.iterator(); iter.hasNext();) {
				Packet p = iter.next();
				if (Packets.byClass(p.getClass()).getChannel() == channel) {
					packets.add(p);
					iter.remove();
				}
			}
		}
		return packets;
	}

	public synchronized void close() {
		recv.stopThread();
		send.stopThread();
		try {
			socket.close();
		} catch (IOException e) {
			System.err.println("Could not close socket in Client.");
			e.printStackTrace();
		}
		System.out.println("SHUTDOWN: Connection - " + socket.toString());
	}

	public boolean isClosed() {
		if (socket == null) {
			return true;
		}
		return socket.isClosed();
	}

}
