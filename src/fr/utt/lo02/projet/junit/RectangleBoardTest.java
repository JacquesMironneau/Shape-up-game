package fr.utt.lo02.projet.junit;

import fr.utt.lo02.projet.model.board.Card;
import fr.utt.lo02.projet.model.board.Coordinates;
import fr.utt.lo02.projet.model.board.RectangleBoard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for the rectangle board, we cover several case:
 *
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
        assertTrue(board.isCardAdjacent(new Coordinates(1, 1))); // An existing card is adjacent


    }

    @Test
    void testAlreadyExistingCard()
    {
        for (Map.Entry<Coordinates, Card> entry : board.getPlacedCards().entrySet())
        {
            Coordinates key = entry.getKey();
            assertFalse(board.isCardInTheLayout(key));
        }
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
            board.getPlacedCards().put(new Coordinates(j, 1), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.FILLED));
        }

        board.display();

    }

    @Test
    void testCase2()
    {
        board.getPlacedCards().clear();
        IntStream.iterate(5, i -> i > 0, i -> i - 1).forEach(i -> board.getPlacedCards().put(new Coordinates(1, i), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED)));
        for (int i1 = 1; i1 < 3; i1++)
        {
            board.getPlacedCards().put(new Coordinates(2, i1), new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED));
        }


        board.display();
        for (int index = 0; index <= 4; index += 3)
        {
            for (int i = 1; i <= 5; ++i)
            {
                assertTrue(board.isCardInTheLayout(new Coordinates(index, i)));
            }
        }

        assertFalse(board.isCardInTheLayout(new Coordinates(-1, 0)));
        assertFalse(board.isCardInTheLayout(new Coordinates(-1, 1)));
        assertFalse(board.isCardInTheLayout(new Coordinates(-1, 2)));
        assertFalse(board.isCardInTheLayout(new Coordinates(-1, 3)));
        assertFalse(board.isCardInTheLayout(new Coordinates(-1, 4)));

        for (Coordinates coordinates : Arrays.asList(new Coordinates(-1, 5), new Coordinates(0, 6), new Coordinates(4, 0), new Coordinates(1, 6), new Coordinates(4, 1), new Coordinates(2, 6), new Coordinates(4, 2), new Coordinates(3, 6), new Coordinates(4, 3), new Coordinates(4, 6), new Coordinates(4, 4), new Coordinates(5, 6)))
        {
            assertFalse(board.isCardInTheLayout(coordinates));
        }


        assertFalse(board.isCardInTheLayout(new Coordinates(4, 5)));

        System.out.println("--------------");
        board.display();

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
                assertTrue(board.isCardInTheLayout(new Coordinates(i, j)));

            }
        }

        for (int i = 2; i <= 3; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                assertTrue(board.isCardInTheLayout(new Coordinates(i, j)));

            }
        }

        for (int i = 2; i <= 3; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                assertTrue(board.isCardInTheLayout(new Coordinates(i, j)));

            }
        }

        for (int i = -3; i <= -2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                assertTrue(board.isCardInTheLayout(new Coordinates(j, i)));
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
                assertTrue(board.isCardInTheLayout(new Coordinates(i, j)));
            }
        }

        for (int i = 2; i < 5; i++)
        {
            for (int j = -1; j < 3; j++)
            {
                assertTrue(board.isCardInTheLayout(new Coordinates(i, j)));
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
        board.getPlacedCards().clear();
        assertTrue(board.isCardInTheLayout(new Coordinates(0, 0)));
    }

    @Test
    void displayTest()
    {
        board.getPlacedCards().put(new Coordinates(0, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(0, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(1, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(1, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(2, 0), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(2, 2), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(1, 2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(-1, 1), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.FILLED));

        board.display();
    }

    @Test
    void horizontalTest()
    {
        board.getPlacedCards().clear();
        board.getPlacedCards().put(new Coordinates(0, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(1, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(2, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(3, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        assertFalse(board.isVertical());
        // 0 - 3 = -3 abs =3
        assertTrue(board.isHorizontal());
        //assertTrue();
    }

    @Test
    void horizontalHoleTest()
    {
        board.getPlacedCards().clear();
        board.getPlacedCards().put(new Coordinates(0, 2), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(0, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(0, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(0, -1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(-1, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(-1, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(-2, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(-2, -1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));

        board.display();


        assertFalse(board.isHorizontal());
        // 0 - 3 = -3 abs =3
        assertTrue(board.isVertical());

        assertFalse(board.isCardInTheLayout(new Coordinates(-3, 0)));

        board.getPlacedCards().remove(new Coordinates(0, 0));
        assertTrue(board.isVertical());
        assertFalse(board.isHorizontal());

        assertFalse(board.isCardInTheLayout(new Coordinates(-3, 0)));


    }
}