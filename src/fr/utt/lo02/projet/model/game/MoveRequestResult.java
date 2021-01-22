package fr.utt.lo02.projet.model.game;

/**
 * Lists all possible cases after making a move request. 
 * @author Baptiste, Jacques
 *	There is 5 different cases.
 */
public enum MoveRequestResult
{
	/**
	 * There is no card to move in the coordinates indicated by the current player.
	 * A movement can not be possible.
	 */
	NO_CARD_IN_THE_ORIGIN_COORDINATE,
	/**
	 * The move request is valid, all conditions are good.
	 */
	MOVE_VALID,
	/**
	 * The new card's coordinates are not adjacent whit an other card.
	 * A movement can not be possible.
	 */
	CARD_NOT_ADJACENT,
	/**
	 * The new card's coordinates are not in the layout.
	 * A movement can not be possible.
	 */
	CARD_NOT_IN_THE_LAYOUT,
	/**
	 * Current player indicates the same coordinates 2 times.
	 * A movement can not be possible.
	 */
	ORIGIN_AND_DESTINATION_ARE_EQUAL
}
