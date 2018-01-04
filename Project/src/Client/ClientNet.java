package eu.vandahl.Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import eu.vandahl.Client.ClientListener;
import eu.vandahl.Client.Observer;
import eu.vandahl.common.ChatMessage;
import eu.vandahl.common.UtilityFunctions;

public class ClientNet {

	private Socket socket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private Observer obs;
	private Scanner sc;
	private String msg;
	private String username;
	private Path filesPath;

	public void connect(String host, int port, Observer obs) throws IOException {
		this.obs = obs;
		socket = new Socket(host, port);
		toServer = new ObjectOutputStream(socket.getOutputStream());
		fromServer = new ObjectInputStream(socket.getInputStream());
		new Thread(new ClientListener(obs, fromServer)).start();

		this.start();
	}

	public void start() {
		sc = new Scanner(System.in);

		while (true) {
			System.out.println("Yes?");
			msg = sc.nextLine();

			String[] op = msg.split(" ");

			if (op[0].charAt(0) == '/') {
				op[0] = UtilityFunctions.removeCharAt(op[0], 0);
				switch (op[0].toUpperCase()) {
				case "LOGIN":
					// Login user
					username = op[1];
					try {
						toServer.writeObject(new ChatMessage(2, op[1]));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					filesPath = Paths.get(
							"C:\\Users\\Tomas Vändahl\\Documents\\EclipseWorkspace\\ChatProject\\" + op[1] + "Files");
					if (!Files.exists(filesPath)) {
						File dir = new File(op[1] + "Files");
						dir.mkdir();
					}

					// abcdefghijklmnopqrstuvwxyzåäö

				case "LOGOUT":
					try {
						toServer.writeObject(new ChatMessage(0, op[1]));
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;

				case "SENDFILE":
					String sendTo = op[2];
					Path FileToSend = Paths.get(filesPath.toString() + "\\" + op[1]);
					System.out.println("Attempting to send " + FileToSend + " to " + sendTo);

					File file = new File(FileToSend.toString());
					byte[] content = null;

					try {
						content = Files.readAllBytes(file.toPath());
						ChatMessage cm = new ChatMessage(ChatMessage.FILE, content, op[2], op[1]);
						toServer.writeObject(cm);
					} catch (IOException e) {
						System.out.println("Error while sending file, please try again!");
						e.printStackTrace();
						continue;
					}

					break;
				default:
					System.out.println("Faulty command!");
				}

				continue;
			} else {
				try {
					toServer.writeObject(new ChatMessage(1, msg));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public void sendMsg(ChatMessage msg) {
		try {
			toServer.writeObject(msg);
		} catch (IOException e) {
			obs.printMsg("Error sending message to server!");
		}
	}

	public static void main(String[] args) {
		try {
			new ClientNet().connect("localhost", 420, new ClientView());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
