package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.boardEmptyException;

import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;

/**
 * Represent the strategy for a virtual player.
 * It implements Player Strategy to follow the player's construction.
 *
 * @author Baptiste, Jacques
 */

public class VirtualPlayer extends PlayerStrategy
{

	public VirtualPlayer(String name, AbstractBoard b)
	{
		super(name, b);
	}

	@Override
	public Choice askChoice(ChoiceOrder choiceNumber)
	{
		int randomNumber = 1 + (int) (Math.random() * ((3 - 1) + 1));
		if (randomNumber == 1) {
			return Choice.MOVE_A_CARD;
		} else if (randomNumber == 2) {
			return Choice.PLACE_A_CARD;
		} else {
			return Choice.END_THE_TURN;
		}
	}

	@Override
	public PlaceRequest askPlaceCard() throws PlayerHandEmptyException
	{
		if (playerHand.isEmpty()) throw new PlayerHandEmptyException();

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
	public MoveRequest askMoveCard() throws boardEmptyException
	{
		if (board.getPlacedCards().isEmpty()) throw new boardEmptyException();

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

	@Override
	public void MoveResult(MoveRequestResult result)
	{
		if (result == MoveRequestResult.MOVE_VALID)
		{
			System.out.println(colorize("The card has been moved", GREEN_TEXT()));
		}
	}

	@Override
	public void PlaceResult(PlaceRequestResult result)
	{

		if (result == PlaceRequestResult.CORRECT_PLACEMENT)
		{
			System.out.println(colorize("The card has been placed", GREEN_TEXT()));
		}
	}

}
