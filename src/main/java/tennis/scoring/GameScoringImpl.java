package tennis.scoring;

import tennis.entities.Player;
import tennis.exceptions.NoWinnerException;

public class GameScoringImpl implements GameScoring {

	private class ScoredPlayer {

		private Player player;

		private int score;

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public Player getPlayer() {
			return player;
		}

		public ScoredPlayer(Player player) {
			this.player = player;
			this.score = 0;
		}
	}

	private ScoredPlayer playerOne;
	private ScoredPlayer playerTwo;

	public boolean hasWinner = false;

	public GameScoringImpl(Player playerOne, Player playerTwo) {
		this.playerOne = new ScoredPlayer(playerOne);
		this.playerTwo = new ScoredPlayer(playerTwo);
	}

	public boolean hasWinner() {
		return hasWinner;
	}

	public Player getWinner() throws NoWinnerException {
		if (!this.hasWinner()) {
			throw new NoWinnerException();
		}
		if (playerOne.getScore() > playerTwo.getScore()) {
			return playerOne.getPlayer();
		}
		return playerTwo.getPlayer();
	}

	public int getScore(Player player) {
		ScoredPlayer scoredPlayer = getScoredPlayerForPlayer(player);
		return scoredPlayer.getScore();
	}

	private void increaseScoreByOne(Player player) {
		ScoredPlayer scoredPlayer = getScoredPlayerForPlayer(player);
		scoredPlayer.setScore(scoredPlayer.getScore() + 1);
	}

	private ScoredPlayer getScoredPlayerForPlayer(Player player) {
		if (playerOne.getPlayer().equals(player)) {
			return playerOne;
		}
		if (playerTwo.getPlayer().equals(player)) {
			return playerTwo;
		}
		throw new IllegalArgumentException();
	}

	public String getState() {
		if (hasWinner()) {
			return winnerToString();
		}
		if (getScoreSum() < 6) {
			return normalStateToString();
		}
		return advantageToString();
	}

	private String advantageToString() {
		if (playerOne.getScore() == playerTwo.getScore())
			return "DEUCE";
		if (playerOne.getScore() > playerTwo.getScore())
			return "PLAYER ONE HAS AN ADVANTAGE";
		return "PLAYER TWO HAS AN ADVANTAGE";
	}

	private String normalStateToString() {
		String playerOneScoreName = getScoreName(playerOne.getScore());
		String playerTwoScoreName = getScoreName(playerTwo.getScore());
		return playerOneScoreName + ":" + playerTwoScoreName;
	}

	private String winnerToString() {
		if (playerOne.getPlayer().equals(getWinner())) {
			return "PLAYER ONE WON THE GAME";
		}
		return "PLAYER TWO WON THE GAME";
	}

	private String getScoreName(int score) {
		switch (score) {
		case 0:
			return "LOVE";
		case 1:
			return "FIFTEEN";
		case 2:
			return "THIRTY";
		case 3:
			return "FORTY";
		default:
			return "";
		}
	}

	private int getScoreSum() {
		return playerOne.getScore() + playerTwo.getScore();
	}

	public void earnPoint(Player player) {
		increaseScoreByOne(player);
		calculateWinner();
	}

	private void calculateWinner() {
		if (getScoreDifference() >= 2
				&& (playerOne.getScore() > 3 || playerTwo.getScore() > 3)) {
			this.hasWinner = true;
		}
	}

	private int getScoreDifference() {
		return Math.abs(playerOne.getScore() - playerTwo.getScore());
	}
}
