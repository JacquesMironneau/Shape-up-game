package fr.utt.lo02.projet;

import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.game.ShapeUpGame;
import fr.utt.lo02.projet.strategy.Player;
import fr.utt.lo02.projet.strategy.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class RealPlayers
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		RectangleBoard rb = new RectangleBoard();
		List<Player> ps = new ArrayList<>();
		ps.add(new RealPlayer("Vrai1",rb));
		ps.add(new RealPlayer("Vrai2",rb));


		new ShapeUpGame(new ScoreCalculatorVisitor(), ps, rb);
	}
}
