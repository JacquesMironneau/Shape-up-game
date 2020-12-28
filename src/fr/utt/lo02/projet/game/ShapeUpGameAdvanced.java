package fr.utt.lo02.projet.game;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.BoardEmptyException;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.strategy.*;

import java.util.List;

public class ShapeUpGameAdvanced extends AbstractShapeUpGame
{

	public ShapeUpGameAdvanced(IBoardVisitor visitor, List<Player> players, AbstractBoard board)
	{
		super(visitor, players, board);
	}


	@Override
	protected void initRound()
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

		} else
		{
			Choice choice;
			do
			{
				choice = currentPlayer.askChoice(ChoiceOrder.FIRST_CHOICE);
			}
			while (choice == Choice.END_THE_TURN);

			setState(GameState.PLACE_DONE);

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
						System.out.println("might aleeeeeeeeeeeeed");
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

	@Override
	protected boolean isRoundFinished()
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
