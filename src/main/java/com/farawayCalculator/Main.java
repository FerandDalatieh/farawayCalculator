package com.farawayCalculator;

import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public class Main {
    public static void main (String[] args){

        ObjectMapper mapper = new ObjectMapper();

        // Read region cards input form a json file
        InputStream regionCardsInputFile = Main.class.getResourceAsStream("/RegionCards.json");
        List<RegionCard> regionCards = mapper.readValue(
                regionCardsInputFile,
                mapper.getTypeFactory().constructCollectionType(List.class, RegionCard.class)
        );

        // Read game input form a json file
        InputStream gameInputFile = Main.class.getResourceAsStream("/InputTemplate.json");
        List<Player> players = mapper.readValue(
                gameInputFile,
                mapper.getTypeFactory().constructCollectionType(List.class, Player.class)
        );

        // test printing out the quest multiplier of a specific region card of a specific player
        System.out.println(regionCards.get(players.get(1).regionCardsExplorationDurations.get(2)).quest.multiplier);

        // initialize game, players and cards

        // calculate score of each player

        // set the winner based of the score

        // output the score of each player and the winner


    }
}
