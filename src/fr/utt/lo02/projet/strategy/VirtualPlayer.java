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

public class VirtualPlayer extends Player
{

	private PlayerStrategy strategy;

	public VirtualPlayer(String name, AbstractBoard b, PlayerStrategy strategy)
	{
		super(name, b);
		this.strategy = strategy;
	}

	@Override
	public Choice askChoice(ChoiceOrder choiceNumber)
	{
		return strategy.makeChoice();
	}

	@Override
	public PlaceRequest askPlaceCard() throws PlayerHandEmptyException
	{
		if (playerHand.isEmpty()) throw new PlayerHandEmptyException();
		return this.strategy.makePlaceRequest(super.board, super.playerHand);
	}

	@Override
	public MoveRequest askMoveCard() throws boardEmptyException
	{
		if (board.getPlacedCards().isEmpty()) throw new boardEmptyException();
		return this.strategy.makeMoveRequest(board);
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
