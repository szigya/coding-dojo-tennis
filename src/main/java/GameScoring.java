/**
 * Game scoring class for tennis matches.
 */
public class GameScoring {

    public static final String RECEIVER_WON = "RECEIVER";
    public static final String SERVER_WON = "SERVER";

    private static final int MIDDLE_INDEX = 2;
    private String[][] standardState = {
            {"LOVE", "0:15", "0:30", "0:40", RECEIVER_WON},
            {"15:0", "15:15", "15:30", "15:40", RECEIVER_WON},
            {"30:0", "30:15", "30:30", "30:40", RECEIVER_WON},
            {"40:0", "40:15", "40:30" /*,"Jump to deuceState*/},
            {SERVER_WON, SERVER_WON, SERVER_WON}
    };

    private String[] deuceState = {RECEIVER_WON, "RECEIVER_ADVANCE", "DEUCE", "SERVER_ADVANCE", SERVER_WON};

    private int server = 0;
    private int receiver = 0;

    public String getState() {
        if (isDeuceState())
            return getDeuceState();
        return getStandardState();
    }

    private String getStandardState() {
        return standardState[server][receiver];
    }

    private String getDeuceState() {
        int difference = server - receiver;
        return deuceState[MIDDLE_INDEX + difference];
    }

    private boolean isDeuceState() {
        return receiver >= 3 && server >= 3;
    }

    public void scoreServer() {
        checkIfFinalStateReached();
        server++;
    }


    public void scoreReceiver() {
        checkIfFinalStateReached();
        receiver++;
    }

    private void checkIfFinalStateReached() {
        String state = getState();
        if (RECEIVER_WON.equals(state) || SERVER_WON.equals(state))
            throw new IllegalStateException("Cannot leave state " + state + "[" + server + "," + receiver + "].");
    }

}
