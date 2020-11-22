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
	void completePattern()
	{
		assertTrue(board.isCardInTheLayout(new Coordinates(0,0)));
		assertTrue(board.isCardInTheLayout(new Coordinates(0,0)));
		assertTrue(board.isCardInTheLayout(new Coordinates(0,0)));
		assertTrue(board.isCardInTheLayout(new Coordinates(0,0)));
		assertTrue(board.isCardInTheLayout(new Coordinates(0,0)));
		assertTrue(board.isCardInTheLayout(new Coordinates(0,0)));
		assertTrue(board.isCardInTheLayout(new Coordinates(0,0)));
		assertTrue(board.isCardInTheLayout(new Coordinates(0,0)));
		assertTrue(board.isCardInTheLayout(new Coordinates(0,0)));
	}

	@Test
	void testSquare()
	{
		board.getPlacedCards().put(new Coordinates(0,0), null);
		assertFalse(board.isCardInTheLayout(new Coordinates(0,0)));

		assertTrue(board.isCardInTheLayout(new Coordinates(1,0)));
		assertTrue(board.isCardInTheLayout(new Coordinates(2,0)));
		assertTrue(board.isCardInTheLayout(new Coordinates(3,0)));
		assertFalse(board.isCardInTheLayout(new Coordinates(4,0)));


	}
}