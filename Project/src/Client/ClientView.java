package eu.vandahl.Client;

import java.util.Scanner;

public class ClientView implements Observer{
	private Scanner sc = new Scanner(System.in);
	private String op;
	private ClientController cont;

	public void printMsg(String message) {
		System.out.println(message);
	}
	
	public void start() {
		ClientNet net = new ClientNet();
		cont = new ClientController(net);
		System.out.println("Please connect to server using: 'connect USERNAME IP PORT'");
		
		String[] cmd = sc.nextLine().split(" ");
	}
	
	public static void main(String[] args) {
		new ClientView().start();
		
		
	}

}
