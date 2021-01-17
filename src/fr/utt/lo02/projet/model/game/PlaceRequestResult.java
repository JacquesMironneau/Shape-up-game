package fr.utt.lo02.projet.model.game;

/**
 * Lists all possible cases after making a place request. 
 * @author Baptiste, Jacques
 *	There is 4 different cases.
 */
public enum PlaceRequestResult
{
	PLAYER_DOESNT_OWN_CARD,
	CARD_NOT_ADJACENT,
	CARD_NOT_IN_THE_LAYOUT,
	CORRECT_PLACEMENT

}
