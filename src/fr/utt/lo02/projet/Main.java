package fr.utt.lo02.projet;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.RectangleBoard;
import fr.utt.lo02.projet.model.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.model.game.ShapeUpGame;
import fr.utt.lo02.projet.model.player.Player;
import fr.utt.lo02.projet.model.player.VirtualPlayer;
import fr.utt.lo02.projet.model.strategy.*;

import java.util.ArrayList;
import java.util.List;

public class Main
{

	public static void main(String[] args)
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
