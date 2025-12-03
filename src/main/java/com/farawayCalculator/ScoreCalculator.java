package com.farawayCalculator;

import java.util.List;

import static com.farawayCalculator.utils.min3;
import static com.farawayCalculator.utils.min4;

public class ScoreCalculator {

    public static PlayerScore calculateScore(Player player) {

        List<Integer> regionCardsExplorationDurations = player.regionCardsExplorationDurations;
        int totalScore = 0;
        Integer questMultiplier = 0;
        String questType;
        Quest quest;
        int visibleCriteria = 0;
        PlayerScore playerScore = new PlayerScore();

        // Loop through the 8 region cards from most right to first left
        for (int cardPosition = 7; cardPosition >= 0; cardPosition--) {

            int explorationDurationOfCardPosition = regionCardsExplorationDurations.get(cardPosition);
            quest = RegionCardsCollection.getRegionCard(explorationDurationOfCardPosition).quest;
            questMultiplier = quest.multiplier;

            // When multiplier is not null, there could be score to be calculated
            if (questMultiplier != null && requirementsMet(player, cardPosition)) {
                questType = quest.type;
                if (questType == null) {
                    playerScore.cardsScores[7-cardPosition] = questMultiplier;
                    totalScore += questMultiplier;
                } else {
                    visibleCriteria = countVisibleCriteria(player, cardPosition, questType);
                    playerScore.cardsScores[7-cardPosition] = visibleCriteria * questMultiplier;
                    totalScore += visibleCriteria * questMultiplier;
                }
            } else {
                playerScore.cardsScores[7-cardPosition] = 0;
            }
        }

        int sanctuaryCardsCount = player.sanctuaryCards.size();
        playerScore.cardsScores[8] = 0;

        if (sanctuaryCardsCount > 0) {
            // Loop through sanctuary cards
            for (int i = 0; i < sanctuaryCardsCount; i++) {

                SanctuaryCard sanctuaryCard = player.sanctuaryCards.get(i);
                quest = sanctuaryCard.quest;
                questMultiplier = quest.multiplier;

                if (questMultiplier != null) {
                    questType = quest.type;
                    if (questType == null) {
                        playerScore.cardsScores[8] += questMultiplier;
                        totalScore += questMultiplier;
                    } else {
                        // We are using visibleIndex=0 (left most) to indicate that all region cards are open
                        visibleCriteria = countVisibleCriteria(player, 0, questType);
                        playerScore.cardsScores[8] += visibleCriteria * questMultiplier;
                        totalScore += visibleCriteria * questMultiplier;
                    }
                }

            }
        }

        playerScore.name = player.name;
        playerScore.totalScore = totalScore;

        return playerScore;
    }

    public static int countVisibleCriteria(Player player, int visibleIndex, String questType) {

        // when visibleIndex is 7 it means we are on the right side with a single visible region card
        // moving downward shift to the left, with 0 all region cards are visible

        int countVisibleCriteria = 0;
        int countVisibleGreenCards = 0;
        int countVisibleRedCards = 0;
        int countVisibleBlueCards = 0;
        int countVisibleYellowCards = 0;
        int countVisibleUddu = 0;
        int countVisibleOkiko = 0;
        int countVisibleGoldlog = 0;

        for (int cardPosition = 7; cardPosition >= visibleIndex; cardPosition--) {

            int explorationDurationOfCardPosition = player.regionCardsExplorationDurations.get(cardPosition);
            RegionCard regionCardOfCardPosition = RegionCardsCollection.getRegionCard(explorationDurationOfCardPosition);
            Resources resources = regionCardOfCardPosition.resources;
            String color = regionCardOfCardPosition.color;

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
                case "yellow" -> {
                    if (color.equals("yellow")) {
                        countVisibleCriteria += 1;
                    }
                }
                case "gray" -> {
                    if (color.equals("gray")) {
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
                case "blue/green" -> {
                    if (color.equals("blue") || color.equals("green")) {
                        countVisibleCriteria += 1;
                    }
                }
                case "red/green" -> {
                    if (color.equals("red") || color.equals("green")) {
                        countVisibleCriteria += 1;
                    }
                }
                case "blue/red" -> {
                    if (color.equals("blue") || color.equals("red")) {
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
                case "resourcesSet" -> {
                    countVisibleUddu += resources.uddu;
                    countVisibleOkiko += resources.okiko;
                    countVisibleGoldlog += resources.goldlog;
                }
                default -> countVisibleCriteria = countVisibleCriteria;
            }
        }

        int sanctuaryCardsCount = player.sanctuaryCards.size();

        if (sanctuaryCardsCount > 0) {
            for (int i = 0; i < sanctuaryCardsCount; i++) {
                SanctuaryCard sanctuaryCard = player.sanctuaryCards.get(i);
                Resources resources = sanctuaryCard.resources;
                String color = sanctuaryCard.color;

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
                    case "yellow" -> {
                        if (color.equals("yellow")) {
                            countVisibleCriteria += 1;
                        }
                    }
                    case "gray" -> {
                        if (color.equals("gray")) {
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
                    case "blue/green" -> {
                        if (color.equals("blue") || color.equals("green")) {
                            countVisibleCriteria += 1;
                        }
                    }
                    case "red/green" -> {
                        if (color.equals("red") || color.equals("green")) {
                            countVisibleCriteria += 1;
                        }
                    }
                    case "blue/red" -> {
                        if (color.equals("blue") || color.equals("red")) {
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
                    case "resourcesSet" -> {
                        countVisibleUddu += resources.uddu;
                        countVisibleOkiko += resources.okiko;
                        countVisibleGoldlog += resources.goldlog;
                    }
                    default -> countVisibleCriteria = countVisibleCriteria;
                }
            }
        }

        if (questType.equals("colorSet")) {
            countVisibleCriteria = min4(countVisibleGreenCards, countVisibleRedCards, countVisibleBlueCards, countVisibleYellowCards);
        } else if (questType.equals("resourcesSet")) {
            countVisibleCriteria = min3(countVisibleUddu, countVisibleOkiko, countVisibleGoldlog);
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
