package eu.vandahl.Server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import eu.vandahl.common.ChatMessage;

public class ClientThread extends Thread {
	private Socket socket;
	private ObjectInputStream sInput;
	private ObjectOutputStream sOutput;
	private String username;
	private ChatMessage cm;
	private ChatServer cs;

	public boolean sendMessage(String msg) {
		if (!socket.isConnected()) {
			close();
			return false;
		}

		try {
			sOutput.writeObject(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("User " + username + " has disconnected!");
		}

		return true;
	}

	public void close() {
		try {
			if (sOutput != null)
				sOutput.close();
		} catch (Exception e) {
		}
		try {
			if (sInput != null)
				sInput.close();
		} catch (Exception e) {
		}
		try {
			if (socket != null)
				socket.close();
		} catch (Exception e) {
		}
	}

	public ClientThread(Socket socket, ChatServer cs) {
		this.cs = cs;
		this.socket = socket;

		try {
			sInput = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		boolean keepGoing = true;

		while (keepGoing) {
			try {
				cm = (ChatMessage) sInput.readObject();
			} catch (ClassNotFoundException | IOException e) {
				keepGoing = false;
				cs.broadcastMessage(username + "has disconnected!");
				continue;
			}

			String message = cm.getMessage();

			switch (cm.getType()) {
				case ChatMessage.LOGOUT:
					// cs.logoutClient(this, username);
	
					break;
	
				// User is sending a message
				case ChatMessage.MESSAGE:
					cs.broadcastUserMessage(username, cm.getMessage());
					break;
	
				// User is logging in
				case ChatMessage.LOGIN:
					this.username = cm.getMessage();
					System.out.println("User " + username + " has signed in!");
					break;
	
				// User is sending a message
				case ChatMessage.FILE:
					System.out.println("File-Transfer recieved: " + cm.getFilename() + " to " + cm.getRecipient());
					Path filesPath = Paths.get("C:\\Users\\Tomas Vändahl\\Documents\\EclipseWorkspace\\ChatProject\\"
							+ cm.getRecipient() + "Files\\" + cm.getFilename());
					File f = new File(filesPath.toString());
					try {
						Files.write(f.toPath(), cm.getContentBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
			}
		}
	}
}
