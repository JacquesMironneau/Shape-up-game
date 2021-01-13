package fr.utt.lo02.projet.model.strategy;

public enum MoveRequestResult
{
	NO_CARD_IN_THE_ORIGIN_COORDINATE,
	MOVE_VALID,
	CARD_NOT_ADJACENT,
	CARD_NOT_IN_THE_LAYOUT,
	ORIGIN_AND_DESTINATION_ARE_EQUAL
}
