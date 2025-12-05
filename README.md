# Project Title

Score calculator of the board game **faraway**.

## Description

In a game of Faraway (published by KOSMOS), a players plays 8 cards from left to right, and the score at the end of the game from right to left. 
This project here aims to make it simple to calculate the score of each player.

## Getting Started

### Dependencies

* [jackson-databind](https://mvnrepository.com/artifact/tools.jackson.core/jackson-databind)
* [spark-core](https://mvnrepository.com/artifact/com.sparkjava/spark-core)
* [slf4j-api](https://mvnrepository.com/artifact/org.slf4j/slf4j-api)
* [slf4j-simple](https://mvnrepository.com/artifact/org.slf4j/slf4j-simple)

### Prerequisites

* Java JDK 21+
* Maven installed


### Build a runnable JAR (including dependencies)
1. Open a terminal in the project root.
2. Build the project using Maven:
```sh
mvn clean package
```
3. After building, a shaded (fat) JAR will be created in the target/ folder:
```sh
java -cp target/farawayCalculator-1.0-SNAPSHOT.jar com.farawayCalculator.ApiServer 
```

### Executing program

* Send POST request (for example in postman or using cUrl ... etc.) to the endpoint: http://localhost:8080/score. THe request body should be in the format of /src/main/resources/InputTemplate.json.
* The response would then return the individual cards scored of each player, total score of each player and the name/score of the winner.

Please note: the sanctuary cards IDs are the numbers that are shown on /src/main/resources/numbered-sanctuary_cards.png.
If you prefer to directly add detailed sanctuary cards in the response, you can use the template /src/main/resources/InputTemplate_detailed-sanctuary-cards.json instead.


## License

This project is licensed under the MIT License - see the LICENSE.md file for details
