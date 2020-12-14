package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;

import java.util.List;

public interface PlayerStrategy
{
	Choice makeChoice();

	PlaceRequest makePlaceRequest(AbstractBoard board, List<Card> playerHand);

	MoveRequest makeMoveRequest(AbstractBoard board);
}
