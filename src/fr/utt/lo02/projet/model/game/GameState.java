package fr.utt.lo02.projet.model.game;

/**
 * Lists all the states where game goes.
 * @author Baptiste, Jacques
 *	There is 13 states.
 */
public enum GameState
{
	/**
	 * Current player has to move a card.
	 */
    MOVE,
    /**
     * Current player has to place a card.
     */
    PLACE,
    /**
     * Current player just placed a card.
     */
    PLACE_DONE,
    /**
     * Current player just moved a card.
     */
    MOVE_DONE,
    /**
     * Current player has put a card on a place which is not valid.
     */
    ACTION_FAILED,
    /**
     * It's the first turn of the round.
     */
    FIRST_TURN,

    /**
     * Current player has to make his first choice.
     */
    FIRST_CHOICE,
    /**
     * Current player has to make his second choice.
     */
    SECOND_CHOICE,

    /**
     * Current player just ended his turn.
     */
    END_TURN,
    /**
     * Current plater just draw a card.
     */
    CARD_DRAW,
    /**
     * It's time to set victory cards to players.
     */
    VICTORY_CARD,
    /**
     * It's the end of the round.
     */
    END_ROUND,
    /**
     * It's the end of the game.
     */
    END_GAME
}
