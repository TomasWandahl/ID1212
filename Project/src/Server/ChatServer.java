package eu.vandahl.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
	ServerSocket serverSocket;
	final int PORTNUMBER = 420;
	private ArrayList<ClientThread> clients;

	public void logoutClient(ClientThread client, String username) {
		// clients.remove(client);
		// client.close();
		// new ChatServer().broadcastMessage(username + " has logged out!");
	}

	public void startServer() throws IOException {
		clients = new ArrayList<ClientThread>();
		serverSocket = new ServerSocket(PORTNUMBER);

		System.out.println("Server running.....");

		while (true) {
			Socket socket = serverSocket.accept();
			ClientThread t = new ClientThread(socket, this);
			System.out.println("A Client has connected!");
			clients.add(t);
			t.start();
		}
	}

	public void broadcastUserMessage(String username, String msg) {
		for (int i = 0; i < clients.size(); i++) {
			ClientThread ct = clients.get(i);
			if (!ct.sendMessage(username + ": " + msg)) {
				clients.remove(i);
			}
		}
	}

	public void broadcastMessage(String msg) {
		for (int i = 0; i < clients.size(); i++) {
			ClientThread ct = clients.get(i);
			if (!ct.sendMessage(msg)) {
				clients.remove(i);
			}
		}
	}

	public static void main(String[] args) {
		try {
			new ChatServer().startServer();
		} catch (IOException e) {
			System.out.println("Couldnt start Server!");
		}
	}

}
