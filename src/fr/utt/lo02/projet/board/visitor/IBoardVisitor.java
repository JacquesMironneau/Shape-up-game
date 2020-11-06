package fr.utt.lo02.projet.board.visitor;

import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.CircleBoard;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.TriangleBoard;

public interface IBoardVisitor {
	
	public abstract int visit(CircleBoard board, Card victoryCard);
	
	public abstract int visit(TriangleBoard board, Card victoryCard);
	
	public abstract int visit(RectangleBoard board, Card victoryCard);
	
}
	