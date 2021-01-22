package fr.utt.lo02.projet.model.player;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.BoardEmptyException;
import fr.utt.lo02.projet.model.game.ChoiceOrder;
import fr.utt.lo02.projet.model.strategy.*;

/**
 * Represents a virtual player (It uses a predefined strategy: random or difficult).
 *	It extends Player to follow the player's construction. 
 *
 * @author Baptiste, Jacques
 */
public class VirtualPlayer extends Player
{

	/**
	 * The strategy of the player.
	 */
	private PlayerStrategy strategy;

	/**
	 * * Player's constructor. Sets up parameters.
	 * @param name the player's name.
	 * @param b the game's board.
	 * @param strategy the virtual player's strategy. Can be Easy or Medium.
	 */
	public VirtualPlayer(String name, AbstractBoard b, PlayerStrategy strategy)
	{
		super(name, b);
		this.strategy = strategy;
	}

	/**
	 * Ask the player if he wants to place or move a card, or end his turn.
	 * @param choiceNumber player's choice.
	 */
	@Override
	public Choice askChoice(ChoiceOrder choiceNumber)
	{
		return strategy.makeChoice(super.board, super.getVictoryCard(), super.getPlayerHand()) ;
	}

	/**
	 * Ask a player to place a card
	 * So pick one card and select where he wants to put it.
	 */
	@Override
	public PlaceRequest askPlaceCard() throws PlayerHandEmptyException
	{
		if (playerHand.isEmpty()) throw new PlayerHandEmptyException();
		return this.strategy.makePlaceRequest(super.board, super.getVictoryCard(), super.getPlayerHand());
	}

	/**
	 * Ask a player to move a card.
	 * So pick one card and select where he wants to move it.
	 */
	@Override
	public MoveRequest askMoveCard() throws BoardEmptyException
	{
		if (board.getPlacedCards().isEmpty()) throw new BoardEmptyException();
		return this.strategy.makeMoveRequest(board, super.getVictoryCard(), super.getPlayerHand());

	}
}
