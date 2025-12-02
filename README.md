# Project Title

Score calculator of the board game **faraway**.

## Description

In a game of Faraway (published by KOSMOS), a players plays 8 cards from left to right, and the score at the end of the game from right to left. 
This project here aims to make it simple to calculate the score of each player.

## Getting Started

### Dependencies

*  [jackson-databind](https://mvnrepository.com/artifact/tools.jackson.core/jackson-databind)

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
java -jar target/farawayCalculator-1.0-SNAPSHOT.jar 
```

### Executing program

* Edit the content of the file /src/main/resources/InputTemplate.json to correspond with your game
* Run the app


## License

This project is licensed under the MIT License - see the LICENSE.md file for details
