package tennis.scoring;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tennis.entities.Player;

public class GameTest {
	GameScoring game;
	Player playerOne;
	Player playerTwo;

	@BeforeMethod
	public void init() {
		playerOne = Mockito.mock(Player.class);
		playerTwo = Mockito.mock(Player.class);
		game = new GameScoringImpl(playerOne, playerTwo);
	}

	@Test
	public void testInitialScores() {
		// given (INIT)
		// when (INIT)
		// then
		int playerOneScore = game.getScore(playerOne);
		int playerTwoScore = game.getScore(playerOne);
		assertEquals(playerOneScore, 0);
		assertEquals(playerTwoScore, 0);
	}

	@Test
	public void testIfScoreCanBeIncreased() {
		// given
		// when
		game.earnPoint(playerOne);
		// then
		assertEquals(game.getScore(playerOne), 1);
	}

	@Test(enabled = false)
	public void testThatScoreCannotBeIncreasedAfterMaximum() {
		// given
		for (int i = 0; i < 4; i++)
			game.earnPoint(playerOne);
		int initialScore = game.getScore(playerOne);
		// when
		game.earnPoint(playerOne);
		// then
		assertEquals(game.getScore(playerOne), initialScore);
		assertEquals(game.getScore(playerOne), 5);
	}

	@Test
	public void testThatPlayerHasToEarnTwoScoresInARowToWin() {
		// given
		for (int i = 0; i < 3; i++) {
			game.earnPoint(playerOne);
			game.earnPoint(playerTwo);
		}
		// when
		game.earnPoint(playerOne);
		// then
		assertFalse(game.hasWinner());
	}

	@Test
	public void testThatPlayerHasToEarnFourScoresToWin() {
		// when
		game.earnPoint(playerOne);
		game.earnPoint(playerTwo);
		game.earnPoint(playerTwo);
		game.earnPoint(playerTwo);
		// then
		assertFalse(game.hasWinner());
	}

	@Test
	public void testThatInitialStateIsCalledLove() {
		assertEquals(game.getState(), "LOVE:LOVE");
	}

	@Test
	public void testThatFifteenWrittenToPlayerTwo() {
		// when
		game.earnPoint(playerTwo);
		// then
		assertEquals(game.getState(), "LOVE:FIFTEEN");
	}

	@Test
	public void testIfFortyFifteenWrittenCorretly() {
		// when
		game.earnPoint(playerOne);
		game.earnPoint(playerTwo);
		game.earnPoint(playerTwo);
		game.earnPoint(playerTwo);
		// then
		assertEquals(game.getState(), "FIFTEEN:FORTY");
	}

	@Test
	public void testIfDeuceWrittenCorretly() {
		// when
		for (int i = 0; i < 3; i++) {
			game.earnPoint(playerOne);
			game.earnPoint(playerTwo);
		}
		// then
		assertEquals(game.getState(), "DEUCE");
	}

	@Test
	public void testIfAdvantageWrittenCorretly() {
		// when
		for (int i = 0; i < 3; i++) {
			game.earnPoint(playerOne);
			game.earnPoint(playerTwo);
		}
		game.earnPoint(playerOne);
		// then
		assertEquals(game.getState(), "PLAYER ONE HAS AN ADVANTAGE");
	}
}
