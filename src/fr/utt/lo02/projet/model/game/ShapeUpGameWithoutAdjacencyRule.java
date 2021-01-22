package fr.utt.lo02.projet.model.game;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.Card;
import fr.utt.lo02.projet.model.board.Coordinates;
import fr.utt.lo02.projet.model.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.model.player.MoveRequest;
import fr.utt.lo02.projet.model.player.PlaceRequest;
import fr.utt.lo02.projet.model.player.Player;

import java.util.List;

/**
 * Represents the no adjacency mode of the game. It's the same as normal mode but without adjacency rule. It extends ShapeUpGame to follow the normal mode game's construction.
 * @author Baptiste, Jacques
 *
 */
public class ShapeUpGameWithoutAdjacencyRule extends ShapeUpGame
{
	/**
	 * Game's constructor. Sets up parameters.
	 * @param visitor the game's visitor. Used to calculate the score.
	 * @param players the player's list. The list can contain 2 or 3 players.
	 * @param board the game's board. It can be a rectangular, triangular or circular board.
	 */
	public ShapeUpGameWithoutAdjacencyRule(IBoardVisitor visitor, List<Player> players, AbstractBoard board)
	{
		super(visitor, players, board);
	}

	/**
	 * Request to place a card from the player hand to a position on the board. We remove adjacency verification from the normal mode. 
	 * 
	 * @param placeRequest place request from one player
	 * @return if the card has been placed or not
	 */
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
	 * Request to move an existing card from the board to another position. We remove adjacency verification from the normal mode. 
	 *
	 * @param moveRequest player request from one player
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
