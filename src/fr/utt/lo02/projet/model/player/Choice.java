package fr.utt.lo02.projet.model.player;

/**
 * Lists the 3 possibilities of choice for a player in each turn: he can move, place or end his turn.
 * @author Baptiste, Jacques
 *
 */
public enum Choice
{
	/**
	 * Current player can move a card.
	 */
	MOVE_A_CARD,
	/**
	 * Current player can place a card.
	 */
	PLACE_A_CARD,
	/**
	 * Current player can end his turn.
	 */
	END_THE_TURN
}
