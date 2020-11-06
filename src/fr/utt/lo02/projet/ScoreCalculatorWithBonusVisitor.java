package fr.utt.lo02.projet;

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
