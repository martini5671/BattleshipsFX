package BattleshipUtils;

public class MongoDM {

    private final String winner;
    private final String looser;
    private final String score;
    private final String date;
    private final String misses;
    private final String time;

    public MongoDM(String winner, String looser, String score, String date, String time, String misses) {
        this.winner = winner;
        this.looser = looser;
        this.score = score;
        this.date = date;
        this.time = time;
        this.misses = misses;
    }

    public String getWinner() {
        return winner;
    }

    public String getLooser() {
        return looser;
    }

    public String getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    public String getMisses() {
        return misses;
    }

    public String getTime() {
        return time;
    }
}
