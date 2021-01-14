package fr.utt.lo02.projet.controller;

/**
 * Represent a game controller in the MVC pattern
 * the controller is thus an entry point for the view that can calls its method
 */
public interface GameController
{

    /**
     * Ask the controller to choose an action (move, place, end turn)
     *
     * @param choiceNumber the number of the choice (1 if it's the first choice in a turn, 2 if it's the second one)
     * @param choice       the actual choice of the player
     */
    void askChoice(int choiceNumber, int choice);

    /**
     * Ask to move a card from (x,y) to (x2,y2)
     *
     * @param x  the card to move abscissa
     * @param y  the card to move ordinate
     * @param x2 the move target abscissa
     * @param y2 the move target ordinate
     */
    void askMove(int x, int y, int x2, int y2);

    /**
     * Ask to place the card at the index cardIndex (from the playerhand)
     * to (x,y)
     *
     * @param x the place target abscissa
     * @param y the place target ordinate
     * @param cardIndex the index in the player hand
     */
    void askPlace(int x, int y, int cardIndex);

    /**
     * The request to end the turn
     */
    void endTurn();

    /**
     * The request to play an other turn
     */
    void play();

    /**
     * The request to end the current round
     */
    void endRound();

    /**
     * The request to end the current game
     */
    void endGame();

}
