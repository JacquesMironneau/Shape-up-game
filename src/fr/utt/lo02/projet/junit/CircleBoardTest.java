package fr.utt.lo02.projet.junit;

import fr.utt.lo02.projet.board.CircleBoard;
import fr.utt.lo02.projet.board.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleBoardTest
{

	private CircleBoard board;
	@BeforeEach
	void setUp()
	{
		board = new CircleBoard();
	}

	@Test
	void testCompletePattern()
	{
		
		board.getPlacedCards().put(new Coordinates(2,2), null);
		board.getPlacedCards().put(new Coordinates(3,2), null);
		board.getPlacedCards().put(new Coordinates(2,1), null);
		board.getPlacedCards().put(new Coordinates(3,1), null);
		board.getPlacedCards().put(new Coordinates(4,1), null);
		board.getPlacedCards().put(new Coordinates(2,0), null);
		board.getPlacedCards().put(new Coordinates(3,0), null);
		board.getPlacedCards().put(new Coordinates(4,0), null);
		board.getPlacedCards().put(new Coordinates(5,0), null);
		board.getPlacedCards().put(new Coordinates(3,-1), null);
		board.getPlacedCards().put(new Coordinates(4,-1), null);
		board.getPlacedCards().put(new Coordinates(5,-1), null);
		board.getPlacedCards().put(new Coordinates(4,-2), null);
		assertTrue(board.isCardInTheLayout(new Coordinates(5,-2)));
		assertFalse(board.isCardInTheLayout(new Coordinates(6,-2)));
		assertFalse(board.isCardInTheLayout(new Coordinates(5,-3)));

	}

	@Test
	void testRow()
	{
		board.getPlacedCards().put(new Coordinates(0,0), null);
		assertFalse(board.isCardInTheLayout(new Coordinates(0,0)));

		
		board.getPlacedCards().put(new Coordinates(1,0), null);
		board.getPlacedCards().put(new Coordinates(2,0), null);
		board.getPlacedCards().put(new Coordinates(3,0), null);

		assertTrue(board.isCardInTheLayout(new Coordinates(0,1)));
		assertTrue(board.isCardInTheLayout(new Coordinates(1,1)));
		assertTrue(board.isCardInTheLayout(new Coordinates(2,1)));
		assertTrue(board.isCardInTheLayout(new Coordinates(1,-1)));
		assertTrue(board.isCardInTheLayout(new Coordinates(2,-1)));
		assertTrue(board.isCardInTheLayout(new Coordinates(3,-1)));
		assertFalse(board.isCardInTheLayout(new Coordinates(4,0)));
		assertFalse(board.isCardInTheLayout(new Coordinates(4,-1)));
		assertFalse(board.isCardInTheLayout(new Coordinates(2,2)));
	}
	
	@Test
	void testSnake() {
		board.getPlacedCards().put(new Coordinates(2,2), null);
		board.getPlacedCards().put(new Coordinates(3,2), null);
		board.getPlacedCards().put(new Coordinates(3,1), null);
		board.getPlacedCards().put(new Coordinates(3,0), null);
		board.getPlacedCards().put(new Coordinates(4,0), null);
		board.getPlacedCards().put(new Coordinates(4,-1), null);
		board.getPlacedCards().put(new Coordinates(5,-2), null);
		board.getPlacedCards().put(new Coordinates(4,-2), null);
		assertTrue(board.isCardInTheLayout(new Coordinates(5,-1)));
		assertTrue(board.isCardInTheLayout(new Coordinates(5,0)));
		assertTrue(board.isCardInTheLayout(new Coordinates(4, 1)));
		assertTrue(board.isCardInTheLayout(new Coordinates(3,-1)));
		assertTrue(board.isCardInTheLayout(new Coordinates(2,0)));
		assertTrue(board.isCardInTheLayout(new Coordinates(2,1)));
		assertFalse(board.isCardInTheLayout(new Coordinates(6,-2)));
		assertFalse(board.isCardInTheLayout(new Coordinates(5,-3)));
		assertFalse(board.isCardInTheLayout(new Coordinates(3,-2)));
		assertFalse(board.isCardInTheLayout(new Coordinates(4,2)));
	}
	
	@Test
	void testSquare()
	{
		board.getPlacedCards().put(new Coordinates(1,0), null);		
		board.getPlacedCards().put(new Coordinates(2,0), null);
		board.getPlacedCards().put(new Coordinates(1,-1), null);
		board.getPlacedCards().put(new Coordinates(2,-1), null);

		assertTrue(board.isCardInTheLayout(new Coordinates(0,1)));
		assertTrue(board.isCardInTheLayout(new Coordinates(0,2)));
		assertTrue(board.isCardInTheLayout(new Coordinates(1,1)));
		assertTrue(board.isCardInTheLayout(new Coordinates(1,2)));
		assertTrue(board.isCardInTheLayout(new Coordinates(2,1)));
		assertTrue(board.isCardInTheLayout(new Coordinates(0,0)));
		assertTrue(board.isCardInTheLayout(new Coordinates(3,0)));
		assertTrue(board.isCardInTheLayout(new Coordinates(3,-1)));
		assertTrue(board.isCardInTheLayout(new Coordinates(3,-2)));
		assertTrue(board.isCardInTheLayout(new Coordinates(3,-3)));
		assertTrue(board.isCardInTheLayout(new Coordinates(2,-2)));
		assertTrue(board.isCardInTheLayout(new Coordinates(3,-2)));
		assertTrue(board.isCardInTheLayout(new Coordinates(3,-3)));
		assertTrue(board.isCardInTheLayout(new Coordinates(3,-4)));
		assertTrue(board.isCardInTheLayout(new Coordinates(4,-1)));
		assertTrue(board.isCardInTheLayout(new Coordinates(4,-2)));
		assertTrue(board.isCardInTheLayout(new Coordinates(4,-3)));
		assertTrue(board.isCardInTheLayout(new Coordinates(4,-4)));
		assertFalse(board.isCardInTheLayout(new Coordinates(4,0)));
		assertFalse(board.isCardInTheLayout(new Coordinates(2,2)));
		assertTrue(board.isCardInTheLayout(new Coordinates(1,-2)));
		assertTrue(board.isCardInTheLayout(new Coordinates(0,-1)));
		assertFalse(board.isCardInTheLayout(new Coordinates(0,-2)));
	}
}