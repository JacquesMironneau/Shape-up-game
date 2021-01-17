package fr.utt.lo02.projet.model.game;

/**
 * Lists all possible cases after making a move request. 
 * @author Baptiste, Jacques
 *	There is 5 different cases.
 */
public enum MoveRequestResult
{
	NO_CARD_IN_THE_ORIGIN_COORDINATE,
	MOVE_VALID,
	CARD_NOT_ADJACENT,
	CARD_NOT_IN_THE_LAYOUT,
	ORIGIN_AND_DESTINATION_ARE_EQUAL
}
