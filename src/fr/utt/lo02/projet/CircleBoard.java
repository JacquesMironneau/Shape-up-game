package fr.utt.lo02.projet;

public class CircleBoard extends AbstractBoard {
	
	public CircleBoard(Coordinates[] pC) {
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
