package fr.utt.lo02.projet.model.game;


import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.BoardEmptyException;
import fr.utt.lo02.projet.model.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.model.player.*;

import java.util.List;

/**
 * Represents the normal mode of the game. It extends AbstractShapeUpGame to follow the game's construction.
 * @author Baptiste, Jacques
 *
 */
public class ShapeUpGame extends AbstractShapeUpGame
{

	/**
	 * Game's constructor. Sets up parameters.
	 * @param visitor the game's visitor. Used to calculate the score.
	 * @param players the player's list. The list can contain 2 or 3 players.
	 * @param board the game's board. It can be a rectangular, triangular or circular board.
	 */
	public ShapeUpGame(IBoardVisitor visitor, List<Player> players, AbstractBoard board)
	{
		super(visitor, players, board);
	}

	/**
	 * Initiates a round. In the normal mode, we set victory cards at the start of the round.
	 */
	@Override
	public void initRound()
	{
		super.initRound();

		// Draw victory cards to players
		for (Player player : players)
		{
			player.setVictoryCard(this.deck.poll());
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

			// The first turn is finished

			setState(GameState.PLACE_DONE);
		} else
		{
			Choice choice;
			do
			{
				choice = currentPlayer.askChoice(ChoiceOrder.FIRST_CHOICE);
			}
			while (choice == Choice.END_THE_TURN);

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
		setState(GameState.END_TURN);


	}


	/**
	 * Allows to know if the round is finished or not. A round is finished when the deck is empty and all players' hands are empty too.
	 */
	@Override
	public boolean isRoundFinished()
	{
		for (Player player : players)
		{
			if (!player.getPlayerHand().isEmpty()) return false;
		}
		return deck.isEmpty();
	}


}
