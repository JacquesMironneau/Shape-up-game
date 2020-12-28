package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;

import java.util.List;

public interface PlayerStrategy
{	
	Choice makeChoice(AbstractBoard board, Card victoryCard, List<Card> playerHand);

	PlaceRequest makePlaceRequest(AbstractBoard board, Card victoryCard, List<Card> playerHand);

	MoveRequest makeMoveRequest(AbstractBoard board, Card victoryCard, List<Card> playerHand);
}
