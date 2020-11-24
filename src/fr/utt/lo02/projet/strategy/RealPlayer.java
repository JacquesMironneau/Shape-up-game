package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.boardEmptyException;

import java.util.Scanner;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;

/**
 * Represent the strategy for a real player.
 * It implements Player Strategy to follow the player's construction.
 *
 * @author Baptiste, Jacques
 */


public class RealPlayer extends PlayerStrategy
{

	private final Scanner scan;

	public RealPlayer(String name, AbstractBoard b)
	{
		super(name, b);
		scan = new Scanner(System.in);
	}

	@Override
	public Choice askChoice(ChoiceOrder choiceNumber)
	{
		switch (choiceNumber)
		{
			case FIRST_CHOICE -> {
				int choice = getNumber("Please choose one action : \n 1. Move a card \n 2. Place a Card \n 3. End the turn");

				return switch (choice)
						{
							case 1 -> Choice.MOVE_A_CARD;
							case 2 -> Choice.PLACE_A_CARD;
							default -> Choice.END_THE_TURN;
						};
			}
			case SECOND_CHOICE -> {

				int choice = getNumber("Do you want to move a card ?\n 1. Yes (move a card)\n 2. No (end the turn)");
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
			for (int i = 0; i < playerHand.size(); i++)
			{
				System.out.println((i + 1) + ". ");
				Card.printSingleCard(playerHand.get(i));
			}
			choiceCard = getNumber("Please choose one card from your Hand :");
			choiceCard--;
		}

		Card card = playerHand.get(choiceCard);

		int scanX, scanY;
		System.out.println("You have to enter coordinates for where you want to place the card you draw. ");

		scanX = getNumber("Please enter X pos : ");
		scanY = getNumber("Please enter Y pos : ");
		Coordinates scanCoord = new Coordinates(scanX, scanY);
		return new PlaceRequest(scanCoord, card);
	}

	@Override
	public MoveRequest askMoveCard() throws boardEmptyException
	{
		if (board.getPlacedCards().isEmpty()) throw new boardEmptyException();

		int scanX1, scanY1;
		Coordinates scanCoord1;
		do
		{

			System.out.println("You have to enter coordinates of the card you want to move. ");
			scanX1 = getNumber("Please enter X pos : ");
			scanY1 = getNumber("Please enter Y pos : ");

			scanCoord1 = new Coordinates(scanX1, scanY1);
		} while (board.getPlacedCards().get(scanCoord1) == null);

		int scanX2, scanY2;

		System.out.println("You have to enter coordinates for where you want to move the card you choose. ");
		scanX2 = getNumber("Please enter X pos : ");
		scanY2 = getNumber("Please enter Y pos : ");

		Coordinates scanCoord2 = new Coordinates(scanX2, scanY2);

		return new MoveRequest(scanCoord1, scanCoord2);

	}

	@Override
	public void MoveResult(MoveRequestResult result)
	{
		switch (result)
		{
			case NO_CARD_IN_THE_ORIGIN_COORDINATE -> System.err.println("There's no card at the origin coordinate");
			case CARD_NOT_ADJACENT -> System.err.println("There's no card near the destination coordinate");
			case CARD_NOT_IN_THE_LAYOUT -> System.err.println("The destination is not included in the board layout");
			case ORIGIN_AND_DESTINATION_ARE_EQUAL -> System.err.println("You are trying to move this card to its current position");
			case MOVE_VALID -> System.out.println(colorize("The card has been moved", GREEN_TEXT()));
		}
	}

	@Override
	public void PlaceResult(PlaceRequestResult result)
	{
		switch (result)
		{
			case PLAYER_DOESNT_OWN_CARD -> System.err.println("This card is not in your hand");
			case CARD_NOT_ADJACENT -> System.err.println("You can't place a card here, no card are adjacent");
			case CARD_NOT_IN_THE_LAYOUT -> System.err.println("You can't place a card here, it is not in the layout");
			case CORRECT_PLACEMENT -> System.out.println(colorize("The card has been placed", GREEN_TEXT()));
		}
	}

	private int getNumber(String msg)
	{
		int choice = -1;

		while (choice == -1)
		{
			System.out.println(msg);

			try {
				choice= scan.nextInt();
			} catch (Exception exception)
			{
				choice= -1;
			}
			scan.nextLine();
		}

		return choice;

	}
}
