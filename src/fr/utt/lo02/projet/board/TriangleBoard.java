package fr.utt.lo02.projet.board;

import fr.utt.lo02.projet.board.visitor.IBoardVisitor;

/**
 * Represent a triangle game board, one of the different shapes for the game.
 * It is extends by Abstract Board to follow the board's construction.
 * @author Baptiste, Jacques
 *
 */

public class TriangleBoard extends AbstractBoard {

	public TriangleBoard() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public int accept(IBoardVisitor board, Card victoryCard)
	{
		return board.visit(this, victoryCard);
	}

	@Override
	public boolean isCardAdjacent(Coordinates coordinates)
	{
		return false;
	}

	@Override
	public boolean isCardInTheLayout(Coordinates coordinates)
	{
		return false;
	}
}
