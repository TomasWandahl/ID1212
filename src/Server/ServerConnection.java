package eu.tomaswandahl.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection implements Runnable {
	private Socket clientSocket;
	private ServerNet server;
	private final ServerController controller = new ServerController();

	private PrintWriter toClient;
	private BufferedReader fromClient;
	private ServerModel sm;

	private String textData;
	private String[] command;

	public ServerConnection(ServerNet server, Socket s) {
		// read word from file HERE
		this.server = server;
		clientSocket = s;
	}

	@Override
	public void run() {
		// set up streams
		try {
			fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			toClient = new PrintWriter(clientSocket.getOutputStream(), false);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				textData = fromClient.readLine();
				while (textData == null) {
					textData = fromClient.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			command = textData.split(" ");

			switch (command[0].toLowerCase()) {
			case "guess":
				if (command[1] == null) {
					toClient.println("A guess must be followed by a character or a word!");
					break;
				} else {
					System.out.println("Server got message: " + command[1]);
					toClient.println(controller.makeGuess(command[1]));
					break;
				}
			case "start":
				System.out.println("Server got told to start game!");
				char[] soughtWord = controller.startGame();
				toClient.println("Game has been st	arted. Word is " + soughtWord.length + " characters long. "
						+ "To make a guess, please use the command 'guess'");
				break;
			case "quit":
				System.out.println("Server got told to quit game!");
				toClient.println("Quitting game!");
				// code to quit game!
				break;
			default:
				System.out.println("Invalid command!");
				toClient.println("Invalid command. Try another one. Valid commands are: 'guess', 'start', 'quit'");
				break;
			}
		}
	}

	public void forwardResult(String result, String publicWord, String guesses) {
		result = result + publicWord + guesses;
		System.out.println("Attempting to send '" + result + "' to client!");
		if (toClient != null) {
			System.out.println("Out is not null!");
			toClient.print(result);
			System.out.println("Message has been sent. dont know if its recieved doe");
		} else {
			System.out.println("lol wat");
		}
	}
}
