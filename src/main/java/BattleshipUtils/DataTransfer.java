package BattleshipUtils;

public class DataTransfer {

    private static final DataTransfer instance = new DataTransfer();
    private DataTransfer(){}

    public static DataTransfer getInstance()
    {
        return instance;
    }

    private String playerId;
    private  String difficulty;

    private  Battlefield battlefield;
    private  int score;
    private  int gameplayTime;
    private  int numberMisses;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public  String getDifficulty() {
        return difficulty;
    }

    public  void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public  Battlefield getBattlefield() {
        return battlefield;
    }

    public  void setBattlefield(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public  int getScore() {
        return score;
    }

    public  void setScore(int score) {
        this.score = score;
    }

    public  int getGameplayTime() {
        return gameplayTime;
    }

    public  void setGameplayTime(int gameplayTime) {
        this.gameplayTime = gameplayTime;
    }

    public  int getNumberMisses() {
        return numberMisses;
    }

    public  void setNumberMisses(int numberMisses) {
        this.numberMisses = numberMisses;
    }
}
