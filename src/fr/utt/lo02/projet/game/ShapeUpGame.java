package fr.utt.lo02.projet.game;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.strategy.Choice;
import fr.utt.lo02.projet.strategy.PlayerStrategy;
import fr.utt.lo02.projet.strategy.Request;

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
	protected void initRound()
	{
		this.playerCards = new ArrayList<Set<Card>>();

		for (int i = 0; i < this.players.size(); i++)
		{
			this.playerCards.add(new HashSet<>());
		}

		this.deck = new LinkedList<>();
		initDeck();

		Collections.shuffle((LinkedList<Card>) this.deck);

		// Remove hidden card
		this.deck.poll();
		// Draw victory cards to players
		for (PlayerStrategy player: players)
		{
			this.playerCards.get(this.players.indexOf(player)).add(this.deck.poll());
		}

		this.isFirstTurn = true;
	}
	@Override
	protected void playRound()
	{
		while (!isRoundFinished())
		{
			playTurn();
		}
		calculateRoundScore();
	}

	@Override
	protected void playTurn()
	{
		for (PlayerStrategy player : this.players)
		{
			Set<Card> playerHand = playerCards.get(this.players.indexOf(player));
			player.displayBoard();
			
			drawCard(this.players.indexOf(player));

			if (this.isFirstTurn)
			{
				player.askPlaceCard(playerHand, this.board);
				// The first turn is finished
				this.isFirstTurn = false;
			} 
			else
			{
				Choice choice;
				do {
					choice = player.askChoice();
				}
				while (choice == Choice.END_THE_TURN);

				switch (choice)
				{
					case PLACE_A_CARD -> {
						Request request;
						do
						{
							request= player.askPlaceCard(playerHand, this.board);
						}while (!placeCardRequest(request, player));

						for (PlayerStrategy p : players)
							p.displayBoard();
						
						Choice secondChoice = player.askChoice();

						if (secondChoice == Choice.MOVE_A_CARD)
						{
							do
							{
								request = player.askMoveCard(this.board);
							}while (!moveCardRequest(request));
						}
					}
					case MOVE_A_CARD -> {
						Request request = player.askMoveCard(this.board);
						moveCardRequest(request);

						for (PlayerStrategy p : players)
							p.displayBoard();

						request = player.askPlaceCard(playerHand, this.board);
						
						placeCardRequest(request, player);
					}
					default -> System.out.println("Error: end choice have been selected !!!");
				}

			}
			for (PlayerStrategy p: players)
				p.displayBoard();
		}
	}

	private boolean isRoundFinished()
	{
		for (Set<Card> s :playerCards)
		{
			if (!s.isEmpty()) return false;
		}
		return deck.isEmpty();
	}
}
