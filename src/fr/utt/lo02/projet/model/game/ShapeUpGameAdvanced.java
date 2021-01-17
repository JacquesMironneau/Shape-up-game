package fr.utt.lo02.projet.model.game;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.BoardEmptyException;
import fr.utt.lo02.projet.model.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.model.player.*;

import java.util.List;

/**
 *  Represents the advanced mode of the game. It extends AbstractShapeUpGame to follow the game's construction.
 * @author Baptiste, Jacques
 *
 */
public class ShapeUpGameAdvanced extends AbstractShapeUpGame
{

	public ShapeUpGameAdvanced(IBoardVisitor visitor, List<Player> players, AbstractBoard board)
	{
		super(visitor, players, board);
	}

	/**
	 * Initiates a round. In the advanced mode, we don't set victory cards at the start of the game but we give 3 cards to each player.
	 */
	@Override
	public void initRound()
	{
		super.initRound();

		// Draw cards to players
		for (Player player : players)
		{
			player.getPlayerHand().add(this.deck.poll());
			player.getPlayerHand().add(this.deck.poll());
			player.getPlayerHand().add(this.deck.poll());
		}

	}

	/**
	 * Makes play one turn to the current player.
	 */
	@Override
	public void playTurn() throws PlayerHandEmptyException, BoardEmptyException
	{

		if (this.isFirstTurn)
		{
			PlaceRequest placeRequest;
			do
			{
				placeRequest = currentPlayer.askPlaceCard();
			} while (PlaceRequestResult.CORRECT_PLACEMENT != placeCardRequest(placeRequest));
			setState(GameState.PLACE_DONE);


		} else
		{
			Choice choice;
			do
			{
				choice = currentPlayer.askChoice(ChoiceOrder.FIRST_CHOICE);
			}
			while (choice == Choice.END_THE_TURN);

//			setState(GameState.PLACE_DONE);

			switch (choice)
			{
				case PLACE_A_CARD -> {
					PlaceRequest placeRequest;
					do
					{
						placeRequest = currentPlayer.askPlaceCard();
					} while (PlaceRequestResult.CORRECT_PLACEMENT != placeCardRequest(placeRequest));

					setState(GameState.PLACE_DONE);

					if (isRoundFinished())
					{
						setState(GameState.END_ROUND);
						return;
					}
					Choice secondChoice = currentPlayer.askChoice(ChoiceOrder.SECOND_CHOICE);

					if (secondChoice == Choice.MOVE_A_CARD)
					{
						MoveRequest request;
						do
						{
							request = currentPlayer.askMoveCard();
						} while (moveCardRequest(request) != MoveRequestResult.MOVE_VALID);
						setState(GameState.MOVE_DONE);

					}
				}
				case MOVE_A_CARD -> {
					MoveRequest request;
					do
					{
						request = currentPlayer.askMoveCard();
					} while (moveCardRequest(request) != MoveRequestResult.MOVE_VALID);

					setState(GameState.MOVE_DONE);

					PlaceRequest placeRequest;
					do
					{
						placeRequest = currentPlayer.askPlaceCard();
					} while (PlaceRequestResult.CORRECT_PLACEMENT != placeCardRequest(placeRequest));
					setState(GameState.PLACE_DONE);

				}
				default -> System.out.println("Error: end choice have been selected !");
			}

		}
		drawCard();


		setState(GameState.END_TURN);

	}

	/**
	 * Allows to know if the round is finished or not. A round is finished when the deck is empty and all players' hands have only 1 card.
	 * The last card of each player is their victory card.
	 */
	@Override
	public boolean isRoundFinished()
	{
		if (!deck.isEmpty()) return false;

		for (Player player : players)
		{
			if ((player.getPlayerHand().size() > 1))
			{
				return false;
			}
		}

		for (Player player : players)
		{
			player.setVictoryCard(player.getPlayerHand().get(0));
		}

		return true;
	}
}
