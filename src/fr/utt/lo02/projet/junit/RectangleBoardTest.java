package fr.utt.lo02.projet.junit;

import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.RectangleBoard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static fr.utt.lo02.projet.junit.ShapeUpGameTest.ANSI_BLUE;
import static fr.utt.lo02.projet.junit.ShapeUpGameTest.ANSI_RESET;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Case 1: the board has 2 row and 4 columns
 * Case 2: the board has 2 column and 5 rows
 * Case 3: the board has 3 columns and 3 rows
 * Case 4: the board has 2 columns and 2 rows
 */
class RectangleBoardTest
{

	private static RectangleBoard board;

	@Test
	@BeforeAll
	static void init()
	{
		board = new RectangleBoard();
		board.getPlacedCards().put(new Coordinates(1, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(2, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(3, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));

	}


	@Test
	void testAdjacency()
	{
		board.getPlacedCards().clear();
		board.getPlacedCards().put(new Coordinates(1, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(2, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(3, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));

		assertTrue(board.isCardAdjacent(new Coordinates(3, 2)));
		assertTrue(board.isCardAdjacent(new Coordinates(3, 1)));

		assertTrue(board.isCardAdjacent(new Coordinates(2, 2)));
		assertTrue(board.isCardAdjacent(new Coordinates(1, 2)));
		assertTrue(board.isCardAdjacent(new Coordinates(1, 1))); // Une carte existante est effectivement adjacente


	}

	@Test
	void testAlreadyExistingCard()
	{
		board.getPlacedCards().forEach((key, value) -> assertFalse(board.isCardInTheLayout(key)));
	}

	@Test
	void testImpossibleLayoutConfiguration()
	{
		board.getPlacedCards().put(new Coordinates(1, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));

		assertFalse(board.isCardInTheLayout(new Coordinates(50, 60)));
		assertFalse(board.isCardInTheLayout(new Coordinates(6, 1)));
		assertFalse(board.isCardInTheLayout(new Coordinates(1, 6)));
	}

	@Test
	void testCase1()
	{
		initCase1();


		for (int index = 0; index <= 4; index += 3)
		{
			for (int i = 0; i <= 5; ++i)
			{
				assertTrue(board.isCardInTheLayout(new Coordinates(i, index)));
			}
		}

		for (int i = 0; i < 3; ++i)
		{
			assertFalse(board.isCardInTheLayout(new Coordinates(6, i)));
		}

		for (int i = 0; i < 6; ++i)
		{
			assertFalse(board.isCardInTheLayout(new Coordinates(i, 4)));
			assertFalse(board.isCardInTheLayout(new Coordinates(i, -1)));

		}
	}

	private void initCase1()
	{
		board.getPlacedCards().clear();

		for (int j = 1; j < 5; j++)
		{
			board.getPlacedCards().put(new Coordinates(j, 2), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
		}

		for (int j = 1; j < 4; j++)
		{
			board.getPlacedCards().put(new Coordinates(j, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
		}
	}

	@Test
	void testCase2()
	{
		board.getPlacedCards().clear();
		IntStream.iterate(5, i -> i > 0, i -> i - 1).forEach(i -> board.getPlacedCards().put(new Coordinates(1, i), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED)));
		IntStream.range(1, 3).forEach(i -> board.getPlacedCards().put(new Coordinates(2, i), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED)));


		for (int index = 0; index <= 4; index += 3)
		{
			for (int i = 1; i <= 5; ++i)
			{
				assertTrue(board.isCardInTheLayout(new Coordinates(index, i)));
			}
		}

		for (int i = 0; i < 6; ++i)
		{
			assertFalse(board.isCardInTheLayout(new Coordinates(-1, i)));
		}

		for (int i = 0; i < 6; ++i)
		{
			assertFalse(board.isCardInTheLayout(new Coordinates(i, 6)));
			assertFalse(board.isCardInTheLayout(new Coordinates(4, i)));

		}

	}

	@Test
	void testCase3()
	{
		board.getPlacedCards().clear();

		for (int i = -1; i < 2; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				board.getPlacedCards().put(new Coordinates(i, j), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
			}
		}

		for (int i = -3; i <= -2; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				assertTrue(board.isCardInTheLayout(new Coordinates(i,j)));

			}
		}

		for (int i = 2; i <= 3; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				assertTrue(board.isCardInTheLayout(new Coordinates(i,j)));

			}
		}

		for (int i = 2; i <= 3; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				assertTrue(board.isCardInTheLayout(new Coordinates(i,j)));

			}
		}

		for (int i = -3; i <= -2; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				assertTrue(board.isCardInTheLayout(new Coordinates(j,i)));
			}
		}

		assertFalse(board.isCardInTheLayout(new Coordinates(2, 2)));
		assertFalse(board.isCardInTheLayout(new Coordinates(2, 3)));


	}

	@Test
	void testCase4()
	{
		board.getPlacedCards().clear();
		board.getPlacedCards().put(new Coordinates(0, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(0, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));

		for (int i = -3; i < 0; i++)
		{
			for (int j = -1; j < 3; j++)
			{
				assertTrue(board.isCardInTheLayout(new Coordinates(i,j)));
			}
		}

		for (int i = 2; i < 5; i++)
		{
			for (int j = -1; j < 3; j++)
			{
				assertTrue(board.isCardInTheLayout(new Coordinates(i,j)));
			}
		}

		for (int i = 2; i < 5; i++)
		{
			for (int j = -1; j < 3; j++)
			{
				assertTrue(board.isCardInTheLayout(new Coordinates(j, i)));
			}
		}

		for (int i = -3; i < 0; i++)
		{
			for (int j = -1; j < 3; j++)
			{
				assertTrue(board.isCardInTheLayout(new Coordinates(j, i)));
			}
		}

		assertFalse(board.isCardInTheLayout(new Coordinates(3, 3)));
		assertFalse(board.isCardInTheLayout(new Coordinates(3, 4)));
		assertFalse(board.isCardInTheLayout(new Coordinates(4, 3)));
		assertFalse(board.isCardInTheLayout(new Coordinates(-2, -2)));
		assertFalse(board.isCardInTheLayout(new Coordinates(2, 5)));
		assertFalse(board.isCardInTheLayout(new Coordinates(5, 2)));

	}

	@Test
	void emptyMap()
	{
		String ANSI_RED = "\u001B[31m";
		String ANSI_GREEN = "\u001B[32m";
		String ANSI_WHITE_BACKGROUND = "\u001B[47m";
		System.out.print(ANSI_BLUE + "⭕" + ANSI_RESET);
		System.out.print(ANSI_BLUE + "⬤" + ANSI_RESET);
		System.out.print(ANSI_RED + "⭕" + ANSI_RESET);
		System.out.print(ANSI_RED + "⬤" + ANSI_RESET);
		System.out.print(ANSI_GREEN + "⭕" + ANSI_RESET);
		System.out.print(ANSI_GREEN + "⬤" + ANSI_RESET);
		System.out.print(ANSI_BLUE + "⬜" + ANSI_RESET);
		System.out.print(ANSI_BLUE + "⬛" + ANSI_RESET);
		System.out.print(ANSI_RED + "⬜" + ANSI_RESET);
		System.out.print(ANSI_RED + "⬛" + ANSI_RESET);
		System.out.print(ANSI_GREEN + "⬜" + ANSI_RESET);
		System.out.print(ANSI_GREEN + "⬛" + ANSI_RESET);

		System.out.print(ANSI_BLUE + "△" + ANSI_RESET);
		System.out.print(ANSI_BLUE + "◄" + ANSI_RESET);
		System.out.print(ANSI_RED + "△" + ANSI_RESET);
		System.out.print(ANSI_RED + "▲" + ANSI_RESET);
		System.out.print(ANSI_GREEN + "△" + ANSI_RESET);
		System.out.print(ANSI_GREEN + "▲" + ANSI_RESET);




		board.getPlacedCards().clear();
		assertTrue(board.isCardInTheLayout(new Coordinates(0, 0)));
	}

}