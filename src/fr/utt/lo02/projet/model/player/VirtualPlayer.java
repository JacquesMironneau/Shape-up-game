package fr.utt.lo02.projet.model.player;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.BoardEmptyException;
import fr.utt.lo02.projet.model.game.ChoiceOrder;
import fr.utt.lo02.projet.model.strategy.*;

/**
 * Represent a virtual player (It uses a predefined strategy).
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
		return strategy.makeChoice(super.board, super.getVictoryCard(), super.getPlayerHand()) ;
	}

	@Override
	public PlaceRequest askPlaceCard() throws PlayerHandEmptyException
	{
		if (playerHand.isEmpty()) throw new PlayerHandEmptyException();
		return this.strategy.makePlaceRequest(super.board, super.getVictoryCard(), super.getPlayerHand());
	}

	@Override
	public MoveRequest askMoveCard() throws BoardEmptyException
	{
		if (board.getPlacedCards().isEmpty()) throw new BoardEmptyException();
		return this.strategy.makeMoveRequest(board, super.getVictoryCard(), super.getPlayerHand());

	}
}