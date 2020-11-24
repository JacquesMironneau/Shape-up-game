package fr.utt.lo02.projet.junit;

import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.TriangleBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TriangleBoardTest
{
	private TriangleBoard triangleBoard;

	@BeforeEach
	void setUp()
	{
		triangleBoard = new TriangleBoard();

	}

	// Rectangle triangle with hypotenuse from top left to bottom right
	@Test
	void Test()
	{
		triangleBoard.getPlacedCards().put(new Coordinates(0, 0), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		triangleBoard.getPlacedCards().put(new Coordinates(1, 0), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		triangleBoard.getPlacedCards().put(new Coordinates(2, 0), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		triangleBoard.getPlacedCards().put(new Coordinates(3, 0), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		triangleBoard.getPlacedCards().put(new Coordinates(4, 0), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		triangleBoard.getPlacedCards().put(new Coordinates(0, -1), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		triangleBoard.getPlacedCards().put(new Coordinates(1, -1), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		triangleBoard.getPlacedCards().put(new Coordinates(2, -1), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		triangleBoard.getPlacedCards().put(new Coordinates(3, -1), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		triangleBoard.getPlacedCards().put(new Coordinates(0, -2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		triangleBoard.getPlacedCards().put(new Coordinates(1, -2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		triangleBoard.getPlacedCards().put(new Coordinates(2, -2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		triangleBoard.getPlacedCards().put(new Coordinates(0, -3), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		triangleBoard.getPlacedCards().put(new Coordinates(1, -3), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		triangleBoard.getPlacedCards().put(new Coordinates(0, -4), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));

	}
}