package com.farawayCalculator;

import java.util.List;

public class Score {

    public static int calculateScore(Player player) {

        List<Integer> regionCardsExplorationDurations = player.regionCardsExplorationDurations;
        int score = 0;
        Integer questMultiplier = 0;
        String questType;
        Quest quest;
        int visibleResources = 0;

        // Loop through the 8 region cards from right last to first
        for (int cardPosition = 7; cardPosition >= 0; cardPosition--) {

            quest = RegionCardsCollection.getRegionCard(regionCardsExplorationDurations.get(cardPosition)).quest;

            questMultiplier = quest.multiplier;
            // When multiplier is not null, there could be score to be calculated
            if (questMultiplier != null) {
                questType = quest.type;
                if (questType == null) {
                    score += questMultiplier;
                } else {
                    if (requirementsMet(player, cardPosition)) {
                        visibleResources = countVisibleResources(player, cardPosition, questType);
                        score += visibleResources * questMultiplier;
                    }
                }
            }
        }
        return score;
    }

    public static int countVisibleResources(Player player, int visibleIndex, String resourceType) {

        // when visibleIndex is 7 it means we are on the right side with a single visible region card
        // moving downward shift to the left, with 0 all region cards are visible

        int countVisibleResources = 0;

        for (int i = 7; i >= visibleIndex; i--) {

            Resources resources = RegionCardsCollection.getRegionCard(player.regionCardsExplorationDurations.get(i)).resources;

            switch (resourceType) {
                case "uddu" -> countVisibleResources += resources.uddu;
                case "okiko" -> countVisibleResources += resources.okiko;
                case "nightTime" -> {
                    if (resources.nightTime) {
                        countVisibleResources += 1;
                    }
                }
                case "clue" -> {
                    if (resources.clue) {
                        countVisibleResources += 1;
                    }
                }
                default -> countVisibleResources += 0;
            }
        }
        return countVisibleResources;
    }

    public static boolean requirementsMet(Player player, int cardPosition) {

        boolean requirementsMet = true;
        RegionCard relevantRegionCard = RegionCardsCollection.getRegionCard(player.regionCardsExplorationDurations.get(cardPosition));

        if (relevantRegionCard.requirements.uddu > 0) {
            if (relevantRegionCard.requirements.uddu > countVisibleResources(player, cardPosition, "uddu")) {
                requirementsMet = false;
            }
        }

        if (relevantRegionCard.requirements.okiko > 0) {
            if (relevantRegionCard.requirements.okiko > countVisibleResources(player, cardPosition, "okiko")) {
                requirementsMet = false;
            }
        }

        if (relevantRegionCard.requirements.goldlog > 0) {
            if (relevantRegionCard.requirements.goldlog > countVisibleResources(player, cardPosition, "goldlog")) {
                requirementsMet = false;
            }
        }

        return requirementsMet;
    }
}
