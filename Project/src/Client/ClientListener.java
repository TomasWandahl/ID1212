package eu.vandahl.Client;

import java.io.IOException;
import java.io.ObjectInputStream;

import eu.vandahl.common.ChatMessage;

public class ClientListener implements Runnable {

	private Observer obs;
	private ObjectInputStream fromServer;
	private ChatMessage cm;

	public ClientListener(Observer obs, ObjectInputStream fromServer) {
		this.obs = obs;
		this.fromServer = fromServer;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Object scm = fromServer.readObject();
				if (scm == null) {
					throw new Exception("Message from server is null!");
				}
				obs.printMsg(scm.toString());
			} catch (Exception e) {
				obs.printMsg("Connection to server has been terminated!");
				break;
			}

		}
	}
}
