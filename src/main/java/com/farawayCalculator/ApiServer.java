package com.farawayCalculator;

import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static spark.Spark.*;

public class ApiServer {

    public static void main(String[] args) {

        port(8080);

        ObjectMapper mapper = new ObjectMapper();

        post("/score", (req, res) -> {

           List<Player> players = mapper.readValue(
                    req.body(),
                    mapper.getTypeFactory().constructCollectionType(List.class, Player.class)
            );

            Game game = new Game(players);
            int winnerScore = 0;

            for (Player player:players){
                int score = ScoreCalculator.calculateScore(player).totalScore;
                game.playerScore.add(ScoreCalculator.calculateScore(player));
                if (winnerScore < score) {
                    winnerScore = score;
                    game.winnerPlayer = player.name;
                }
            }

            int lowestRegionCardExplorationDurationInGame = 76;
            boolean tiedGame = false;
            int playersWithWinnerScore = 0;

            for (Player player : players) {
                if (player.totalScore == winnerScore) {
                    playersWithWinnerScore++;
                }
            }
            game.winnerScore = winnerScore;

            if (playersWithWinnerScore > 1) {
                tiedGame = true;
            }

            if (tiedGame) {
                for (Player player : players) {
                    if (player.totalScore == winnerScore) {
                        if (player.lowestRegionCardExplorationDuration < lowestRegionCardExplorationDurationInGame) {
                            lowestRegionCardExplorationDurationInGame = player.lowestRegionCardExplorationDuration;
                            game.winnerPlayer = player.name;
                        }
                    }
                }
            }

            res.type("application/json");
            GameResult gameResult = new GameResult(game);
            return mapper.writeValueAsString(gameResult);

        });

    }

}
