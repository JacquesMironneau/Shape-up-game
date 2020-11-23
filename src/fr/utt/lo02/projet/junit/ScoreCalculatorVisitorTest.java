package fr.utt.lo02.projet.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorVisitor;



/**
 * Case 1: the board is horizontal and has 15 cards
 * Case 2: the board is vertical and has 15 cards
 * Case 3: the board is vertical and has 14 cards with a hole in the right down corner
 * Case 4: the board is horizontal and has 14 cards with a hole in the board's middle
 * Case 5: the board is not vertical and not horizontal (3*3)
 * Case 6: the board is not vertical and not horizontal (2*2)
 * Case 7: the board has only one Card
 */
class ScoreCalculatorVisitorTest {
	
	private static RectangleBoard board;
	private static Card victoryCardA = new Card(Card.Color.GREEN,Card.Shape.CIRCLE,Card.Filling.HOLLOW);
	private static Card victoryCardB = new Card(Card.Color.RED,Card.Shape.TRIANGLE,Card.Filling.FILLED);
	private static Card victoryCardC = new Card(Card.Color.BLUE,Card.Shape.TRIANGLE,Card.Filling.HOLLOW);
	private static Card victoryCardD;
	
	@Test
	void case1() {
		board = new RectangleBoard();
		board.getPlacedCards().put(new Coordinates(0,0), new Card(Card.Color.RED,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(1,0), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(2,0), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(3,0), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(4,0), new Card(Card.Color.BLUE,Card.Shape.TRIANGLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(0,-1), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1,-1), new Card(Card.Color.GREEN,Card.Shape.CIRCLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(2,-1), new Card(Card.Color.GREEN,Card.Shape.SQUARE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(3,-1), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(4,-1), new Card(Card.Color.BLUE,Card.Shape.SQUARE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(0,-2), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(1,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(2,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(3,-2), new Card(Card.Color.BLUE,Card.Shape.SQUARE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(4,-2), new Card(Card.Color.GREEN,Card.Shape.SQUARE,Card.Filling.FILLED));
		ScoreCalculatorVisitor test = new ScoreCalculatorVisitor();
		assertEquals(test.visit(board, victoryCardA), 13);
		assertEquals(test.visit(board, victoryCardB), 10);
		assertEquals(test.visit(board, victoryCardC), 12);
	}
	
	@Test
	void case2() {
		board = new RectangleBoard();
		board.getPlacedCards().put(new Coordinates(2,0), new Card(Card.Color.RED,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(2,-1), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(2,-2), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(2,-3), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(2,-4), new Card(Card.Color.BLUE,Card.Shape.TRIANGLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1,0), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1,-1), new Card(Card.Color.GREEN,Card.Shape.CIRCLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1,-2), new Card(Card.Color.GREEN,Card.Shape.SQUARE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(1,-3), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1,-4), new Card(Card.Color.BLUE,Card.Shape.SQUARE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(0,0), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(0,-1), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(0,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(0,-3), new Card(Card.Color.BLUE,Card.Shape.SQUARE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(0,-4), new Card(Card.Color.GREEN,Card.Shape.SQUARE,Card.Filling.FILLED));
		ScoreCalculatorVisitor test = new ScoreCalculatorVisitor();
		assertEquals(test.visit(board, victoryCardA), 13);
		assertEquals(test.visit(board, victoryCardB), 10);
		assertEquals(test.visit(board, victoryCardC), 12);
	}
	
	@Test
	void case3() {
		board = new RectangleBoard();
		board.getPlacedCards().put(new Coordinates(2,0), new Card(Card.Color.RED,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(2,-1), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(2,-2), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(2,-3), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1,0), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1,-1), new Card(Card.Color.GREEN,Card.Shape.CIRCLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1,-2), new Card(Card.Color.GREEN,Card.Shape.SQUARE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(1,-3), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1,-4), new Card(Card.Color.BLUE,Card.Shape.SQUARE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(0,0), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(0,-1), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(0,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(0,-3), new Card(Card.Color.BLUE,Card.Shape.SQUARE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(0,-4), new Card(Card.Color.GREEN,Card.Shape.SQUARE,Card.Filling.FILLED));
		ScoreCalculatorVisitor test = new ScoreCalculatorVisitor();
		victoryCardD = new Card(Card.Color.BLUE,Card.Shape.TRIANGLE,Card.Filling.FILLED);
		assertEquals(test.visit(board, victoryCardA), 13);
		assertEquals(test.visit(board, victoryCardB), 7);
		assertEquals(test.visit(board, victoryCardC), 12);
		assertEquals(test.visit(board, victoryCardD), 6);
	}
	
	
	@Test
	void case4() {
		board = new RectangleBoard();
		board.getPlacedCards().put(new Coordinates(0,0), new Card(Card.Color.RED,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(1,0), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(2,0), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(3,0), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(4,0), new Card(Card.Color.BLUE,Card.Shape.TRIANGLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(0,-1), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1,-1), new Card(Card.Color.GREEN,Card.Shape.CIRCLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(3,-1), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(4,-1), new Card(Card.Color.BLUE,Card.Shape.SQUARE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(0,-2), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(1,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(2,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(3,-2), new Card(Card.Color.BLUE,Card.Shape.SQUARE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(4,-2), new Card(Card.Color.GREEN,Card.Shape.SQUARE,Card.Filling.FILLED));
		ScoreCalculatorVisitor test = new ScoreCalculatorVisitor();
		victoryCardD = new Card(Card.Color.GREEN,Card.Shape.SQUARE,Card.Filling.HOLLOW);
		assertEquals(test.visit(board, victoryCardA), 6);
		assertEquals(test.visit(board, victoryCardB), 10);
		assertEquals(test.visit(board, victoryCardC), 9);
		assertEquals(test.visit(board, victoryCardD), 6);
	}
	
	@Test
	void case5() {
		board = new RectangleBoard();
		board.getPlacedCards().put(new Coordinates(0,0), new Card(Card.Color.RED,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(1,0), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(2,0), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(0,-1), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1,-1), new Card(Card.Color.GREEN,Card.Shape.CIRCLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(2,-1), new Card(Card.Color.GREEN,Card.Shape.SQUARE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(0,-2), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(1,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(2,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
		ScoreCalculatorVisitor test = new ScoreCalculatorVisitor();
		assertEquals(test.visit(board, victoryCardA), 13);
		assertEquals(test.visit(board, victoryCardB), 6);
		assertEquals(test.visit(board, victoryCardC), 8);
	}
	
	@Test
	void case6( ) {
			board = new RectangleBoard();
			board.getPlacedCards().put(new Coordinates(0,0), new Card(Card.Color.RED,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
			board.getPlacedCards().put(new Coordinates(1,0), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
			board.getPlacedCards().put(new Coordinates(0,-1), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.FILLED));
			board.getPlacedCards().put(new Coordinates(1,-1), new Card(Card.Color.GREEN,Card.Shape.CIRCLE,Card.Filling.FILLED));
			ScoreCalculatorVisitor test = new ScoreCalculatorVisitor();

			assertEquals(test.visit(board, victoryCardA), 1);
			assertEquals(test.visit(board, victoryCardB), 1);
			assertEquals(test.visit(board, victoryCardC), 1);
	}
	
	@Test
	void case7() {
		board = new RectangleBoard();
		board.getPlacedCards().put(new Coordinates(0,0), new Card(Card.Color.RED,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
		ScoreCalculatorVisitor test = new ScoreCalculatorVisitor();
		assertEquals(test.visit(board, victoryCardA), 0);
		assertEquals(test.visit(board, victoryCardB), 0);
		assertEquals(test.visit(board, victoryCardC), 0);
	}

	@Test
	void caseCircle1()
	{

	}

}
