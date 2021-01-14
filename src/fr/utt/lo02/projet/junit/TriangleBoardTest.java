package fr.utt.lo02.projet.junit;

import fr.utt.lo02.projet.model.board.Card;
import fr.utt.lo02.projet.model.board.Coordinates;
import fr.utt.lo02.projet.model.board.TriangleBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test every case of the triangle board
 * A case is a orientation of the right-angled triangle
 * (thus where the right angled is located (top left, top right, bottom left, bottom right)
 */
class TriangleBoardTest
{
    private TriangleBoard triangleBoard;

    @BeforeEach
    void setUp()
    {
        triangleBoard = new TriangleBoard();

    }

    // Rectangle triangle with hypotenuse from top right to bottom left
    @Test
    void TestCase2()
    {
        for (int i = 0; i < 5; i++)
        {
            triangleBoard.getPlacedCards().put(new Coordinates(i, -4), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        }
        for (int i : new int[]{0, 2, 3})
        {
            triangleBoard.getPlacedCards().put(new Coordinates(i, -5), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        }
        for (int i = 0; i < 3; i++)
        {
            triangleBoard.getPlacedCards().put(new Coordinates(i, -6), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        }
        for (int i = 0; i < 2; i++)
        {
            triangleBoard.getPlacedCards().put(new Coordinates(i, -7), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        }
        //triangleBoard.getPlacedCards().put(new Coordinates(0, -4), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        assertFalse(triangleBoard.isCardInTheLayout(new Coordinates(0, -5)));
        assertTrue(triangleBoard.isCardInTheLayout(new Coordinates(1, -5)));
        assertFalse(triangleBoard.isCardInTheLayout(new Coordinates(0, -9)));


        assertTrue(triangleBoard.isCardInTheLayout(new Coordinates(0, -8)));
        assertFalse(triangleBoard.isCardInTheLayout(new Coordinates(0, -9)));

        triangleBoard.display();
    }

    @Test
    void TestCase2WithAFewThings()
    {
        for (int i = 0; i < 4; i++)
        {
            triangleBoard.getPlacedCards().put(new Coordinates(i, -4), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        }
        assertTrue(triangleBoard.isCardInTheLayout(new Coordinates(4, -4)));
        assertTrue(triangleBoard.isCardInTheLayout(new Coordinates(-1, -4)));
        assertFalse(triangleBoard.isCardInTheLayout(new Coordinates(5, -4)));
        assertFalse(triangleBoard.isCardInTheLayout(new Coordinates(-2, -4)));
        triangleBoard.getPlacedCards().put(new Coordinates(4, -4), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));

        triangleBoard.getPlacedCards().put(new Coordinates(0, -2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(1, -2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(2, -2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.display();

        assertFalse(triangleBoard.isCardInTheLayout(new Coordinates(3, -2)));
        assertFalse(triangleBoard.isCardInTheLayout(new Coordinates(-1, -2)));

        assertFalse(triangleBoard.isCardInTheLayout(new Coordinates(-1, -3)));
        assertTrue(triangleBoard.isCardInTheLayout(new Coordinates(1, -3)));
        assertTrue(triangleBoard.isCardInTheLayout(new Coordinates(2, -3)));
        assertTrue(triangleBoard.isCardInTheLayout(new Coordinates(3, -3)));
        assertFalse(triangleBoard.isCardInTheLayout(new Coordinates(4, -3)));


        //triangleBoard.getPlacedCards().put(new Coordinates(0, -2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));

    }

    @Test
    void TestCase1()
    {
        triangleBoard.getPlacedCards().put(new Coordinates(4, 0), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(3, -1), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(4, -1), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(1, -3), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(2, -3), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(3, -3), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(4, -3), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(0, -4), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(1, -4), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(2, -4), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(3, -4), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(4, -4), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));


        assertTrue(triangleBoard.isCardInTheLayout(new Coordinates(2, -2)));
        assertTrue(triangleBoard.isCardInTheLayout(new Coordinates(3, -2)));
        assertTrue(triangleBoard.isCardInTheLayout(new Coordinates(4, -2)));
        assertFalse(triangleBoard.isCardInTheLayout(new Coordinates(5, -2)));
        assertFalse(triangleBoard.isCardInTheLayout(new Coordinates(1, -2)));
        assertFalse(triangleBoard.isCardInTheLayout(new Coordinates(4, 1)));


    }

    @Test
    void TestFragment()
    {
        triangleBoard.getPlacedCards().put(new Coordinates(2, -3), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(3, -3), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(4, -3), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(2, -4), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(3, -4), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(4, -4), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        assertTrue(triangleBoard.isCardInTheLayout(new Coordinates(5, -3)));


        assertTrue(triangleBoard.isCardInTheLayout(new Coordinates(1, -3)));
        assertTrue(triangleBoard.isCardInTheLayout(new Coordinates(0, -3)));
        assertTrue(triangleBoard.isCardInTheLayout(new Coordinates(6, -3)));
    }

    @Test
    void testDisplay()
    {
        triangleBoard.getPlacedCards().put(new Coordinates(2, 0), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(2, -1), new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(3, -1), new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(2, -2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(3, -2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(4, -2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(2, -3), new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(3, -3), new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(4, -3), new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(5, -3), new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(2, -4), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(3, -4), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(4, -4), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(5, -4), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        triangleBoard.getPlacedCards().put(new Coordinates(6, -4), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW));

        triangleBoard.display();
    }
}