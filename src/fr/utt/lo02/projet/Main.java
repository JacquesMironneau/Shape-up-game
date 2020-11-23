package fr.utt.lo02.projet;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.CircleBoard;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.boardEmptyException;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.game.ShapeUpGame;
import fr.utt.lo02.projet.game.ShapeUpGameAdvanced;
import fr.utt.lo02.projet.game.ShapeUpGameWithoutAdajencyRule;
import fr.utt.lo02.projet.strategy.PlayerHandEmptyException;
import fr.utt.lo02.projet.strategy.PlayerStrategy;
import fr.utt.lo02.projet.strategy.RealPlayer;
import fr.utt.lo02.projet.strategy.VirtualPlayer;

import java.util.ArrayList;
import java.util.List;

public class Main
{

	public static void main(String[] args) throws PlayerHandEmptyException, boardEmptyException
	{
		// TODO Auto-generated method stub
		AbstractBoard rb = new CircleBoard();
		List<PlayerStrategy> ps = new ArrayList<>();
		ps.add(new VirtualPlayer("ord1",rb));
		ps.add(new VirtualPlayer("ord2",rb));
		ps.add(new VirtualPlayer("ord2",rb));
		ps.add(new VirtualPlayer("ord2",rb));
		//ps.add(new VirtualPlayer("ord3",rb));


		new ShapeUpGameWithoutAdajencyRule(new ScoreCalculatorVisitor(), ps, rb).playGame();
	}

}
