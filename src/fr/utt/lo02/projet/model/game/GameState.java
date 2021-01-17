package fr.utt.lo02.projet.model.game;

/**
 * Lists all the states where game goes.
 * @author Baptiste, Jacques
 *	There is 13 states.
 */
public enum GameState
{
    MOVE,
    PLACE,
    PLACE_DONE,
    MOVE_DONE,
    ACTION_FAILED,

    FIRST_TURN,

    FIRST_CHOICE,
    SECOND_CHOICE,

    END_TURN,
    CARD_DRAW,
    VICTORY_CARD,
    END_ROUND,
    END_GAME
}
