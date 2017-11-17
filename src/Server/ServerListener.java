package eu.tomaswandahl.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerListener implements Runnable{
	private ServerController sc;
	private Socket clientSocket;
	private int port;
	private PrintWriter out;	
	private BufferedReader in;
	private String textData;
	private String[] parts;
	
	public ServerListener(Socket s, int port) {
		clientSocket = s;
		this.port = port;
		sc = new ServerController();
	}

	@Override
	public void run() {
		while(true) {
			try {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out = new PrintWriter(clientSocket.getOutputStream(), true);
			} catch(IOException e) {
				e.printStackTrace();
			}
				
			try {textData = in.readLine();
				while(textData == null) {
					textData = in.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			parts = textData.split(" ");
			
			// parts[0] should be guess or exit
			// parts[1] should be a character
			
			System.out.println("Message Recieved: " + textData);
			
			switch(parts[0].toLowerCase()) {
				case "guess":
					
					break;
				case "exit":
					// code
					break;
			}
		} 	
	}

}
