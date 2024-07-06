# BattleshipsFX :boat:

BattleshipsFX is my first major Java project. It is a classic battleships game with a GUI which was coded using Java and JavaFX framework. The game also utilizes MongoDB to store a player's score. 

![grab-landing-page](https://github.com/martini5671/BattleshipsFX/blob/master/battleshipsFX_gif.gif)

## Windows installation
**Simple installation with JAR file:**
1. Download Java JDK 21
2. Install Java JDK 21
3. Adjust your system variables and add path to Java directory in Program Files.
4. Download jar file from this link: https://1drv.ms/u/c/50d18e26bb6460b5/EfWBEQk_Um9Agi0YmI6eVTYBV6jxLU1FG9X9_w7l5DeI-Q?e=vwRR4w
5. Open jar file using java.
6. Install MongoDB for windows. 


```java
some code here.. idk.. This section needs to be improved.
```

## Features
1. You can play awesome classic battleships game (and win 99% o time because the AI is really stupid). Maybe in the next release of the app I manage to implement Hunt/destroy strategy to make it smarter. 
2. The faster you win with the AI, and the lower the number of missed shots, the higher your score will be. Your final score will be disaplayed in the end of the game, and the score will be uploaded to your local instance of MongoDB. 
3. If you go to the "Statistics" section in the main menu, you will have a chance to have a look at the sorted scoreboard with all of the scores from the previous games. This scoreboard is obtained by getting, and sorting the data from the MongoDB database. 


## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)
