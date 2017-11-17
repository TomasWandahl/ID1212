package eu.tomaswandahl.Server;

public class ServerController {
	private ServerModel serverModel = new ServerModel("C:\\Users\\Tomas Vändahl\\Downloads\\words.txt");
	ServerConnection con;

	public ServerController() {
		// empty constructor
	}

	public String makeGuess(String value) {
		GameResult result;

		try {
			if (value.length() > 1) {
				result = serverModel.guess(value.toCharArray());
			} else {
				result = serverModel.guess(value.toCharArray()[0]);
			}
		} catch (Exception e) {
			return e.toString().substring(21);
		}

		return result.toString();
	}

	public char[] startGame() {
		System.out.println("controller starting the game");
		return serverModel.start();
	}
}
