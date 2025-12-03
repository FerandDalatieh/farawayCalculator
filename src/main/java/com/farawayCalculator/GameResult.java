package com.farawayCalculator;

import java.util.ArrayList;
import java.util.List;

public class GameResult {

    public String winnerPlayer;
    public int winnerScore;
    public List<PlayerScore> playerScore;

    public GameResult(Game game) {
        this.winnerPlayer = game.winnerPlayer;
        this.winnerScore = game.winnerScore;
        this.playerScore = game.playerScore;
    }

}
