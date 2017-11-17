package eu.tomaswandahl.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ClientListener implements Runnable {
	private Observer observer;
	private PrintWriter toServer;
	private BufferedReader fromServer;

	public ClientListener(Observer obs) {
		this.observer = obs;
	}

	@Override
	public void run() {

		try {
			while (true) {
				String resultFromServer = fromServer.readLine();
				if (resultFromServer == null) {
					throw new Exception("message from server is null");
				}
				observer.printMsg(resultFromServer);
			}
		} catch (Exception e) {
			observer.printMsg("Error when receiving message from the server");
		}
	}
}
