package fr.utt.lo02.projet;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.BoardEmptyException;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.game.ShapeUpGame;
import fr.utt.lo02.projet.strategy.*;

import java.util.ArrayList;
import java.util.List;

public class Main
{

	public static void main(String[] args) throws PlayerHandEmptyException, BoardEmptyException
	{
		// TODO Auto-generated method stub
		AbstractBoard rb = new RectangleBoard();
		List<Player> ps = new ArrayList<>();
		ScoreCalculatorVisitor visitor = new ScoreCalculatorVisitor();


		ps.add(new VirtualPlayer("ord1",rb, new DifficultStrategy(visitor)));
		ps.add(new VirtualPlayer("ord2",rb, new RandomStrategy()));


		new ShapeUpGame(visitor, ps, rb);

	}

}
