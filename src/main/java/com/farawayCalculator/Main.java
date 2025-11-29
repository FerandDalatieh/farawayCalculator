package com.farawayCalculator;

import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public class Main {
    public static void main (String[] args){

        ObjectMapper mapper = new ObjectMapper();

        // Read players input form a json file
        InputStream gameInputFile = Main.class.getResourceAsStream("/InputTemplate.json");
        List<Player> players = mapper.readValue(
                gameInputFile,
                mapper.getTypeFactory().constructCollectionType(List.class, Player.class)
        );

       Game game = new Game(players);
       game.printGameResult();

    }
}
