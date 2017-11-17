package eu.tomaswandahl.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientNet {
	private Socket socket;
	private PrintWriter toServer;
	private BufferedReader fromServer;

	public void connect(String host, int port, Observer obs) throws IOException {
		socket = new Socket(host, port);
		toServer = new PrintWriter(socket.getOutputStream(), true);
		fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		new Thread(new ClientListener(obs)).start();
	}

	public void quit() throws IOException {
		toServer.println("quit");
		socket.close();
		socket = null;
	}

	public void start() {
		toServer.println("start");
	}

	public void guess(String s) {
		toServer.println("guess " + s);

	}
}