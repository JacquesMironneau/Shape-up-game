package fr.utt.lo02.projet.model.game;

/**
 * Lists all the states where game goes.
 * @author Baptiste, Jacques
 *	There is 13 states.
 */
public enum GameState
{
    /**
     * The player may move a card
     */
    MOVE,
    /**
     * The player may place a card
     */
    PLACE,
    /**
     * The player has placed a card correctly
     */
    PLACE_DONE,
    /**
     * The player has moved a card correctly
     */
    MOVE_DONE,
    /**
     * An move or a place has been failed
     */
    ACTION_FAILED,

    /**
     * The player only has the choice to place at the first turn
     */
    FIRST_TURN,

    /**
     * The player can choose between place and move
     */
    FIRST_CHOICE,
    /**
     * The player can choose between move and end the turn
     */
    SECOND_CHOICE,

    /**
     * The current payer's turn is finished
     */
    END_TURN,
    /**
     * The player has draw a card
     */
    CARD_DRAW,

    /**
     * The victory card has been draw
     */
    VICTORY_CARD,
    /**
     * The round is finished
     */
    END_ROUND,
    /**
     * The game is finished
     */
    END_GAME
}
