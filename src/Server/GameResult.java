package eu.tomaswandahl.Server;

public class GameResult {
	private int tries;
	private char[] visibleWord;
	private boolean correct;
	private boolean gameFinished;
	private int score;

	public GameResult(int tries, char[] visibleWord, int score, boolean correct, boolean gameFinished) {
		this.tries = tries;
		this.visibleWord = visibleWord;
		this.correct = correct;
		this.gameFinished = gameFinished;
		this.score = score;
	}

	public String toString() {
		boolean wordCompleted = true;
		for (char c : visibleWord) {
			if (c == '_')
				wordCompleted = false;
		}
		StringBuilder word = new StringBuilder();
		for (char c : this.visibleWord) {
			word.append(c).append(",");
		}
		word.deleteCharAt(word.length() - 1);
		String word1 = word.toString();

		if (gameFinished) {
			if (correct) {
				if (wordCompleted) {
					return "Congratulations, you have won the round! The word was: " + word1.replace(",", "")
							+ ". The score is: " + score;
				}
			} else {
				return "Sorry. You have run out of guesses! You did not find the correct word. "
						+ "Your current score: " + score;
			}
		}

		String guessResult;

		if (correct) {
			guessResult = "correct";
		} else {
			guessResult = "incorrect";
		}

		return "(" + "Guess was: " + guessResult + " |" + word + "| Attempts left: " + this.tries + ")";
	}
}