package fr.utt.lo02.projet.model.game;


import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.BoardEmptyException;
import fr.utt.lo02.projet.model.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.model.strategy.*;

import java.util.List;

public class ShapeUpGame extends AbstractShapeUpGame
{

	public ShapeUpGame(IBoardVisitor visitor, List<Player> players, AbstractBoard board)
	{
		super(visitor, players, board);
	}

	/**
	 * Initiate a round
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
