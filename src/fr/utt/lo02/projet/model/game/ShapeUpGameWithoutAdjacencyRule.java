package fr.utt.lo02.projet.model.game;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.Card;
import fr.utt.lo02.projet.model.board.Coordinates;
import fr.utt.lo02.projet.model.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.model.player.MoveRequest;
import fr.utt.lo02.projet.model.player.PlaceRequest;
import fr.utt.lo02.projet.model.player.Player;

import java.util.List;

public class ShapeUpGameWithoutAdjacencyRule extends ShapeUpGame
{
	public ShapeUpGameWithoutAdjacencyRule(IBoardVisitor visitor, List<Player> players, AbstractBoard board)
	{
		super(visitor, players, board);
	}

	public PlaceRequestResult placeCardRequest(PlaceRequest placeRequest)
	{

		Card aCard = placeRequest.getCard();
		Coordinates coord = placeRequest.getCoordinates();

		if (!currentPlayer.getPlayerHand().contains(aCard))
		{

			return PlaceRequestResult.PLAYER_DOESNT_OWN_CARD;
		}

		boolean cardInTheLayout = board.isCardInTheLayout(coord);

		if (cardInTheLayout)
		{
			board.addCard(coord, aCard);
			currentPlayer.getPlayerHand().remove(aCard);
			this.isFirstTurn = false;
			return PlaceRequestResult.CORRECT_PLACEMENT;
		}
		return PlaceRequestResult.CARD_NOT_IN_THE_LAYOUT;
	}

	/**
	 * * Request to move a existing card from the board to another position
	 *
	 * @param moveRequest player request
	 * @return if the card has been moved or not
	 */
	public MoveRequestResult moveCardRequest(MoveRequest moveRequest)
	{

		Coordinates origin = moveRequest.getOrigin();
		Coordinates destination = moveRequest.getDestination();

		if (origin.equals(destination))
		{
			return MoveRequestResult.ORIGIN_AND_DESTINATION_ARE_EQUAL;
		}

		Card card = this.board.getPlacedCards().get(origin);

		if (card == null)
		{
			return MoveRequestResult.NO_CARD_IN_THE_ORIGIN_COORDINATE;
		}

		board.removeCard(origin, card);

		if (board.getPlacedCards().isEmpty())
		{
			board.addCard(destination, card);
			return MoveRequestResult.MOVE_VALID;
		}
		boolean cardInTheLayout = board.isCardInTheLayout(destination);

		if (cardInTheLayout)
		{
			board.addCard(destination, card);
			return MoveRequestResult.MOVE_VALID;
		}
		board.addCard(origin,card);

		return MoveRequestResult.CARD_NOT_IN_THE_LAYOUT;
	}
}
