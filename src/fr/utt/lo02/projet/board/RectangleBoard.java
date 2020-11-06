package fr.utt.lo02.projet.board;

import fr.utt.lo02.projet.board.visitor.IBoardVisitor;

public class RectangleBoard extends AbstractBoard {

	public RectangleBoard(Coordinates[] pC) {
		super(pC);
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
	public boolean isCardInTheLayout(int x, int y) {
		
		return true;
	}

}
