package com.farawayCalculator;

import java.util.List;

import static com.farawayCalculator.utils.min4;

public class Score {

    public static int calculateScore(Player player) {

        List<Integer> regionCardsExplorationDurations = player.regionCardsExplorationDurations;
        int score = 0;
        Integer questMultiplier = 0;
        String questType;
        Quest quest;
        int visibleCriteria = 0;

        // Loop through the 8 region cards from right last to first
        for (int cardPosition = 7; cardPosition >= 0; cardPosition--) {

            quest = RegionCardsCollection.getRegionCard(regionCardsExplorationDurations.get(cardPosition)).quest;

            questMultiplier = quest.multiplier;
            // When multiplier is not null, there could be score to be calculated
            if (questMultiplier != null) {
                questType = quest.type;
                if (questType == null) {
                    if (requirementsMet(player, cardPosition)) {
                        score += questMultiplier;
                    }
                } else {
                    if (requirementsMet(player, cardPosition)) {
                        visibleCriteria = countVisibleCriteria(player, cardPosition, questType);
                        score += visibleCriteria * questMultiplier;
                    }
                }
            }
        }
        return score;
    }

    public static int countVisibleCriteria(Player player, int visibleIndex, String questType) {

        // when visibleIndex is 7 it means we are on the right side with a single visible region card
        // moving downward shift to the left, with 0 all region cards are visible

        int countVisibleCriteria = 0;
        int countVisibleGreenCards = 0;
        int countVisibleRedCards = 0;
        int countVisibleBlueCards = 0;
        int countVisibleYellowCards = 0;

        for (int i = 7; i >= visibleIndex; i--) {

            Resources resources = RegionCardsCollection.getRegionCard(player.regionCardsExplorationDurations.get(i)).resources;
            String color = RegionCardsCollection.getRegionCard(player.regionCardsExplorationDurations.get(i)).color;

            switch (questType) {
                case "uddu" -> countVisibleCriteria += resources.uddu;
                case "okiko" -> countVisibleCriteria += resources.okiko;
                case "goldlog" -> countVisibleCriteria += resources.goldlog;
                case "nightTime" -> {
                    if (resources.nightTime) {
                        countVisibleCriteria += 1;
                    }
                }
                case "clue" -> {
                    if (resources.clue) {
                        countVisibleCriteria += 1;
                    }
                }
                case "red" -> {
                    if (color.equals("red")) {
                        countVisibleCriteria += 1;
                    }
                }
                case "green" -> {
                    if (color.equals("green")) {
                        countVisibleCriteria += 1;
                    }
                }
                case "blue" -> {
                    if (color.equals("blue")) {
                        countVisibleCriteria += 1;
                    }
                }
                case "yellow/red" -> {
                    if (color.equals("yellow") || color.equals("red")) {
                        countVisibleCriteria += 1;
                    }
                }
                case "yellow/green" -> {
                    if (color.equals("yellow") || color.equals("green")) {
                        countVisibleCriteria += 1;
                    }
                }
                case "yellow/blue" -> {
                    if (color.equals("yellow") || color.equals("blue")) {
                        countVisibleCriteria += 1;
                    }
                }
                case "colorSet" -> {
                    switch (color) {
                        case "green" -> countVisibleGreenCards++;
                        case "red" -> countVisibleRedCards++;
                        case "blue" -> countVisibleBlueCards++;
                        case "yellow" -> countVisibleYellowCards++;
                        default -> countVisibleCriteria = countVisibleCriteria;
                    }
                }
                default -> countVisibleCriteria = countVisibleCriteria;
            }
        }

        if (questType.equals("colorSet")) {
            countVisibleCriteria = min4(countVisibleGreenCards, countVisibleRedCards, countVisibleBlueCards, countVisibleYellowCards);
        }

        return countVisibleCriteria;
    }

    public static boolean requirementsMet(Player player, int cardPosition) {

        boolean requirementsMet = true;
        RegionCard relevantRegionCard = RegionCardsCollection.getRegionCard(player.regionCardsExplorationDurations.get(cardPosition));

        if (relevantRegionCard.requirements.uddu > 0) {
            if (relevantRegionCard.requirements.uddu > countVisibleCriteria(player, cardPosition, "uddu")) {
                requirementsMet = false;
            }
        }

        if (relevantRegionCard.requirements.okiko > 0) {
            if (relevantRegionCard.requirements.okiko > countVisibleCriteria(player, cardPosition, "okiko")) {
                requirementsMet = false;
            }
        }

        if (relevantRegionCard.requirements.goldlog > 0) {
            if (relevantRegionCard.requirements.goldlog > countVisibleCriteria(player, cardPosition, "goldlog")) {
                requirementsMet = false;
            }
        }

        return requirementsMet;
    }
}
