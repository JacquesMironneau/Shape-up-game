package fr.utt.lo02.projet.model.strategy;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.Card;
import fr.utt.lo02.projet.model.board.Coordinates;
import fr.utt.lo02.projet.model.player.Choice;
import fr.utt.lo02.projet.model.player.MoveRequest;
import fr.utt.lo02.projet.model.player.PlaceRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a random strategy. As its name indicates, it takes random decisions.
 * It implements PlayerStrategy to follows the strategy's construction.
 * @author Baptiste, Jacques
 *
 */
public class RandomStrategy implements PlayerStrategy
{
	/**
	 * Strategy's constructor. Don't do anything particular for now.
	 */
	public RandomStrategy() {
	
	}
	
	/**
	 * Chooses one option to play a turn (place then end turn, place then move, move then place).
	 * 
	 * @param board the board of the round.
	 * @param victoryCard the victory card of the player.
	 * @param playerHand the hand of the player.
	 * @return the choice of the virtual player.
	 */
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

	/**
	 * Makes a place request on the board.
	 * 
	 * @param board the board of the round.
	 * @param victoryCard the victory card of the player.
	 * @param playerHand the hand of the player.
	 * @return return a place request.
	 */
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
		List<Coordinates> coordsMap = new ArrayList<>(board.getPlacedCards().keySet());
		if (coordsMap.isEmpty()) return new PlaceRequest(new Coordinates(0, 0), card);

		int randomX = (Coordinates.smallestAbscissa(coordsMap) - 1) + (int) (Math.random() * (((Coordinates.biggestAbscissa(coordsMap) + 1) - (Coordinates.smallestAbscissa(coordsMap) - 1)) + 1));
		int randomY = (Coordinates.smallestOrdinate(coordsMap) - 1) + (int) (Math.random() * (((Coordinates.biggestOrdinate(coordsMap) + 1) - (Coordinates.smallestOrdinate(coordsMap) - 1)) + 1));
		randomCoord = new Coordinates(randomX, randomY);

		return new PlaceRequest(randomCoord, card);
	}

	/**
	 * Makes a move request from an placed card to an empty case on the board
	 * 
	 * @param board the board of the round.
	 * @param victoryCard the victory card of the player.
	 * @param playerHand the hand of the player.
	 * @return return a move request.
	 */
	@Override
	public MoveRequest makeMoveRequest(AbstractBoard board, Card victoryCard, List<Card> playerHand)
	{
		List<Coordinates> coordsMap = new ArrayList<>(board.getPlacedCards().keySet());

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
