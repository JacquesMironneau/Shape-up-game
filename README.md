# Shape Up Game

## Introduction
As part of a course at UTT, we had to program a game using OOP in Java. The game is called Shape Up and it is a board game using cards. The goal is to have the best score, calculating from the different alignments of cards which have the same characteristics.  
A card has 3 characteristics: Shape, Color and Filling. The game can be played by 2 to 3 players. 
IMAGES CARTES
The detailed rules can be found here: http://goodlittlegames.co.uk/games/06-shape-up.html  
As well as the subject, which is in French: https://moodle.utt.fr/pluginfile.php/22853/mod_resource/content/3/PROJET%20LO02.pdf 

To create the game, we decided to change the graphic charter in creating ourselves cards. To make this, we called upon a friend, Thomas Durand who is graphist to create the background, buttons and cards of the game. The idea was to represent stones which make an hologram appear when we put it on the table. So cards are represented by holograms, that’s why you can see it sizzle.
The game’s music was made by another friend, Marceau Canu, who is a music producer.

## Installation: 
Java 14+ is required in order to play the game, you can find it here: https://jdk.java.net/  
You can install the game by downloading the jar file here: https://github.com/JacquesMironneau/Shape-up-game/releases  

The game is then executable with a mere double click or via a terminal with the  
```$ java -jar ShapeUpGame.jar```  
If the game is launched via the desktop, only the HMI view will be displayed (the game will not be playable in terminal and hmi simultaneously).

## Repository organisation:  
.  
├── **doc/**: javadoc, diagrams and project report (fr)  
├── **res/**: project ressources (tiles, cards, holograms, music)   
└── **src/**: java sources files  

## User informations:
To play to this game, you 


## Technical implementation
For this project we have used the SWING library and implemented several design patterns.

- We first use MVC pattern to separate the logic from the view of the application. Every user action is given to a controller which then changes the state of the model. Next, the model notifies the views (using the Observer pattern) that its state has changed. 
According to the specification, the game must be playable using terminal and GUI at the same time. Thus, the user can decide to start an action on its terminal and then move to the HMI. We have therefore made a custom scanner class which is interruptible (contrary to the Scanner class), our controller supports concurrency and allows the user to play with the chosen view.  

- For this project we have realized unit testing using the JUnit framework. Since our model quickly grows in complexity (with the addition of different boards, score calculator and game versions) we realized a robust test suite to cover the different game scenarios.  

- We also used the Visitor pattern to dissociate the score calculation from the board in itself. At the end of a turn, the calculator visits the board and then proceeds to the player score calculation.  

- The last pattern we used is the Strategy one. We used it to implement different strategies for the virtual players. A virtual player might use an easy or a hard strategy depending on the game set up.  

