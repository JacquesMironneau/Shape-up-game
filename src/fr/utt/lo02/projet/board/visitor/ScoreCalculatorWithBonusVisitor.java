package fr.utt.lo02.projet.board.visitor;

import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.CircleBoard;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.TriangleBoard;

/**
 * Represent one of the different variants to calculate the score for the game.
 * This one have Bonus with Cards.
 * It implements IBoard Visitor to follow the visitor's construction.
 * @author Baptiste, Jacques
 *
 */

public class ScoreCalculatorWithBonusVisitor implements IBoardVisitor {

	public ScoreCalculatorWithBonusVisitor() {
	}

	public int visit(CircleBoard board, Card victoryCard) {
		int score=1;
		return score;
	}
	
	public int visit(TriangleBoard board, Card victoryCard) {
		int score=1;
		return score;
	}
	
	public int visit(RectangleBoard board, Card victoryCard) {
		int score=1;
		return score;
	}
}
