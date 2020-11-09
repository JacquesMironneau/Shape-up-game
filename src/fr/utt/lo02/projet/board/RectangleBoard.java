package fr.utt.lo02.projet.board;

import fr.utt.lo02.projet.board.visitor.IBoardVisitor;

/**
 * Represent a rectangle game board, one of the different shapes for the game.
 * It is extends by Abstract Board to follow the board's construction.
 * @author Baptiste, Jacques
 *
 */

public class RectangleBoard extends AbstractBoard {

	public RectangleBoard() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(IBoardVisitor board, Card victoryCard) {
		
	}
	
	@Override
	public boolean isCardAdjacent(int x, int y) {
		
		
		return true;
	}
	
	@Override
	public boolean isCardInTheLayout(int x, int y)
	{
		if (placedCards.containsKey(new Coordinates(x,y))) return false;
		
		
		return true;
	}

}
