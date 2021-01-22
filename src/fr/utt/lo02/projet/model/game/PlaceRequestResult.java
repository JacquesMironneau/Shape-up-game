package fr.utt.lo02.projet.model.game;

/**
 * Lists all possible cases after making a place request. 
 * @author Baptiste, Jacques
 *	There is 4 different cases.
 */
public enum PlaceRequestResult
{
	/**
	 * Player doesn't own card.
	 * A placement can not be possible.
	 */
	PLAYER_DOESNT_OWN_CARD,
	/**
	 * Coordinates indicated by the current player are adjacent with an other card.
	 * A placement can not be possible.
	 */
	CARD_NOT_ADJACENT,
	/**
	 * Coordinates indicated by the current player are not in the layout.
	 * A placement can not be possible.
	 */
	CARD_NOT_IN_THE_LAYOUT,
	/**
	 * The place request is valid, all conditions are good.
	 */
	CORRECT_PLACEMENT

}
