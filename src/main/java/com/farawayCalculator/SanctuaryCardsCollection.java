package com.farawayCalculator;

import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public class SanctuaryCardsCollection {

    public static List<SanctuaryCard> sanctuaryCards;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream sanctuaryCardsInputFile = Main.class.getResourceAsStream("/SanctuaryCards.json");
            sanctuaryCards = mapper.readValue(sanctuaryCardsInputFile, mapper.getTypeFactory().constructCollectionType(List.class, SanctuaryCard.class));
        } catch (Exception e) {
            e.printStackTrace();
            sanctuaryCards = List.of();
        }
    }

    public static SanctuaryCard getSanctuaryCard(int sanctuaryCardsID) {
        return sanctuaryCards.get(sanctuaryCardsID);
    }

}
