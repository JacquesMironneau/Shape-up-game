package fr.utt.lo02.projet.game;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.strategy.MoveRequest;
import fr.utt.lo02.projet.strategy.PlaceRequest;
import fr.utt.lo02.projet.strategy.PlayerStrategy;

import java.util.List;

public class ShapeUpGameWithoutAdajencyRule extends ShapeUpGame
{

	public ShapeUpGameWithoutAdajencyRule(IBoardVisitor visitor, List<PlayerStrategy> players, AbstractBoard board)
	{
		super(visitor, players, board);
	}

	/**
	 * Request to place a card from the player hand to a position on the board without checking adjacency
	 *
	 * @param placeRequest Which card and where the player want to put it
	 * @param player       the player that is making the request
	 * @return if the request is matching the game rules
	 */
	@Override
	public boolean placeCardRequest(PlaceRequest placeRequest, PlayerStrategy player)
	{
		Card aCard = placeRequest.getCard();
		Coordinates coord = placeRequest.getCoordinates();

		if (!player.getPlayerHand().contains(aCard)) return false;

		boolean cardInTheLayout = board.isCardInTheLayout(coord);

		if (cardInTheLayout)
		{
			board.addCard(coord, aCard);
			player.getPlayerHand().remove(aCard);
			System.out.println("[LOG] "+ aCard + " has been placed at " + coord);

			return true;
		}
		System.out.println("Please choose a correct location");
		return false;
	}
	/**
	 * * Request to move a existing card from the board to another position
	 *
	 * @param moveRequest player request
	 * @return if the card has been moved or not
	 */
	@Override
	public boolean moveCardRequest(MoveRequest moveRequest)
	{
		Coordinates origin = moveRequest.getOrigin();
		Coordinates destination = moveRequest.getDestination();

		if (origin.equals(destination)) return false;

		Card card = this.board.getPlacedCards().get(origin);

		if (card == null) return false;

		board.removeCard(origin, card);

		if (board.getPlacedCards().isEmpty())
		{
			board.addCard(destination, card);
			System.out.println("[LOG] " + card + " has been moved from" + origin + "to "+ destination);
			return true;
		}
		boolean cardInTheLayout = board.isCardInTheLayout(destination);

		if (cardInTheLayout)
		{
			board.addCard(destination, card);
			System.out.println("[LOG] " + card + " has been moved from" + origin + "to "+ destination);
			return true;
		}
		board.addCard(origin,card);

		return false;
	}
}
