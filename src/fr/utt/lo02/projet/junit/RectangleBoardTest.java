package fr.utt.lo02.projet.junit;

import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.RectangleBoard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

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
		board.getPlacedCards().put(new Coordinates(1, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED));
		board.getPlacedCards().put(new Coordinates(2, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED));
		board.getPlacedCards().put(new Coordinates(3, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED));

	}


	@Test
	void testAdjacency()
	{
		board.getPlacedCards().clear();
		board.getPlacedCards().put(new Coordinates(1, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED));
		board.getPlacedCards().put(new Coordinates(2, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED));
		board.getPlacedCards().put(new Coordinates(3, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED));

		assertTrue(board.isCardAdjacent(3, 2));
		assertTrue(board.isCardAdjacent(3, 1));
		assertTrue(board.isCardAdjacent(2, 2));
		assertTrue(board.isCardAdjacent(1, 2));
		assertTrue(board.isCardAdjacent(1, 1)); // Une carte existante est effectivement adjacente


	}

	@Test
	void testAlreadyExistingCard()
	{
		board.getPlacedCards().forEach((key, value) -> assertFalse(board.isCardInTheLayout(key.getX(), key.getY())));
	}

	@Test
	void testImpossibleLayoutConfiguration()
	{
		board.getPlacedCards().put(new Coordinates(1, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED));

		assertFalse(board.isCardInTheLayout(50, 60));
		assertFalse(board.isCardInTheLayout(6, 1));
		assertFalse(board.isCardInTheLayout(1, 6));
	}

	@Test
	void testCase1()
	{
		initCase1();


		for (int index = 0; index <= 4; index += 3)
		{
			for (int i = 0; i <= 5; ++i)
			{
				assertTrue(board.isCardInTheLayout(i, index));
			}
		}

		for (int i = 0; i < 3; ++i)
		{
			assertFalse(board.isCardInTheLayout(6, i));
		}

		for (int i = 0; i < 6; ++i)
		{
			System.out.println(i + " " + 4);
			assertFalse(board.isCardInTheLayout(i, 4));
			assertFalse(board.isCardInTheLayout(i, -1));

		}
	}

	private void initCase1()
	{
		board.getPlacedCards().clear();

		for (int j = 1; j < 5; j++)
		{
			board.getPlacedCards().put(new Coordinates(j, 2), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED));
		}

		for (int j = 1; j < 4; j++)
		{
			board.getPlacedCards().put(new Coordinates(j, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED));
		}
	}

	@Test
	void testCase2()
	{
		board.getPlacedCards().clear();
		IntStream.iterate(5, i -> i > 0, i -> i - 1).forEach(i -> board.getPlacedCards().put(new Coordinates(1, i), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED)));
		IntStream.range(1, 3).forEach(i -> board.getPlacedCards().put(new Coordinates(2, i), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED)));


		for (int index = 0; index <= 4; index += 3)
		{
			for (int i = 1; i <= 5; ++i)
			{
				assertTrue(board.isCardInTheLayout(index, i));
			}
		}

		for (int i = 0; i < 6; ++i)
		{
			assertFalse(board.isCardInTheLayout(-1, i));
		}

		for (int i = 0; i < 6; ++i)
		{
			System.out.println(i + " " + 4);
			assertFalse(board.isCardInTheLayout(i, 6));
			assertFalse(board.isCardInTheLayout(4, i));

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
				board.getPlacedCards().put(new Coordinates(i, j), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.HOLLOW));
			}
		}

		for (int i = -3; i <= -2; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				assertTrue(board.isCardInTheLayout(i, j));
			}
		}

		for (int i = 2; i <= 3; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				assertTrue(board.isCardInTheLayout(i, j));
			}
		}

		for (int i = 2; i <= 3; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				System.out.println(i + " " + j);
				assertTrue(board.isCardInTheLayout(i, j));
			}
		}

		for (int i = -3; i <= -2; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				System.out.println("aa" + j + " " + i);
				assertTrue(board.isCardInTheLayout(j, i));
			}
		}

		assertFalse(board.isCardInTheLayout(2, 2));
		assertFalse(board.isCardInTheLayout(2, 3));


	}

	@Test
	void testCase4()
	{
		board.getPlacedCards().clear();
		board.getPlacedCards().put(new Coordinates(0, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED));
		board.getPlacedCards().put(new Coordinates(0, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED));
		board.getPlacedCards().put(new Coordinates(1, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED));
		board.getPlacedCards().put(new Coordinates(1, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Hollow.FILLED));

		for (int i = -3; i < 0; i++)
		{
			for (int j = -1; j < 3; j++)
			{
				assertTrue(board.isCardInTheLayout(i, j));
			}
		}

		for (int i = 2; i < 5; i++)
		{
			for (int j = -1; j < 3; j++)
			{
				assertTrue(board.isCardInTheLayout(i, j));
			}
		}

		for (int i = 2; i < 5; i++)
		{
			for (int j = -1; j < 3; j++)
			{
				assertTrue(board.isCardInTheLayout(j, i));
			}
		}

		for (int i = -3; i < 0; i++)
		{
			for (int j = -1; j < 3; j++)
			{
				assertTrue(board.isCardInTheLayout(j, i));
			}
		}

		assertFalse(board.isCardInTheLayout(3, 3));
		assertFalse(board.isCardInTheLayout(3, 4));
		assertFalse(board.isCardInTheLayout(4, 3));
		assertFalse(board.isCardInTheLayout(-2, -2));
		assertFalse(board.isCardInTheLayout(2, 5));
		assertFalse(board.isCardInTheLayout(5, 2));

	}

	@Test
	void emptyMap()
	{
		board.getPlacedCards().clear();
		assertTrue(board.isCardInTheLayout(0, 0));
	}

}