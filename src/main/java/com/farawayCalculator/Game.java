package com.farawayCalculator;

import java.util.List;

public class Game {

    public List<Player> players;

    public Game(List<Player> players) {
        this.players = players;
    }

    public void printGameResult() {
        String winnerName = players.getFirst().name;
        int winnerScore = 0;
        int currentScore = 0;
        String currentName;
        boolean isTie = true;

        for (int i = 0; i < players.size(); i++) {
            currentScore = Score.calculateScore(players.get(i));
            currentName = players.get(i).name;

            System.out.println(currentName + " got " + currentScore + " points.");

            if (currentScore != winnerScore) {
                isTie = false;
                if (currentScore > winnerScore) {
                    winnerName = currentName;
                    winnerScore = currentScore;
                }
            }
        }
        System.out.println();
        if (!isTie) {
            System.out.println("The winner is " + winnerName + "!");
        } else {
            System.out.println("It is a tie!");
        }

    }

}
