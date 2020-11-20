package fr.utt.lo02.projet.game;


import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.strategy.Choice;
import fr.utt.lo02.projet.strategy.MoveRequest;
import fr.utt.lo02.projet.strategy.PlaceRequest;
import fr.utt.lo02.projet.strategy.PlayerStrategy;

import java.util.*;

public class ShapeUpGame extends AbstractShapeUpGame
{

	public ShapeUpGame(IBoardVisitor visitor, List<PlayerStrategy> players, AbstractBoard board)
	{
		super(visitor, players, board);
	}

	/**
	 * Initiate a round
	 */
	@Override
	public void initRound()
	{
		for (PlayerStrategy ps : players)
		{
			List<Card> list = ps.getPlayerHand();
			list = new ArrayList<>();
		}

		this.deck = new LinkedList<>();
		initDeck();

		Collections.shuffle((LinkedList<Card>) this.deck);

		// Remove hidden card
		this.deck.poll();
		// Draw victory cards to players
		for (PlayerStrategy player : players)
		{
			player.setVictoryCard(this.deck.poll());
		}

		this.isFirstTurn = true;
	}

	@Override
	protected void playRound()
	{
		PlayerStrategy player = players.get(0);

		while (!isRoundFinished())
		{
			playTurn(player);
			player = nextPlayer(player);
		}
		calculateRoundScore();
	}

	@Override
	protected void playTurn(PlayerStrategy player)
	{
		drawCard(player);
		board.display();


		if (this.isFirstTurn)
		{
			PlaceRequest placeRequest;
			do
			{
				placeRequest = player.askPlaceCard();
			} while (!placeCardRequest(placeRequest, player));

			// The first turn is finished
			this.isFirstTurn = false;
		} else
		{
			Choice choice;
			do
			{
				choice = player.askChoice();
			}
			while (choice == Choice.END_THE_TURN);

			switch (choice)
			{
				case PLACE_A_CARD -> {
					PlaceRequest placeRequest;
					do
					{
						placeRequest = player.askPlaceCard();
					} while (!placeCardRequest(placeRequest, player));

					board.display();

					Choice secondChoice = player.askChoice();

					if (secondChoice == Choice.MOVE_A_CARD)
					{
						MoveRequest request;
						do
						{
							request = player.askMoveCard();
						} while (!moveCardRequest(request));
					}
				}
				case MOVE_A_CARD -> {
					MoveRequest moveRequest = player.askMoveCard();
					moveCardRequest(moveRequest);

					board.display();

					PlaceRequest placeRequest = player.askPlaceCard();

					placeCardRequest(placeRequest, player);
				}
				default -> System.out.println("Error: end choice have been selected !!!");
			}

		}
		board.display();

	}

	private boolean isRoundFinished()
	{
		for (PlayerStrategy player : players)
		{
			if (!player.getPlayerHand().isEmpty()) return false;
		}
		return deck.isEmpty();
	}

	private PlayerStrategy nextPlayer(PlayerStrategy player)
	{
		return players.get(players.indexOf(player) + 1 % this.players.size());
	}
}
