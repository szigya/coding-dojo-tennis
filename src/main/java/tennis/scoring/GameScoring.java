package tennis.scoring;

import tennis.entities.Player;
import tennis.exceptions.NoWinnerException;

public interface GameScoring {
	public boolean hasWinner();

	public Player getWinner() throws NoWinnerException;

	public int getScore(Player player);

	public String getState();

	public void earnPoint(Player player);
}
