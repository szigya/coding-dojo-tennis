import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GameScoringTest {

    private GameScoring gameScoring;

    @BeforeMethod
    public void init() {
        gameScoring = new GameScoring();
    }

    @Test
    public void testInitialScoreIsLove() {
        assertEquals(gameScoring.getState(), "LOVE");
    }

    @Test
    public void testServerScoreIs15_0() {
        scorePlayers(1, 0);
        assertEquals(gameScoring.getState(), "15:0");
    }

    private void scorePlayers(int server, int receiver) {
        int i = 0;
        for (; i < Math.min(server, receiver); i++) {
            gameScoring.scoreServer();
            gameScoring.scoreReceiver();
        }
        for (; i < server; i++) {
            gameScoring.scoreServer();
        }
        for (; i < receiver; i++) {
            gameScoring.scoreReceiver();
        }
    }

    @Test
    public void testReceiverScoreIs0_15() {
        scorePlayers(0, 1);
        assertEquals(gameScoring.getState(), "0:15");
    }

    @Test
    public void testReceiverAndServerScoreIs15_15() {
        scorePlayers(1, 1);
        assertEquals(gameScoring.getState(), "15:15");
    }

    @Test
    public void test3_1Is40_15() {
        scorePlayers(3, 1);
        assertEquals(gameScoring.getState(), "40:15");
    }

    @Test
    public void testFortyFortyIsDeuce() {
        scorePlayers(3, 3);
        assertEquals(gameScoring.getState(), "DEUCE");
    }

    @Test
    public void testFourReceiverScoreIsReceiverWin() {
        scorePlayers(0, 4);
        assertEquals(gameScoring.getState(), GameScoring.RECEIVER_WON);
    }

    @Test
    public void testFourServerScoreIsServerWin() {
        scorePlayers(4, 1);
        assertEquals(gameScoring.getState(), GameScoring.SERVER_WON);
    }

    @Test
    public void testFourServerScoreWithFourReceiverScoreIsDeuce() {
        scorePlayers(4, 4);
        assertEquals(gameScoring.getState(), "DEUCE");
    }

    @Test
    public void testNineServerScoreAndEightReceiverScoreIsServerAdvance() {
        scorePlayers(9, 8);
        assertEquals(gameScoring.getState(), "SERVER_ADVANCE");
    }

    @Test
    public void testTenReceiverScoreAndEightServerScoreIsReceiver() {
        scorePlayers(8, 10);
        assertEquals(gameScoring.getState(), GameScoring.RECEIVER_WON);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testSixReceiverScoreThrowsIllegalStateException() {
        scorePlayers(1, 5);
        gameScoring.getState();
    }
}
