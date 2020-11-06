package fr.utt.lo02.projet;

public interface IBoardVisitor {
	
	public abstract int visit(CircleBoard board, Card victoryCard);
	
	public abstract int visit(TriangleBoard board, Card victoryCard);
	
	public abstract int visit(RectangleBoard board, Card victoryCard);
	
}
	