package fr.utt.lo02.projet.model.game;

/**
 * Lists all possible cases after making a place request. 
 * @author Baptiste, Jacques
 *	There is 4 different cases.
 */
public enum PlaceRequestResult
{
	/**
	 * The player asked to place a card that he doesn't have in his hand
	 */
	PLAYER_DOESNT_OWN_CARD,
	/**
	 * The player asked to place a card that is not adjacent to an already existing card
	 */
	CARD_NOT_ADJACENT,
	/**
	 * The player asked to place a card that is not the layout or to an already occupied location
	 */
	CARD_NOT_IN_THE_LAYOUT,

	/**
	 * The player made a correct placement
	 */
	CORRECT_PLACEMENT

}
