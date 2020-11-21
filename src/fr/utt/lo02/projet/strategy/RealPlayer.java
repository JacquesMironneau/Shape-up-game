package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.boardEmptyException;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represent the strategy for a real player.
 * It implements Player Strategy to follow the player's construction.
 *
 * @author Baptiste, Jacques
 */


public class RealPlayer implements PlayerStrategy
{

	private String name;
	private List<Card> playerHand;
	private List<Integer> scoresRound;
	private Card victoryCard;
	private AbstractBoard board;
	private Scanner scan;

	public RealPlayer(String name,AbstractBoard b)
	{
		this.name = name;
		this.board = b;
		this.scoresRound = new ArrayList<>();
		this.playerHand = new ArrayList<>();
		scan = new Scanner(System.in);
	}

	@Override
	public Choice askChoice(ChoiceOrder choiceNumber)
	{
		switch (choiceNumber)
		{
			case FIRST_CHOICE -> {
				System.out.println("Please choose one action : ");
				System.out.println("1. Move a Card");
				System.out.println("2. Place a Card");
				System.out.println("3. End the turn");

				int choice = scan.nextInt();

				return switch (choice)
						{
							case 1 -> Choice.MOVE_A_CARD;
							case 2 -> Choice.PLACE_A_CARD;
							default -> Choice.END_THE_TURN;
						};
			}
			case SECOND_CHOICE -> {
				System.out.println("Do you want to move a card ?");
				System.out.println("1. Yes (move a card)");
				System.out.println("2. No (End the turn)");

				int choice = scan.nextInt();
				if (choice == 1)
				{
					return Choice.MOVE_A_CARD;
				}
				return Choice.END_THE_TURN;

			}
			default -> throw new IllegalStateException("Unexpected value: " + choiceNumber);
		}
	}

	@Override
	public PlaceRequest askPlaceCard() throws PlayerHandEmptyException
	{
		if (playerHand.isEmpty()) throw new PlayerHandEmptyException();

		int choiceCard;
		if (playerHand.size() == 1)
		{
			choiceCard = 0;
		} else
		{
			System.out.println("Please choose one card from your Hand : ");
			for (int i = 0; i < playerHand.size(); i++)
			{
				System.out.println((i + 1) + ". " + playerHand.get(i));
			}
			choiceCard = scan.nextInt();
		}

		Card card = playerHand.get(choiceCard);

		int scanX, scanY;
		System.out.println("You have to enter coordinates for where you want to place this card. ");
		System.out.println("Please enter X pos");

		scanX = scan.nextInt();
		System.out.println("Please enter Y pos : ");
		scanY = scan.nextInt();

		Coordinates scanCoord = new Coordinates(scanX, scanY);
		PlaceRequest request = new PlaceRequest(scanCoord, card);
		return request;
	}

	@Override
	public MoveRequest askMoveCard() throws boardEmptyException
	{
		if (board.getPlacedCards().isEmpty()) throw new boardEmptyException();

		List<Coordinates> coordsMap = new ArrayList<Coordinates>();
		int i = 0;
		for (Map.Entry<Coordinates, Card> entry : board.getPlacedCards().entrySet())
		{
			coordsMap.add(i, entry.getKey());
			i++;
		}
		int scanX1, scanY1;
		Coordinates scanCoord1;
		do
		{

			System.out.println("You have to enter coordinates of the card you want to move. ");
			System.out.println("Please enter X pos : ");
			scanX1 = scan.nextInt();
			System.out.println("Please enter Y pos : ");
			scanY1 = scan.nextInt();

			scanCoord1 = new Coordinates(scanX1, scanY1);
		} while (board.getPlacedCards().get(scanCoord1) == null);

		int scanX2, scanY2;

		System.out.println("You have to enter coordinates for where you want to move this card. ");
		System.out.println("Please enter X pos : ");
		scanX2 = scan.nextInt();
		System.out.println("Please enter Y pos : ");
		scanY2 = scan.nextInt();

		Coordinates scanCoord2 = new Coordinates(scanX2, scanY2);

		MoveRequest request = new MoveRequest(scanCoord1, scanCoord2);
		return request;
	}

	@Override
	public void displayRoundScore()
	{
		int score = scoresRound.get(scoresRound.size()-1);
		System.out.println(name + "Score : "+ score);
	}

	@Override
	public void displayFinalScore() {
		int roundNumber=1;
		int finalScore=0;
		for (int scores: scoresRound) {
			System.out.println(name + " : Score for Round " + roundNumber + " -> " + scores);
			roundNumber++;
			finalScore += scores;
		}
		System.out.println("Player " + name + " : FINAL SCORE = " + finalScore);
	}

	@Override
	public void setVictoryCard(Card victoryCard)
	{
		this.victoryCard = victoryCard;
	}

	@Override
	public void drawCard(Card card)
	{
		this.playerHand.add(card);
	}

	@Override
	public void addRoundScore(int scoreOfCurrentRound)
	{
		this.scoresRound.add(scoreOfCurrentRound);
	}

	@Override
	public Card getVictoryCard()
	{
		return victoryCard;
	}

	public List<Card> getPlayerHand()
	{
		return playerHand;
	}

	public String getName()
	{
		return name;
	}
}
