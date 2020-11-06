package fr.utt.lo02.projet;

public abstract class AbstractBoard {
	
	Coordinates placedCards[];
	
	public AbstractBoard(Coordinates pC[]) {
		this.placedCards = pC;
	}
	
	public abstract void accept(IBoardVisitor board, Card victoryCard);
	
	public abstract boolean isCardAdjacent(int x, int y);
	
	public abstract boolean isCardInTheLayout(int x, int y);
	
	public void addCard(Card newCard, int x, int y) {
		
	}
	
}
