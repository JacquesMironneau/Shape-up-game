package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;

import java.util.ArrayList;
import java.util.List;

public class RandomStrategy implements PlayerStrategy
{
	
	public RandomStrategy() {
	
	}
	
	@Override
	public Choice makeChoice(AbstractBoard board, Card victoryCard, List<Card> playerHand)
	{
		int randomNumber = 1 + (int) (Math.random() * ((2 - 1) + 1));
		if (randomNumber == 1) {
			return Choice.PLACE_A_CARD;
		} else {
			return Choice.END_THE_TURN;
		}
	}

	@Override
	public PlaceRequest makePlaceRequest(AbstractBoard board, Card victoryCard, List<Card> playerHand)
	{
		Card card;
		if (playerHand.size() == 1)
		{
			card = playerHand.get(0);
		} else
		{
			int randomCard = (int) (Math.random() * ((playerHand.size() - 1) + 1));
			card = playerHand.get(randomCard);
		}

		Coordinates randomCoord;
		List<Coordinates> coordsMap = new ArrayList<Coordinates>(board.getPlacedCards().keySet());
		if (coordsMap.isEmpty()) return new PlaceRequest(new Coordinates(0, 0), card);

		int randomX = (Coordinates.smallestAbscissa(coordsMap) - 1) + (int) (Math.random() * (((Coordinates.biggestAbscissa(coordsMap) + 1) - (Coordinates.smallestAbscissa(coordsMap) - 1)) + 1));
		int randomY = (Coordinates.smallestOrdinate(coordsMap) - 1) + (int) (Math.random() * (((Coordinates.biggestOrdinate(coordsMap) + 1) - (Coordinates.smallestOrdinate(coordsMap) - 1)) + 1));
		randomCoord = new Coordinates(randomX, randomY);

		return new PlaceRequest(randomCoord, card);
	}

	@Override
	public MoveRequest makeMoveRequest(AbstractBoard board, Card victoryCard, List<Card> playerHand)
	{
		List<Coordinates> coordsMap = new ArrayList<Coordinates>(board.getPlacedCards().keySet());

		Coordinates randomCoord1;
		do
		{
			int randomX = (Coordinates.smallestAbscissa(coordsMap)) + (int) (Math.random() * (((Coordinates.biggestAbscissa(coordsMap)) - (Coordinates.smallestAbscissa(coordsMap))) + 1));
			int randomY = (Coordinates.smallestOrdinate(coordsMap)) + (int) (Math.random() * (((Coordinates.biggestOrdinate(coordsMap)) - (Coordinates.smallestOrdinate(coordsMap))) + 1));
			randomCoord1 = new Coordinates(randomX, randomY);
		} while (board.getPlacedCards().get(randomCoord1) == null);

		int randomX2 = (Coordinates.smallestAbscissa(coordsMap) - 1) + (int) (Math.random() * (((Coordinates.biggestAbscissa(coordsMap) + 1) - (Coordinates.smallestAbscissa(coordsMap) - 1)) + 1));
		int randomY2 = (Coordinates.smallestOrdinate(coordsMap) - 1) + (int) (Math.random() * (((Coordinates.biggestOrdinate(coordsMap) + 1) - (Coordinates.smallestOrdinate(coordsMap) - 1)) + 1));
		Coordinates randomCoord2 = new Coordinates(randomX2, randomY2);

		return new MoveRequest(randomCoord1, randomCoord2);
	}


}
