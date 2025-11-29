package com.farawayCalculator;

import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public class RegionCardsCollection {

    public static List<RegionCard> regionCards;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream regionCardsInputFile = Main.class.getResourceAsStream("/RegionCards.json");
            regionCards = mapper.readValue(regionCardsInputFile, mapper.getTypeFactory().constructCollectionType(List.class, RegionCard.class));
        } catch (Exception e) {
            e.printStackTrace();
            regionCards = List.of();
        }
    }

    public static RegionCard getRegionCard(int regionCardsExplorationDurations) {
        return regionCards.get(regionCardsExplorationDurations);
    }

}
