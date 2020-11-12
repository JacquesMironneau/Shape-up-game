package fr.utt.lo02.projet.game;


import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.strategy.Choice;
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
	protected void initRound()
	{
		this.playerCards = new ArrayList<Set<Card>>();

		for (int i = 0; i < this.players.size(); i++)
		{
			this.playerCards.add(new HashSet<>());
		}

		this.deck = new LinkedList<>();
		initDeck();

		Collections.shuffle((LinkedList) this.deck);

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
			player.displayBoard();
			
			drawCard(this.players.indexOf(player));

			if (this.isFirstTurn)
			{
				player.askPlaceCard();
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
						player.askPlaceCard();

						for (PlayerStrategy p : players)
							p.displayBoard();
						
						Choice secondChoice = player.askChoice();
						if (secondChoice == Choice.MOVE_A_CARD)
						{
							player.askMoveCard();
						}
					}
					case MOVE_A_CARD -> {
						player.askMoveCard();
						for (PlayerStrategy p : players)
							p.displayBoard();
						player.askPlaceCard();
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
		return deck.isEmpty() && playerCards.isEmpty();
	}
}
