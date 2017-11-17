package eu.tomaswandahl.Server;

import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.io.*;

public class ServerNet {
	private ServerSocket serverSocket;
	private ServerController serverController;
	private InputStream clientIn;
	private PrintWriter out;
	private BufferedReader in;

	private final List<ServerConnection> clients = new ArrayList<>();
	private int portNumber = 420;

	public void stop() throws IOException {
		in.close();
		out.close();
		serverSocket.close();
	}

	public void initiateConnections() throws IOException {
		// initiate server
		serverSocket = new ServerSocket(portNumber);

		System.out.println("Server running.....");

		while (true) {
			// check if a client wants to connect
			Socket clientSocket = serverSocket.accept();

			// Check whether a client has connected
			if (clientSocket != null) {
				handleClient(clientSocket);
			}
		}
	}

	public void handleClient(Socket clientSocket) throws SocketException {
		ServerConnection client = new ServerConnection(this, clientSocket);
		synchronized (clients) {
			clients.add(client);
		}

		Thread clientThread = new Thread(client);
		clientThread.setPriority(Thread.MAX_PRIORITY);
		clientThread.start();
	}

	public static void main(String[] args) {
		ServerNet server = new ServerNet();
		System.out.println("Starting server.....");

		try {
			server.initiateConnections();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
