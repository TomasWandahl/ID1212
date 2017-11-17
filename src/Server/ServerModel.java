package eu.tomaswandahl.Server;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ServerModel {
	private char[] soughtWord;
	private int score = 0;
	private int tries;
	private char[] publicWord;
	private Set<Character> guessedLetters = new HashSet<>();

	private List<String> possibleWords;
	private boolean onGoingGame = false;
	private Random rand = new Random();

	public ServerModel(String filepath) {
		try {
			Path path = Paths.get(filepath);
			possibleWords = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void resetGame() {
		tries = 0;
		soughtWord = null;
		guessedLetters.clear();
		onGoingGame = false;
	}

	public GameResult guess(char[] charArray) throws Exception {
		if (onGoingGame) {
			if (Arrays.equals(charArray, soughtWord)) {
				score++;
				resetGame();
				return new GameResult(-1, charArray, score, true, true);
			} else {
				tries--;
				if (tries == 0) {
					resetGame();
					return new GameResult(0, publicWord, score, false, false);
				}
			}
		} else {
			throw new Exception("There is no game running at the moment. Please start the game!");
		}
		return null;
	}

	public GameResult guess(char value) throws Exception {
		// player guessed a single char
		boolean correct = false;
		boolean completed = true;

		if (onGoingGame) {
			if (!guessedLetters.contains(value)) {
				// Game is running and player has not guessed the letter
				guessedLetters.add(value);
				for (int i = 0; i < soughtWord.length; i++) {
					if (soughtWord[i] == value) {
						correct = true;
						publicWord[i] = value;
					}
					if (publicWord[i] == '_') {
						// at least one character is not yet successfully guessed
						// so game is not completed
						completed = false;
					}
				}
				if (correct == false) {
					// no character in sought word matches guess
					tries--;
				}
				if (tries == 0 && completed == false) {
					resetGame();
					return new GameResult(-1, publicWord, score, correct, completed);
				}
				if (completed == true) {
					guessedLetters.clear();
					score++;
				}
				return new GameResult(tries, publicWord, score, correct, completed);
			} else {
				throw new Exception("Player has already guessed that letter. Try a new one!");
			}
		} else {
			throw new Exception("There is no game running at the moment. Please start the game!");
		}
	}

	public char[] start() {
		onGoingGame = true;
		// get a random word from the list of words handed by the assignment
		System.out.println("Model is fetching a word...");
		String word = possibleWords.get(rand.nextInt(possibleWords.size())).toLowerCase();
		soughtWord = word.toCharArray();
		tries = word.length();
		publicWord = new char[soughtWord.length];
		for (int i = 0; i < publicWord.length; i++) {
			publicWord[i] = '_';
		}
		System.out.println("Word fetched, game is starting...");
		return publicWord;
	}

}
